package ui.cli;

import core.Board;
import core.Tile;
import core.interfaces.TileFillGenerator;
import ui.cli.fills.AbstractTileFillGeneratorCLI;
import ui.gui.fills.AbstractTileFillGeneratorGUI;
import core.interfaces.TileFill;
import ui.cli.io.ConsoleReader;
import ui.cli.io.ConsoleWriter;
import ui.cli.interfaces.InputReader;
import ui.cli.interfaces.OutputWriter;

import java.util.HashMap;
import java.util.Map;

public class CLIManager {
    private static final String EXIT_COMMAND = "exit";
    private final InputReader reader;
    private final OutputWriter writer;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public CLIManager() {
        this.reader = new ConsoleReader();
        this.writer = new ConsoleWriter();
    }

    public void startGame() {
        int size = 4;
        boolean completed = false;
        TileFillGenerator fillGenerator = AbstractTileFillGeneratorCLI.getFactory();
        Board board = new Board(size, size, fillGenerator);
        Map<String, TileFill> colorCommandMapping = new HashMap<>();
        // @todo Command handling and difficulty manager.
        for (TileFill fill : fillGenerator.getAllTileColors()) {
            colorCommandMapping.put(fill.getValue(), fill);
        }

        while (true) {
            completed = this.drawBoard(board, size);

            if (completed) {
                this.writer.writeLine("Harasho, you get kebeb!");
                break;
            }
            this.writer.writeLine("Please choose a fills by typing the corresponding letter. To exit type: " + EXIT_COMMAND);
            String command = reader.readLine();

            if (command.equals(EXIT_COMMAND)) {
                this.writer.writeLine("Game ended!");
                break;
            }

            if (colorCommandMapping.containsKey(command)) {
                board.makeMove(colorCommandMapping.get(command));
            } else {
                this.writer.writeLine("Incorrect command");
            }

        }
    }

    private boolean drawBoard(Board board, int size) {
        TileFill tileFill = null;
        boolean completed = true;
        for (Tile tile : board) {
            if (tile.getPosition() != 0 && tile.getPosition() % size == 0) {
                this.writer.writeLine("");
            }

            if (tileFill != null && completed && tileFill != tile.getFill()) {
                completed = false;
            }

            if (tileFill == null) {
                tileFill = tile.getFill();
            }

            this.writer.write(String.format("%1$" + 3 + "s", tile.getFill().getValue()));
        }

        this.writer.writeLine("");

        return completed;
    }
}
