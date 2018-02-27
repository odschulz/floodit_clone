package ui.gui;

import core.Board;
import core.Tile;
import core.color.AbstractTileColorFactory;
import core.color.TileColor;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GUI extends Application {

    private static final int X_TILES = 4;
    private static final int Y_TILES = 4;
    private static final int TILE_SIZE = 40;
    private static final int WIDTH = X_TILES * TILE_SIZE;
    private static final int HEIGHT = Y_TILES * TILE_SIZE;
    private static final int BORDER_SIZE = TILE_SIZE - 2;

    private Board board;
    private Scene scene;

    private Parent drawBoard() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);

        for (Tile tile : board) {
//            TileStackPane tileStackPane = new TileStackPane(tile);
            StackPane pane = new StackPane();
            Color color = Color.rgb(0, 0, 0, 0.35);
            BackgroundFill fill = new BackgroundFill(color, CornerRadii.EMPTY,
                    Insets.EMPTY);
            Background background = new Background(fill);
            pane.setStyle("-fx-background: #FFFFFF;");
            Rectangle border = new Rectangle(BORDER_SIZE, BORDER_SIZE);
            border.setStroke(Color.LIGHTGRAY);
            pane.getChildren().addAll(border);

            pane.setTranslateX(board.getTileX(tile) * TILE_SIZE);
            pane.setTranslateY(board.getTileY(tile) * TILE_SIZE);

            pane. setOnMouseClicked(e -> this.makeMove(tile));


            root.getChildren().add(pane);
        }

        return root;
    }

    private void makeMove(Tile tile) {
        board.makeMove(tile.getColor());
        scene.setRoot(drawBoard());
    }

    @Override
    public void start(Stage stage) throws Exception {
        board = new Board(X_TILES, Y_TILES, AbstractTileColorFactory.getFactory());
        scene = new Scene(drawBoard());

        stage.setScene(scene);
        stage.show();
    }

    public static void launchGame(String[] args) {
        launch(args);
    }
}
