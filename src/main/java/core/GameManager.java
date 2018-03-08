package core;

import core.config.Difficulty;
import core.fills.TileFillGeneratorFactory;
import core.interfaces.Board2D;
import core.interfaces.TileFill;
import core.interfaces.TileFillGenerator;

public class GameManager {
    public static Board2D getBoard(Difficulty difficulty, TileFill[] tileFills) {
        // @todo
        TileFillGenerator fillGenerator = TileFillGeneratorFactory.getFillGenerator(tileFills);
        return new Board2DArray(difficulty.getRowCount(), difficulty.getColCount(), fillGenerator);
    }
}
