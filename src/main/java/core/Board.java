package core;

import core.interfaces.TileFill;
import core.interfaces.TileFillGenerator;

import java.util.Iterator;

public class Board implements Iterable<Tile> {
    final private int xCount;
    final private int yCount;

    private TileFillGenerator fillGenerator;
    final private Tile[] tiles;
    private TileFill currentFill;

    public Board(int x, int y, TileFillGenerator fillGenerator) {
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

    public void makeMove(TileFill color) {
        this.setCurrentFill(color);
    }

    public int getTileX(Tile tile) {
        return tile.getPosition() % this.getXCount() + 1;
    }

    public int getTileY(Tile tile) {
        return tile.getPosition() / this.getYCount() + 1;
    }

    private void initBoard() {
        for (int i = 0; i < this.tiles.length; i++) {
            TileFill color = this.getFillGenerator().getRandomTileColor();

            boolean captured = false;
            if (i == 0) {
                this.setCurrentFill(color);
                captured = true;
            }
            this.tiles[i] = new Tile(i, color, captured);
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
                if ((index +1) % sideCount == 0) {
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
            TileFill currentFill = getCurrentFill();
            if (currentTile.isCaptured()) {
                currentTile.setFill(currentFill);
            } else {
                for (NeighbourPoistion position : NeighbourPoistion.values()) {
                    Tile neighbourTile = getNeighbouringTile(this.index, position);
                    if (neighbourTile != null &&
                            neighbourTile.isCaptured() &&
                            currentTile.getFill() == currentFill) {
                        currentTile.setFill(currentFill);
                        currentTile.setCaptured(true);
                        break;
                    }
                }
            }

            this.index++;
            return currentTile;
        }
    }
}
