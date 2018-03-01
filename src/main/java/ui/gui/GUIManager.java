package ui.gui;

import core.Board;
import core.Tile;
import ui.gui.fills.AbstractTileFillGeneratorGUI;
import core.interfaces.TileFill;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GUIManager extends Application {

    private static final int X_TILES = 8;
    private static final int Y_TILES = 8;
    private static final int TILE_SIZE = 40;
    private static final int WIDTH = X_TILES * TILE_SIZE + TILE_SIZE;
    private static final int HEIGHT = Y_TILES * TILE_SIZE + TILE_SIZE;
    private static final int BORDER_SIZE = TILE_SIZE - 2;

    private static int movesCount;

    private Board board;
    private Scene scene;

    public static void startGame(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        board = new Board(X_TILES, Y_TILES, AbstractTileFillGeneratorGUI.getFactory());
        scene = new Scene(drawBoard());
        movesCount = 0;

        stage.setScene(scene);
        stage.show();
    }

    private Parent drawBoard() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);

        for (Tile tile : board) {
            StackPane pane = new StackPane();
            TileFill tileColor = tile.getFill();
            Color color = Color.web(tileColor.getValue(), 1);
            Rectangle border = new Rectangle(BORDER_SIZE, BORDER_SIZE, color);
            // @todo Fix border.
            border.setStroke(Color.LIGHTGRAY);
            pane.getChildren().addAll(border);

            pane.setTranslateX(board.getTileX(tile) * TILE_SIZE);
            pane.setTranslateY(board.getTileY(tile) * TILE_SIZE);

            if (tile.getFill() != board.getCurrentFill()) {
                pane.setOnMouseClicked(e -> this.makeMove(tile));
            }

            root.getChildren().add(pane);
        }

        return root;
    }

    private void makeMove(Tile tile) {
        board.makeMove(tile.getFill());
        movesCount++;
        scene.setRoot(drawBoard());
    }
}
