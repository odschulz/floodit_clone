package core.interfaces;

import core.Tile2D;
import core.config.GameStatus;

public interface Board2D {
    int getRowCount();

    int getColCount();

    TileFillGenerator getFillGenerator();

    public Tile2D[][]   getTiles();

    TileFill getCurrentFill();

    int getMoveCount();

    void makeMove(TileFill tileFill);
}
