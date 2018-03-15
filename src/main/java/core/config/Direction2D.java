package core.config;

/**
 * Two-dimensional matrix directions to show connection between elements.
 */
public enum Direction2D {
    RIGHT(0, 1), BOTTOM(1, 0), LEFT(0, -1), TOP(-1, 0);

    private final int row;
    private final int col;

    private Direction2D(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }
}
