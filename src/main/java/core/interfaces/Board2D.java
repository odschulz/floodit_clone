package core.interfaces;

import core.board2d.Tile2D;
import core.config.GameStatus;

public interface Board2D {
    int getRowCount();

    int getColCount();

    public Tile2D[][] getTiles();

    TileFill getCurrentFill();

    int getMoveCount();

    void makeMove(TileFill tileFill);

    boolean isCompleted();

    GameStatus getGameStatus();

    String getGameStatusMessage();
}
