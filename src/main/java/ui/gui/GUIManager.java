package ui.gui;

import core.BoardManager;
import core.Tile;
import core.config.Difficulty;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
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

    private static final int TILE_SIZE = 30;
    private static final int BORDER_SIZE = TILE_SIZE - 1;

    private int movesCount;

    private Difficulty difficulty;

    private BoardManager boardManager;
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
        this.boardManager = new BoardManager(this.difficulty.getxCount(), this.difficulty.getyCount(), AbstractTileFillGeneratorGUI.getFactory());
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
                (Difficulty.HARD.getxCount() * TILE_SIZE) + 2 * TILE_SIZE,
                (Difficulty.HARD.getyCount() * TILE_SIZE) + 2 * TILE_SIZE
        );
        root.getChildren().addAll(this.difficultySelect, this.startGameButton);

        for (Tile tile : this.boardManager) {
            StackPane pane = new StackPane();
            TileFill tileColor = tile.getFill();
            Color color = Color.web(tileColor.getValue(), 1);
            Rectangle border = new Rectangle(BORDER_SIZE, BORDER_SIZE, color);
            // @todo Fix border.
            border.setStroke(Color.LIGHTGRAY);
            pane.getChildren().addAll(border);

            pane.setTranslateX(this.boardManager.getTileX(tile) * TILE_SIZE);
            pane.setTranslateY(this.boardManager.getTileY(tile) * TILE_SIZE);

            if (tile.getFill() != this.boardManager.getCurrentFill()) {
                pane.setOnMouseClicked(e -> this.makeMove(tile));
                win = false;
            }

            root.getChildren().add(pane);
        }

        Color color = null;
        if (win) {
            color = Color.GREEN;
        } else if (this.movesCount >= this.difficulty.getMaxMoves()) {
            color = Color.RED;
        }
        Text text = new Text();
        text.setTranslateX(TILE_SIZE);
        text.setTranslateY((this.difficulty.getyCount() + 1.5) * TILE_SIZE );
        String message = String.format("Moves %d/%d", this.movesCount, this.difficulty.getMaxMoves());
        if (this.movesCount >= this.difficulty.getMaxMoves()) {
            text.setText(message + ". Sorry, you should eat more kebeb!");
            text.setFill(Color.RED);
        } else if (win) {
            text.setText(message + ". Niceuuu, you get kebeb!.");
            text.setFill(Color.GREEN);
        }  else {
            text.setText(message);
        }

        root.getChildren().add(text);

        return root;
    }

    private void makeMove(Tile tile) {
        this.boardManager.makeMove(tile.getFill());
        this.movesCount++;
        this.scene.setRoot(this.drawBoard());
    }

    private void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}