import ui.cli.CLIManager;
import ui.gui.GUIManager;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (Arrays.asList(args).contains("cli")) {
            CLIManager.startGame(args);
        } else {
            GUIManager.startGame(args);
        }
    }
}
