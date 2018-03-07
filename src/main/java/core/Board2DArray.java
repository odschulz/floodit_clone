package core;

import core.config.Direction2D;
import core.interfaces.Board2D;
import core.interfaces.TileFill;
import core.interfaces.TileFillGenerator;
import java.util.ArrayList;
import java.util.HashMap;

final class Board2DArray implements Board2D {
    private static final int START_TILE_ROW = 0;
    private static final int START_TILE_COL = 0;

    private final int rowCount;
    private final int colCount;
    private final TileFillGenerator fillGenerator;
    private final Tile2D[][] tiles;

    private TileFill currentFill;

    public Board2DArray(int rowCount, int colCount, TileFillGenerator fillGenerator) {
        // @todo Validate x/y.
        // @todo remove public
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.fillGenerator = fillGenerator;
        this.tiles = new Tile2D[rowCount][colCount];

        this.initBoard();
    }

    @Override
    public int getRowCount() {
        return this.rowCount;
    }

    @Override
    public int getColCount() {
        return this.colCount;
    }

    public TileFillGenerator getFillGenerator() {
        return this.fillGenerator;
    }

    public Tile2D[][] getTiles() {
        // @todo make deep copy
        return this.tiles.clone();
    }

    @Override
    public TileFill getCurrentFill() {
        return this.currentFill;
    }

    private void setCurrentFill(TileFill currentFill) {
        this.currentFill = currentFill;
    }

    @Override
    public void makeMove(TileFill tileFill) {
        Tile2D startingTile = this.getTiles()[START_TILE_ROW][START_TILE_COL];
        this.setCurrentFill(tileFill);
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
                new boolean[this.rowCount][this.colCount]);
    }

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
        if (!tile.isCaptured()) {
            tile.setCaptured(true);
        }

        if (tile.getFill() != this.getCurrentFill()) {
            tile.setFill(this.getCurrentFill());
        }
        visited[tile.getRow()][tile.getCol()] = true;

        for (Tile2D neighbour : this.getNeighbouringTiles(tile.getRow(), tile.getCol()).values()) {
            if ((neighbour.getFill() == this.getCurrentFill() || neighbour.isCaptured())
                    && !visited[neighbour.getRow()][neighbour.getCol()]) {
                this.recursiveTileCapture(neighbour, visited);
            }
        }
    }
}
