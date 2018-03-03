package core;

import core.config.NeighbourPoistion;
import core.interfaces.TileFill;
import core.interfaces.TileFillGenerator;

import java.util.Iterator;

public class BoardManager implements Iterable<Tile> {
    final private int xCount;
    final private int yCount;

    private TileFillGenerator fillGenerator;
    final private Tile[] tiles;
    private TileFill currentFill;

    public BoardManager(int x, int y, TileFillGenerator fillGenerator) {
        // @todo Refactor to make it work with different x and y.
        this.xCount = x;
        this.yCount = y;
        this.tiles = new Tile[x * y];
        this.setFillGenerator(fillGenerator);
        this.initBoard();
    }

    public int getXCount() {
        return this.xCount;
    }

    public int getYCount() {
        return this.yCount;
    }

    public int getSize() {
        return this.getXCount() * this.getYCount();
    }

    public TileFillGenerator getFillGenerator() {
        return this.fillGenerator;
    }

    private void setFillGenerator(TileFillGenerator fillGenerator) {
        this.fillGenerator = fillGenerator;
    }

    public TileFill getCurrentFill() {
        return this.currentFill;
    }

    private void setCurrentFill(TileFill currentFill) {
        this.currentFill = currentFill;
    }

    public int getTileX(Tile tile) {
        return tile.getPosition() % this.getXCount() + 1;
    }

    public int getTileY(Tile tile) {
        return tile.getPosition() / this.getYCount() + 1;
    }

    private void initBoard() {
        for (int i = 0; i < this.tiles.length; i++) {
            TileFill tileFill = this.getFillGenerator().getRandomTileFill();

            boolean captured = false;
            if (i == 0) {
                // Set current color to the one of the first element.
                this.setCurrentFill(tileFill);
                captured = true;
            } else if (tileFill == this.getCurrentFill()) {
                // Capture this tile if it has the same color as the current one
                // and at least one captured neighbour.
                for (NeighbourPoistion neighbourPoistion : NeighbourPoistion.values()) {
                    Tile neighbourTile = this.getNeighbouringTile(i, neighbourPoistion);
                    if (neighbourTile != null && neighbourTile.isCaptured()) {
                        captured = true;
                        break;
                    }
                }
            }
            this.tiles[i] = new Tile(i, tileFill, captured);
        }
    }

    public void makeMove(TileFill tileFill) {
        this.setCurrentFill(tileFill);
        this.tiles[0].setFill(tileFill);
        this.recursiveTileCapture(this.tiles[0], new boolean[this.getSize()]);
    }

    /**
     * Capture the passed tile and recursively traverse and capture its
     * neighbouring tiles.
     *
     * @param tile    the Tile to capture, the method will recursively traverse
     *                through all of its neighbouring tiles and capture them
     *                if they have the same as the current color
     * @param visited flag all visited nodes to avoid recursive calls
     *
     * @see NeighbourPoistion
     */
    private void recursiveTileCapture(Tile tile, boolean[] visited) {
        if (!tile.isCaptured()) {
            tile.setCaptured(true);
        }

        if (tile.getFill() != this.getCurrentFill()) {
            tile.setFill(this.getCurrentFill());
        }
        visited[tile.getPosition()] = true;

        for (NeighbourPoistion neighbourPoistion : NeighbourPoistion.values()) {
            Tile neighbour = this.getNeighbouringTile(tile.getPosition(), neighbourPoistion);
            if (neighbour != null
                    && (neighbour.getFill() == this.getCurrentFill() || neighbour.isCaptured())
                    && !visited[neighbour.getPosition()]) {
                this.recursiveTileCapture(neighbour, visited);
            }
        }
    }

    private Tile getNeighbouringTile(int index, NeighbourPoistion poistion) {
        int sideCount = this.getXCount();
        int neighboutIndex = -1;
        switch (poistion) {
            case TOP:
                neighboutIndex = index - sideCount;
                break;
            case BOTTOM:
                neighboutIndex = index + sideCount;
                break;
            case LEFT:
                if (index % sideCount == 0) {
                    neighboutIndex = -1;
                } else {
                    neighboutIndex = index - 1;
                }
                break;
            case RIGHT:
                if ((index + 1) % sideCount == 0) {
                    neighboutIndex = -1;
                } else {
                    neighboutIndex = index + 1;
                }
                break;
            default:
                break;
        }

        return (neighboutIndex > -1 && neighboutIndex < this.getSize()) ?
                this.tiles[neighboutIndex] :
                null;
    }

    @Override
    public Iterator<Tile> iterator() {
        return new TileIterator();
    }

    private final class TileIterator implements Iterator<Tile> {

        private int index;

        TileIterator() {
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return this.index < tiles.length;
        }

        @Override
        public Tile next() {
            Tile currentTile = tiles[this.index];
            this.index++;
            return currentTile;
        }
    }
}
