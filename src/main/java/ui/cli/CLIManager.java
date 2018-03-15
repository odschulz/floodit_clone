package ui.cli;

import core.board2d.Board2DFactory;
import core.board2d.Tile2D;
import core.config.Difficulty;
import core.interfaces.Board2D;
import core.interfaces.TileFill;
import ui.cli.fills.TileFillDigit;
import ui.cli.io.ConsoleReader;
import ui.cli.io.ConsoleWriter;
import ui.cli.interfaces.InputReader;
import ui.cli.interfaces.OutputWriter;

import java.util.HashMap;
import java.util.Map;

public class CLIManager {
    private static final String EXIT_COMMAND = "exit";
    private static final Difficulty DEFAULT_DIFFICULTY = Difficulty.EASY;
    private static final int FILL_RENDER_PADDING = 3;
    private static final InputReader reader = new ConsoleReader();
    private static final OutputWriter writer = new ConsoleWriter();

    public static void startGame(String[] args) {
        Board2D board = Board2DFactory.getBoard(DEFAULT_DIFFICULTY, TileFillDigit.values());
        Map<String, TileFill> tileFillCommandMapping = new HashMap<>();
        for (TileFill fill : TileFillDigit.values()) {
            tileFillCommandMapping.put(fill.getValue(), fill);
        }

        while (true) {
            drawBoard(board);
            boolean completed = board.isCompleted();
            if (!completed) {
                writer.writeLine("Please choose a fill by typing the corresponding letter. To exit type: " + EXIT_COMMAND);
            }
            writer.writeLine(board.getGameStatusMessage());
            if (completed) {
                break;
            }
            String command = reader.readLine();

            if (command.equals(EXIT_COMMAND)) {
                writer.writeLine("Game ended, you coward!");
                break;
            }

            if (tileFillCommandMapping.containsKey(command)) {
                board.makeMove(tileFillCommandMapping.get(command));
            } else {
                writer.writeLine("Incorrect command");
            }

        }
    }

    private static void drawBoard(Board2D board) {
        TileFill tileFill = null;
        for (Tile2D[] tileRow : board.getTiles()) {
            for (Tile2D tile : tileRow) {
                if (tileFill == null) {
                    tileFill = tile.getFill();
                }

                writer.write(String.format("%1$" + FILL_RENDER_PADDING + "s", tile.getFill().getValue()));
            }
            writer.writeLine("");
        }

        writer.writeLine("");
    }
}
