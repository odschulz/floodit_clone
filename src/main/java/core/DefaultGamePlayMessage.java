package core;

import core.interfaces.GamePlayMessage;

public class DefaultGamePlayMessage implements GamePlayMessage {

    @Override
    public String gameLoseMessage(int movesMade, int maxMoves) {
        return String.format("Moves %d/%d. Sorry, not stronk enough, you should eat more kebeb!", movesMade, maxMoves);
    }

    @Override
    public String gameWinMessage(int movesMade, int maxMoves) {
        return String.format("Moves %d/%d. Niceuuu!", movesMade, maxMoves);
    }

    @Override
    public String gameInProgressMessage(int movesMade, int maxMoves) {
        return String.format("Moves %d/%d.", movesMade, maxMoves);
    }
}
