package ui.cli.fills;

import core.interfaces.TileFill;

public enum TileFillDigit implements TileFill {
    ONE, TWO, THREE, FOUR, FIVE, SIX;


    @Override
    public String getValue() {
        return String.valueOf(super.ordinal());
    }
}

