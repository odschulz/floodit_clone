package core.board2d;

import core.config.Direction2D;
import core.interfaces.TileFill;
import core.interfaces.TileFillGenerator;

import java.util.HashMap;

final class Board2DArray extends AbstractBoard2D {
    private static final int START_TILE_ROW = 0;
    private static final int START_TILE_COL = 0;

    private final Tile2D[][] tiles;

    Board2DArray(int rowCount, int colCount, TileFillGenerator fillGenerator, int maxMoves) {
        super(rowCount, colCount, fillGenerator, maxMoves);

        this.tiles = new Tile2D[rowCount][colCount];
        this.initBoard();
    }

    @Override
    public Tile2D[][] getTiles() {
        return this.tiles.clone();
    }
    @Override
    void captureTiles() {
        Tile2D startingTile = this.getTiles()[START_TILE_ROW][START_TILE_COL];
        this.recursiveTileCapture(startingTile, new boolean[this.getRowCount()][this.getColCount()]);
    }

    private void initBoard() {
        for (int row = 0; row < this.getRowCount(); row++) {
            for (int col = 0; col < this.getColCount(); col++) {
                TileFill tileFill = this.getFillGenerator().getRandomTileFill();

                boolean captured = false;
                if (row == START_TILE_ROW && col == START_TILE_COL) {
                    // Set the first element color as the current board color.
                    this.setCurrentFill(tileFill);
                    captured = true;
                }
                this.tiles[row][col] = new Tile2D(row, col, tileFill, captured);
            }
        }

        // Capture mark all captured tiles.
        this.recursiveTileCapture(
                this.tiles[START_TILE_ROW][START_TILE_COL],
                new boolean[this.getRowCount()][this.getColCount()]);
    }

    /**
     * Neighbouring tiles are all adjacent tiles in horizontal/vertical line.
     *
     * @param row Row number.
     * @param col Column number
     *
     * @return All found tiles indexed by their direction or empty Map.
     */
    private HashMap<Direction2D, Tile2D> getNeighbouringTiles(int row, int col) {
        HashMap<Direction2D, Tile2D> neighbourTiles = new HashMap<>();
        Tile2D[][] tiles = this.getTiles();
        for (Direction2D direction : Direction2D.values()) {
            int neighbourRow = row + direction.getRow();
            int neighbourCol = col + direction.getCol();
            if (neighbourRow >= 0 && neighbourRow < this.getRowCount()
                    && neighbourCol >= 0 && neighbourCol < this.getColCount()) {
                neighbourTiles.put(direction, tiles[neighbourRow][neighbourCol]);
            }
        }

        return neighbourTiles;
    }

    /**
     * Capture the passed tile2D and recursively traverse (DFS) and capture its
     * neighbouring tiles.
     *
     * @param tile    the Tile2D to capture, the method will recursively traverse
     *                through all of its neighbouring tiles and captuzre them
     *                if they have the same as the current color
     * @param visited flag all visited nodes to avoid recursive calls
     *
     * @see Direction2D
     */
    private void recursiveTileCapture(Tile2D tile, boolean[][] visited) {
        int tileRow = tile.getRow();
        int tileCol = tile.getCol();

        // This flag will be reset if at least one tile is found that has a
        // different color.
        if (tileRow == START_TILE_ROW && tileCol == START_TILE_COL) {
            this.setCompleted(true);
        }

        if (!tile.isCaptured()) {
            tile.setCaptured(true);
        }

        if (!tile.getFill().getValue().equals(this.getCurrentFill().getValue())) {
            tile.setFill(this.getCurrentFill());
        }
        visited[tileRow][tileCol] = true;

        for (Tile2D neighbour : this.getNeighbouringTiles(tileRow, tileCol).values()) {
            if ((neighbour.getFill().getValue().equals(this.getCurrentFill().getValue()) || neighbour.isCaptured())) {
                if (!visited[neighbour.getRow()][neighbour.getCol()]) {
                    this.recursiveTileCapture(neighbour, visited);
                }
            } else {
                this.setCompleted(false);
            }
        }
    }
}
