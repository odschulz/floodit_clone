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
    // @todo: Refactor hardcoded logic.
    private static final InputReader READER = new ConsoleReader();
    private static final OutputWriter WRITER = new ConsoleWriter();
    private static final String MESSAGE_END = "Game ended, you coward!";
    private static final String MESSAGE_ACT = "Please choose a fill by typing the corresponding letter. To exit type: " + EXIT_COMMAND;
    private static final String MESSAGE_WRONG_COMMAND = "Wrong command! Do 50 pushups and try again!";

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
                WRITER.writeLine(MESSAGE_ACT);
            }
            WRITER.writeLine(board.getGameStatusMessage());
            if (completed) {
                break;
            }
            String command = READER.readLine();

            if (command.equals(EXIT_COMMAND)) {
                WRITER.writeLine(MESSAGE_END);
                break;
            }

            if (tileFillCommandMapping.containsKey(command)) {
                board.makeMove(tileFillCommandMapping.get(command));
            } else {
                WRITER.writeLine(MESSAGE_WRONG_COMMAND);
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

                WRITER.write(String.format("%1$" + FILL_RENDER_PADDING + "s", tile.getFill().getValue()));
            }
            WRITER.writeLine("");
        }

        WRITER.writeLine("");
    }
}
