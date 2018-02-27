package core;

import core.color.TileColor;
import core.color.TileColorFactory;

import java.util.Iterator;

public class Board implements Iterable<Tile> {
    final private int sideCount;
    private TileColorFactory colorFactory;
    final private Tile[] tiles;
    private TileColor currentColor;

    public Board(int sideCount, TileColorFactory colorFactory) {
        // @todo validate size.
        this.sideCount = sideCount;
        this.tiles = new Tile[sideCount * sideCount];
        this.setColorFactory(colorFactory);
        this.initBoard();
    }

    public int getSideCount() {
        return this.sideCount;
    }

    public int getSize() {
        int sideCount = this.getSideCount();
        return sideCount * sideCount;
    }

    public TileColorFactory getColorFactory() {
        return this.colorFactory;
    }

    private void setColorFactory(TileColorFactory colorFactory) {
        this.colorFactory = colorFactory;
    }

    public TileColor getCurrentColor() {
        return this.currentColor;
    }

    private void setCurrentColor(TileColor currentColor) {
        this.currentColor = currentColor;
    }

    public void makeMove(TileColor color) {
        this.setCurrentColor(color);
    }

    private void initBoard() {
        for (int i = 0; i < this.tiles.length; i++) {
            TileColor color = this.getColorFactory().getRandomTileColor();

            boolean captured = false;
            if (i == 0) {
                this.setCurrentColor(color);
                captured = true;
            }
            this.tiles[i] = new Tile(i, color, captured);
        }
    }

    private Tile getNeighbouringTile(int index, NeighbourPoistion poistion) {
        int sideCount = this.getSideCount();
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
            TileColor currentColor = getCurrentColor();
            if (currentTile.isCaptured()) {
                currentTile.setColor(currentColor);
            } else {
                for (NeighbourPoistion position : NeighbourPoistion.values()) {
                    Tile neighbourTile = getNeighbouringTile(this.index, position);
                    if (neighbourTile != null &&
                            neighbourTile.isCaptured() &&
                            currentTile.getColor() == currentColor) {
                        currentTile.setColor(currentColor);
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
