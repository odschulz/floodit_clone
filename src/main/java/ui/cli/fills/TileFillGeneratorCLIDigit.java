package ui.cli.fills;

import core.interfaces.TileFill;
import ui.cli.fills.AbstractTileFillGeneratorCLI;

import java.util.Random;

public class TileFillGeneratorCLIDigit extends AbstractTileFillGeneratorCLI {

    private Random random;

    TileFillGeneratorCLIDigit() {
        this.setRandom(new Random());
    }

    private Random getRandom() {
        return this.random;
    }

    private void setRandom(Random random) {
        this.random = random;
    }

    @Override
    public TileFill getRandomTileColor() {
        TileFillDigit[] colors = TileFillDigit.values();
        int ordinal = this.random.nextInt(colors.length);
        return colors[ordinal];
    }

    @Override
    public TileFill[] getAllTileColors() {
        return TileFillDigit.values();
    }
}
