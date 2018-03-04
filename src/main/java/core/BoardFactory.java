package core;

import core.interfaces.Board2D;
import core.interfaces.TileFillGenerator;

public class BoardFactory {
    public static Board2D getBoard(int rowCount, int colCount, TileFillGenerator fillGenerator) {
        // @todo
        return new Board2DArray(rowCount, colCount, fillGenerator);
    }
}
