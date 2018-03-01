package core;

import core.interfaces.TileFill;

public class Tile {
    private int position;
    private TileFill fill;

    private boolean captured;

    public Tile(int position, TileFill fill, boolean captured) {
        this.setPosition(position);
        this.setFill(fill);
        this.setCaptured(captured);
    }

    public int getPosition() {
        return this.position;
    }

    private void setPosition(int position) {
        this.position = position;
    }

    public TileFill getFill() {
        return this.fill;
    }

    void setFill(TileFill fill) {
        this.fill = fill;
    }

    public boolean isCaptured() {
        return this.captured;
    }

    public void setCaptured(boolean captured) {
        this.captured = captured;
    }
}
