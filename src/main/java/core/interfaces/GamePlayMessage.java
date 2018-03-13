package core.interfaces;

public interface GamePlayMessage {
    String gameLoseMessage(int movesMade, int maxMoves);

    String gameWinMessage(int movesMade, int maxMoves);

    String gameInProgressMessage(int movesMade, int maxMoves);
}


