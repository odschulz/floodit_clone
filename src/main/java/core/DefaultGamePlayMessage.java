package core;

import core.interfaces.GamePlayMessage;

public class DefaultGamePlayMessage implements GamePlayMessage {

    @Override
    public String gameLoseMessage(int movesMade, int movesLeft) {
        return String.format("Moves %d/%d. Sorry, you should eat more kebeb!", movesMade, movesLeft);
    }

    @Override
    public String gameWinMessage(int movesMade, int movesLeft) {
        return String.format("Moves %d/%d.", movesMade, movesLeft);
    }

    @Override
    public String gameInProgressMessage(int movesMade, int movesLeft) {
        return String.format("Moves %d/%d. Niceuuu!.", movesMade, movesLeft);
    }
}
