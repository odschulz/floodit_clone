package core.config;

/**
 * Predefined difficulty rules.
 */
public enum Difficulty {
    EASY(6, 6, 10), MEDIUM(14, 14, 25), HARD(26, 26, 46);

    private final int rowCount;
    private final int colCount;
    private final int maxMoves;

    Difficulty(int rowCount, int colCount, int maxMoves) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.maxMoves = maxMoves;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColCount() {
        return colCount;
    }

    public int getMaxMoves() {
        return maxMoves;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" (%dx%d)", this.getColCount(), this.getRowCount());
    }
}