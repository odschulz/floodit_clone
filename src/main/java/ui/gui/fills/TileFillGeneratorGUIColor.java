package ui.gui.fills;

import core.interfaces.TileFill;

import java.util.Random;

public class TileFillGeneratorGUIColor extends AbstractTileFillGeneratorGUI {

    private Random random;

    TileFillGeneratorGUIColor() {
        this.setRandom(new Random());
    }

    private Random getRandom() {
        return this.random;
    }

    private void setRandom(Random random) {
        this.random = random;
    }

    @Override
    public TileFill getRandomTileFill() {
        TileFillColor[] colors = TileFillColor.values();
        int ordinal = this.random.nextInt(colors.length);
        return colors[ordinal];
    }

    @Override
    public TileFill[] getAllTileFills() {
        return TileFillColor.values();
    }
}
