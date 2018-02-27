package core;

import core.color.TileColor;

public class Tile {
    private int position;
    private TileColor color;

    private boolean captured;

    public Tile(int position, TileColor color, boolean captured) {
        this.setPosition(position);
        this.setColor(color);
        this.setCaptured(captured);
    }

    public int getPosition() {
        return this.position;
    }

    private void setPosition(int position) {
        this.position = position;
    }

    public TileColor getColor() {
        return this.color;
    }

    void setColor(TileColor color) {
        this.color = color;
    }

    public boolean isCaptured() {
        return this.captured;
    }

    public void setCaptured(boolean captured) {
        this.captured = captured;
    }
}
