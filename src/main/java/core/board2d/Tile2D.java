package core.board2d;

import core.interfaces.TileFill;

final public class Tile2D {
    private final int row;
    private final int col;

    private TileFill fill;
    private boolean captured;

    Tile2D(int row, int col, TileFill fill, boolean captured) {
        this.row = row;
        this.col = col;
        this.setFill(fill);
        this.setCaptured(captured);
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public TileFill getFill() {
        return this.fill;
    }

    void setFill(TileFill fill) {
        this.fill = fill;
    }

    public boolean isCaptured() {
        return this.captured;
    }

    void setCaptured(boolean captured) {
        this.captured = captured;
    }
}
