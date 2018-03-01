package core.color;

import core.interfaces.TileColor;

public enum TileColorEnum implements TileColor {
    RED(255, 0, 0),
    GREEN(105, 225, 150),
    CYAN(140, 200, 230),
    YELLOW(230, 230, 140),
    PURPLE(210, 140, 230),
    TAN(230, 230, 200);

    private int red;
    private int green;
    private int blue;

    private TileColorEnum(int red, int green, int blue) {
        this.setRed(red);
        this.setGreen(green);
        this.setBlue(blue);
    }

    private void setRed(int red) {
        this.red = red;
    }

    @Override
    public int getRed() {
        return this.red;
    }
    public void setGreen(int green) {
        this.green = green;
    }

    @Override
    public int getGreen() {
        return this.green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    @Override
    public int getBlue() {
        return this.blue;
    }

    @Override
    public String toString() {
        return String.valueOf(super.name().charAt(0));
    }
}
