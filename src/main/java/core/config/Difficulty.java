package core.config;

public enum Difficulty {
    EASY(6, 6, 10), MEDIUM(14, 14, 25), HARD(26, 26, 46);

    private final int xCount;
    private final int yCount;
    private final int maxMoves;

    Difficulty(int xCount, int yCount, int maxMoves) {
        this.xCount = xCount;
        this.yCount = yCount;
        this.maxMoves = maxMoves;
    }

    public int getxCount() {
        return xCount;
    }

    public int getyCount() {
        return yCount;
    }

    public int getMaxMoves() {
        return maxMoves;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" (%dx%d)", this.getxCount(), this.getyCount());
    }
}