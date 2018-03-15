package core.interfaces;

/**
 * Used to generate in-game messages informing on progress.
 */
public interface GamePlayMessage {

    /**
     * Message to show lost game.
     *
     * @param movesMade currently made moves by player
     * @param maxMoves  maximum allowed moves for winning the game
     * @return          message to inform of loss
     */
    String gameLoseMessage(int movesMade, int maxMoves);

    /**
     * Message to show won game.
     *
     * @param movesMade currently made moves by player
     * @param maxMoves  maximum allowed moves for winning the game
     * @return          message to inform of win
     */
    String gameWinMessage(int movesMade, int maxMoves);

    /**
     * Message to inform of current progress.
     *
     * @param movesMade currently made moves by player
     * @param maxMoves  maximum allowed moves for winning the game
     * @return          message to inform of progress
     */
    String gameInProgressMessage(int movesMade, int maxMoves);
}


