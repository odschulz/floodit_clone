package core.board2d;

import core.config.Difficulty;
import core.fills.TileFillGeneratorFactory;
import core.interfaces.Board2D;
import core.interfaces.TileFill;
import core.interfaces.TileFillGenerator;

public class Board2DFactory {
    public static Board2D getBoard(Difficulty difficulty, TileFill[] tileFills) {
        TileFillGenerator fillGenerator = TileFillGeneratorFactory.getFillGenerator(tileFills);
        return new Board2DArray(difficulty.getRowCount(), difficulty.getColCount(), fillGenerator, difficulty.getMaxMoves());
    }
}
