package core;

import core.config.Difficulty;
import core.config.GameStatus;
import core.interfaces.Board2D;
import core.interfaces.GamePlayMessage;
import core.interfaces.TileFill;
import core.interfaces.TileFillGenerator;

public abstract class AbstractBoard2D implements Board2D {
    private final int rowCount;
    private final int colCount;
    private final TileFillGenerator fillGenerator;

    private int moveCount;
    private TileFill currentFill;

    AbstractBoard2D(int rowCount, int colCount, TileFillGenerator fillGenerator) {
        // @todo Validate x/y.
        // @todo remove public
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.fillGenerator = fillGenerator;
        this.moveCount = 0;
    }

    @Override
    public int getRowCount() {
        return this.rowCount;
    }

    @Override
    public int getColCount() {
        return this.colCount;
    }

    @Override
    public TileFillGenerator getFillGenerator() {
        return this.fillGenerator;
    }
    @Override
    public TileFill getCurrentFill() {
        return this.currentFill;
    }

    void setCurrentFill(TileFill currentFill) {
        this.currentFill = currentFill;
    }

    @Override
    public int getMoveCount() {
        return this.moveCount;
    }

    @Override
    public void makeMove(TileFill tileFill) {
        this.addMoveCount();
        this.setCurrentFill(tileFill);
        this.captureTiles();
    }


    abstract void captureTiles();

    private void addMoveCount() {
        this.moveCount++;
    }

}
