package core.fills;

import core.interfaces.TileFill;

import java.util.Random;

/**
 * Uses java.util.Random for random generation of fills.
 */
final class TileFillGeneratorUtilRandom extends TileFillGeneratorFactory {

    private final Random random;
    private final TileFill[] tileFills;

    TileFillGeneratorUtilRandom(TileFill[] tileFills) {
        this.random = new Random();
        this.tileFills = tileFills;
    }

    private Random getRandom() {
        return this.random;
    }

    @Override
    public TileFill[] getTileFills() {
        return tileFills;
    }

    @Override
    public TileFill getRandomTileFill() {
        int ordinal = this.random.nextInt(this.tileFills.length);
        return this.tileFills[ordinal];
    }
}
