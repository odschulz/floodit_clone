package ui.cli;

import core.BoardFactory;
import core.Tile2D;
import core.interfaces.Board2D;
import core.interfaces.TileFillGenerator;
import ui.cli.fills.AbstractTileFillGeneratorCLI;
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

    public CLIManager() {
        this.reader = new ConsoleReader();
        this.writer = new ConsoleWriter();
    }

    public void startGame() {
        int size = 2;
        boolean completed = false;
        TileFillGenerator fillGenerator = AbstractTileFillGeneratorCLI.getFactory();
        Board2D board = BoardFactory.getBoard(size, size, fillGenerator);
        Map<String, TileFill> tileFillCommandMapping = new HashMap<>();
        // @todo Command handling and difficulty manager.
        for (TileFill fill : fillGenerator.getAllTileFills()) {
            tileFillCommandMapping.put(fill.getValue(), fill);
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

            if (tileFillCommandMapping.containsKey(command)) {
                board.makeMove(tileFillCommandMapping.get(command));
            } else {
                this.writer.writeLine("Incorrect command");
            }

        }
    }

    private boolean drawBoard(Board2D board, int size) {
        TileFill tileFill = null;
        boolean completed = true;
        for (Tile2D[] tileRow : board.getTiles()) {
            for (Tile2D tile : tileRow) {
                if (tileFill != null && completed && tileFill != tile.getFill()) {
                    completed = false;
                }

                if (tileFill == null) {
                    tileFill = tile.getFill();
                }

                this.writer.write(String.format("%1$" + 3 + "s", tile.getFill().getValue()));
            }
            this.writer.writeLine("");
        }

        this.writer.writeLine("");

        return completed;
    }
}
