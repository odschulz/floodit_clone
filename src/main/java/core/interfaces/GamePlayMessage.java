package core.interfaces;

public interface GamePlayMessage {
    String gameLoseMessage(int movesMade, int movesLeft);

    String gameWinMessage(int movesMade, int movesLeft);

    String gameInProgressMessage(int movesMade, int movesLeft);
}


