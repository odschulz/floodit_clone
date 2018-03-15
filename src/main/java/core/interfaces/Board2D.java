package core.interfaces;

import core.board2d.Tile2D;
import core.config.GameStatus;

/**
 * The main object managing the game, representing a 2D board.
 */
public interface Board2D {

    /**
     * The total amount of rows.
     *
     * @return row count
     */
    int getRowCount();

    /**
     * The total amount of columns.
     *
     * @return col count
     */
    int getColCount();

    /**
     * A two-dimensional array representation of a board.
     *
     * @return all tiles from the board
     */
    public Tile2D[][] getTiles();

    /**
     * The currently active fill, matches the initial tile fill.
     *
     * @return current fill
     */
    TileFill getCurrentFill();

    /**
     * The amount of moves made from the start of the current game.
     *
     * @return number of moves made
     */
    int getMoveCount();

    /**
     * Perform a move on the board.
     *
     * @param tileFill the fill to which to change, will set current fill
     */
    void makeMove(TileFill tileFill);

    /**
     * If game is completed and all fills match.
     *
     * @return true if completed, otherwise false
     */
    boolean isCompleted();

    /**
     * Return a game status indicator of the current game.
     *
     * @return whether game is in progress/completion status
     */
    GameStatus getGameStatus();

    /**
     * The message related to the current status.
     *
     * @return informs the player of the current status
     */
    String getGameStatusMessage();
}
