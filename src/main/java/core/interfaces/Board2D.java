package core.interfaces;

import core.Tile2D;

public interface Board2D {
    int getRowCount();

    int getColCount();

    TileFillGenerator getFillGenerator();

    public Tile2D[][] getTiles();

    TileFill getCurrentFill();

    void makeMove(TileFill tileFill);
}
