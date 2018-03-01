package ui.gui.fills;

import core.interfaces.TileFill;

public enum TileFillColor implements TileFill {
    YELLOW("#FFBE0B"),
    ORANGE("#E36414"),
    RED("#89023E"),
    BLUE("#223A75"),
    TAN("#805E73"),
    PURPLE("#441151");

    private final String value;

    private TileFillColor(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.valueOf(super.name().charAt(0));
    }
}
