package ui.cli.io;

import ui.cli.interfaces.OutputWriter;

public class ConsoleWriter implements OutputWriter {
    @Override
    public void write(String output) {
        System.out.print(output);
    }

    @Override
    public void writeLine(String output) {
        System.out.println(output);
    }
}
