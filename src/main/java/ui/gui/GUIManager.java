package ui.gui;

import core.GameBoardFactory;
import core.Tile2D;
import core.config.Difficulty;
import core.config.GameStatus;
import core.interfaces.Board2D;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import core.interfaces.TileFill;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ui.gui.fills.TileFillColor;

public class GUIManager extends Application {

    private static final int TILE_SIZE = 30;
    private static final int BORDER_SIZE = TILE_SIZE - 1;
    private static final int BOARD_OFFSET = 30;
    private static final int COLOR_OPACITY = 1;
    private static final Color BORDER_COLOR = Color.LIGHTGRAY;

    private int movesCount;

    private Difficulty difficulty;

    private Board2D board;
    private Scene scene;
    private ChoiceBox<Difficulty> difficultySelect;
    private Button startGameButton;

    public static void startGame(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.movesCount = 0;
        Difficulty defaultDifficulty = Difficulty.MEDIUM;

        this.difficulty = defaultDifficulty;
        this.difficultySelect = new ChoiceBox<>();
        this.difficultySelect.getItems().addAll(Difficulty.values());
        this.difficultySelect.setValue(defaultDifficulty);
        this.difficultySelect.setTranslateX(TILE_SIZE);
        this.difficultySelect.setMaxWidth(TILE_SIZE * 5);
        this.difficultySelect.setOnAction(event -> setDifficulty(difficultySelect.getValue()));

        this.startGameButton = new Button();
        this.startGameButton.setText("New game");
        this.startGameButton.setTranslateX(TILE_SIZE * 6);
        this.startGameButton.setOnAction(event -> this.resetGame());

        this.setBoard();
        this.scene = new Scene(this.drawBoard());


        stage.setScene(scene);
        stage.show();
    }

    private void setBoard() {
        // @todo: Add factory for colors and ability for player to choose.
        // a palette.
        this.board = GameBoardFactory.getBoard(
                this.difficulty,
                TileFillColor.values());
    }

    private void resetGame() {
        this.movesCount = 0;
        this.setBoard();
        this.scene.setRoot(this.drawBoard());
    }

    private Parent drawBoard() {
        Boolean win = true;
        Pane root = new Pane();
        root.setPrefSize(
                (Difficulty.HARD.getRowCount() * TILE_SIZE) + (2 * BOARD_OFFSET),
                (Difficulty.HARD.getColCount() * TILE_SIZE) + (2 * BOARD_OFFSET)
        );
        root.getChildren().addAll(this.difficultySelect, this.startGameButton);

        for (Tile2D[] tileRow : this.board.getTiles()) {
            for (Tile2D tile : tileRow) {
                StackPane pane = new StackPane();
                TileFill tileColor = tile.getFill();
                Color color = Color.web(tileColor.getValue(), COLOR_OPACITY);
                Rectangle border = new Rectangle(BORDER_SIZE, BORDER_SIZE, color);
                border.setStroke(BORDER_COLOR);
                pane.getChildren().addAll(border);

                pane.setTranslateX(tile.getCol() * TILE_SIZE + BOARD_OFFSET);
                pane.setTranslateY(tile.getRow() * TILE_SIZE + BOARD_OFFSET);

                if (tile.getFill() != this.board.getCurrentFill()) {
                    pane.setOnMouseClicked(e -> this.makeMove(tile.getFill()));
                }

                root.getChildren().add(pane);
            }
        }

        Text text = new Text(this.board.getGameStatusMessage());
        text.setTranslateX(TILE_SIZE);
        text.setTranslateY((this.difficulty.getColCount() + 1.5) * TILE_SIZE );
        GameStatus status = this.board.getGameStatus();
        if (status == GameStatus.WON) {
            text.setFill(Color.GREEN);
        } else if(status == GameStatus.LOST) {
            text.setFill(Color.RED);
        }

        root.getChildren().add(text);

        return root;
    }

    private void makeMove(TileFill tileFill) {
        this.board.makeMove(tileFill);
        this.movesCount++;
        this.scene.setRoot(this.drawBoard());
    }

    private void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}