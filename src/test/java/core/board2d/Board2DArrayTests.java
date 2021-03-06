package core.board2d;

import core.config.Direction2D;
import core.interfaces.Board2D;
import core.interfaces.TileFill;
import core.interfaces.TileFillGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class Board2DArrayTests {
    private static final int ROW_SIZE = 3;
    private static final int COL_SIZE = 3;
    private static final int MAX_MOVES = 5;
    private static final String FILL_A_VAL = "A";
    private static final String FILL_B_VAL = "B";
    private static final String FILL_C_VAL = "C";
    private static final int LEFT_CORNER_TILE_ROW = 0;
    private static final int LEFT_CORNER_TILE_COL = 0;
    private static final int MIDDLE_TILE_ROW = 1;
    private static final int MIDDLE_TILE_COL = 1;

    private TileFill fillA;
    private TileFill fillB;
    private TileFill fillC;
    private TileFill[][] tileFills;
    private boolean[][] tileFillsToBeCaptured;
    private TileFill startFill;
    private TileFill changeFill;
    private TileFill lastFill;
    private TileFillGenerator fillGenerator;
    private Board2D board;

    @Before
    public void setUp() {
        this.fillA = Mockito.mock(TileFill.class);
        Mockito.when(fillA.getValue()).thenReturn(FILL_A_VAL);
        this.fillB = Mockito.mock(TileFill.class);
        Mockito.when(fillB.getValue()).thenReturn(FILL_B_VAL);
        this.fillC = Mockito.mock(TileFill.class);
        Mockito.when(fillC.getValue()).thenReturn(FILL_C_VAL);

        this.startFill = fillA;
        this.changeFill = fillB;
        this.lastFill = fillC;

        /*
         * Mock a TileFill generator so that the constructed board has a
         * predefined structure as the array bellow.
         */
        this.tileFills = new TileFill[][] {
                {fillA, fillB, fillB},
                {fillC, fillB, fillC},
                {fillB, fillB, fillB},
        };
        this.tileFillsToBeCaptured = new boolean[][] {
                {true, true, true},
                {false, true, false},
                {true, true, true},
        };
        this.fillGenerator = Mockito.mock(TileFillGenerator.class);
        Mockito.when(this.fillGenerator.getRandomTileFill()).thenAnswer(new Answer<Object>() {
            private int position = 0;

            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                TileFill fill = tileFills[this.position / ROW_SIZE][this.position % COL_SIZE];
                this.position++;

                return fill;
            }
        });

        this.board = new Board2DArray(ROW_SIZE, COL_SIZE, fillGenerator, MAX_MOVES);
    }

    @Test
    public void initBoardCapturedTest() {
        TileFill currentFill = this.board.getCurrentFill();
        Tile2D[][] tiles = this.board.getTiles();
        for (int row = 0; row < this.board.getRowCount(); row++) {
            for (int col = 0; col < this.board.getColCount(); col++) {
                if (tiles[row][col].getFill().getValue().equals(currentFill.getValue())) {
                    Assert.assertTrue(String.format("Tile at row %d and col %d should be captured.", row, col), tiles[row][col].isCaptured());
                }
            }
        }
    }

    @Test
    public void initBoardCurrentFillTest() {
        Assert.assertEquals(
                "Current tile file should be the same as the first tile.",
                this.board.getTiles()[0][0].getFill().getValue(),
                this.board.getCurrentFill().getValue()
        );
    }

    @Test
    public void getNeighbouringTilesMiddle() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = Board2DArray.class.getDeclaredMethod("getNeighbouringTiles", int.class, int.class);
        method.setAccessible(true);
        HashMap<Direction2D, Tile2D> neighbourTiles = (HashMap<Direction2D, Tile2D>) method.invoke(
                this.board, LEFT_CORNER_TILE_ROW, LEFT_CORNER_TILE_COL);

        Assert.assertEquals("Incorrect amount of neighbouring tiles", 2, neighbourTiles.size());

        Assert.assertTrue("Right neighbour tile not found.", neighbourTiles.containsKey(Direction2D.RIGHT));
        Tile2D rightNeighbour = neighbourTiles.get(Direction2D.RIGHT);
        Assert.assertEquals("Right neighbour has incorrect row.", LEFT_CORNER_TILE_ROW, rightNeighbour.getRow());
        Assert.assertEquals("Right neighbour has incorrect col.", LEFT_CORNER_TILE_COL + 1, rightNeighbour.getCol());

        Assert.assertTrue("Bottom neighbour tile not found.", neighbourTiles.containsKey(Direction2D.BOTTOM));
        Tile2D bottomNeighbour = neighbourTiles.get(Direction2D.BOTTOM);
        Assert.assertEquals("Bottom neighbour has incorrect row.", LEFT_CORNER_TILE_ROW + 1, bottomNeighbour.getRow());
        Assert.assertEquals("Bottom neighbour has incorrect col.", LEFT_CORNER_TILE_COL, bottomNeighbour.getCol());
    }

    @Test
    public void getNeighbouringTilesCorner() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = Board2DArray.class.getDeclaredMethod("getNeighbouringTiles", int.class, int.class);
        method.setAccessible(true);
        HashMap<Direction2D, Tile2D> neighbourTiles = (HashMap<Direction2D, Tile2D>) method.invoke(
                this.board, MIDDLE_TILE_ROW, MIDDLE_TILE_COL);

        Assert.assertEquals("Incorrect amount of neighbouring tiles", 4, neighbourTiles.size());

        Assert.assertTrue("Right neighbour tile not found.", neighbourTiles.containsKey(Direction2D.RIGHT));
        Tile2D rightNeighbour = neighbourTiles.get(Direction2D.RIGHT);
        Assert.assertEquals("Right neighbour has incorrect row.", MIDDLE_TILE_ROW, rightNeighbour.getRow());
        Assert.assertEquals("Right neighbour has incorrect col.", MIDDLE_TILE_COL + 1, rightNeighbour.getCol());

        Assert.assertTrue("Bottom neighbour tile not found.", neighbourTiles.containsKey(Direction2D.BOTTOM));
        Tile2D bottomNeighbour = neighbourTiles.get(Direction2D.BOTTOM);
        Assert.assertEquals("Bottom neighbour has incorrect row.", MIDDLE_TILE_ROW + 1, bottomNeighbour.getRow());
        Assert.assertEquals("Bottom neighbour has incorrect col.", MIDDLE_TILE_COL, bottomNeighbour.getCol());

        Assert.assertTrue("Left neighbour tile not found.", neighbourTiles.containsKey(Direction2D.LEFT));
        Tile2D leftNeighbour = neighbourTiles.get(Direction2D.LEFT);
        Assert.assertEquals("Left neighbour has incorrect row.", MIDDLE_TILE_ROW, leftNeighbour.getRow());
        Assert.assertEquals("Left neighbour has incorrect col.", MIDDLE_TILE_COL - 1, leftNeighbour.getCol());

        Assert.assertTrue("Top neighbour tile not found.", neighbourTiles.containsKey(Direction2D.TOP));
        Tile2D topNeighbour = neighbourTiles.get(Direction2D.TOP);
        Assert.assertEquals("Top neighbour has incorrect row.", MIDDLE_TILE_ROW - 1, topNeighbour.getRow());
        Assert.assertEquals("Top neighbour has incorrect col.", MIDDLE_TILE_COL, topNeighbour.getCol());
    }

    @Test
    public void makeMoveTest() {
        this.board.makeMove(this.changeFill);
        for (Tile2D[] tilesRow : this.board.getTiles()) {
            for (Tile2D tile : tilesRow) {
                if (this.tileFillsToBeCaptured[tile.getRow()][tile.getCol()]) {
                    Assert.assertTrue("Tile has not been captured.", tile.isCaptured());
                    Assert.assertEquals("Tile color should be the same as the one made in this move.", this.changeFill.getValue(), tile.getFill().getValue());
                } else {
                    Assert.assertFalse("Tile should not be captured.", tile.isCaptured());
                    Assert.assertNotEquals("Tile color should be different than the one that was made in this move.",  this.changeFill.getValue(), tile.getFill().getValue());
                }
            }
        }
    }

    @Test
    public void makeMoveDownTest() {
        this.board.makeMove(this.lastFill);
        Tile2D[][] tiles = board.getTiles();

        int capturedRow = 1;
        int capturedCol = 0;
        int notCapturedRow = 0;
        int notCapturedCol = 1;

        Assert.assertTrue(
                String.format(
                        "Tile at row %d and col %d should be captured.",
                        capturedRow,
                        capturedCol
                ),
                tiles[capturedRow][capturedCol].isCaptured()
        );

        Assert.assertFalse(
                String.format(
                        "Tile at row %d and col %d should not be captured.",
                        notCapturedRow,
                        notCapturedCol
                ),
                tiles[notCapturedRow][notCapturedCol].isCaptured()
        );
    }

    @Test
    public void testIsCompleted() {
        this.board.makeMove(this.changeFill);
        this.board.makeMove(this.lastFill);
        Assert.assertTrue("Board should be completed.", this.board.isCompleted());
    }

    @Test
    public void testIsNotCompleted() {
        this.board.makeMove(this.changeFill);
        Assert.assertFalse("Board should not be completed.", this.board.isCompleted());
    }

}
