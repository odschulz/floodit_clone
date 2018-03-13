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
    private final int maxMoves;

    private int moveCount;
    private TileFill currentFill;
    private boolean isCompleted;
    private GamePlayMessage messages;

    AbstractBoard2D(int rowCount, int colCount, TileFillGenerator fillGenerator, int maxMoves) {
        // @todo Validate x/y.
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.fillGenerator = fillGenerator;
        this.moveCount = 0;
        // @todo: validate maxMoves.
        this.maxMoves = maxMoves;
        // @todo fix multiple constructors.
        this.setMessages(new DefaultGamePlayMessage());
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
    public final void makeMove(TileFill tileFill) {
        this.addMoveCount();
        this.setCurrentFill(tileFill);
        this.captureTiles();
    }

    @Override
    public boolean isCompleted() {
        return this.isCompleted;
    }

    void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }

    @Override
    public final GameStatus getGameStatus() {
        int currentMoveCount = this.getMoveCount();
        int maxMoves = this.getMaxMoves();
        if(this.isCompleted() && currentMoveCount <= maxMoves) {
            return GameStatus.WON;
        } else if (currentMoveCount < maxMoves) {
            return GameStatus.IN_PROGRESS;
        }

        return GameStatus.LOST;
    }

    GamePlayMessage getMessages() {
        return this.messages;
    }

    private void setMessages(GamePlayMessage messages) {
        this.messages = messages;
    }

    @Override
    public final String getGameStatusMessage() {
        GameStatus status = this.getGameStatus();
        String message;

        switch (status) {
            case WON:
                message = this.getMessages().gameWinMessage(this.getMoveCount(), this.getMaxMoves());
                break;
            case LOST:
                message = this.getMessages().gameLoseMessage(this.getMoveCount(), this.getMaxMoves());
                break;
            case IN_PROGRESS:
                message = this.getMessages().gameInProgressMessage(this.getMoveCount(), this.getMaxMoves());
                break;
            default:
                message = null;
                break;
        }

        return message;
    }

    int getMaxMoves() {
        return this.maxMoves;
    }

    abstract void captureTiles();

    private void addMoveCount() {
        this.moveCount++;
    }

}
