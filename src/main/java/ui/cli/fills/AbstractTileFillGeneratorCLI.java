package ui.cli.fills;

import core.interfaces.TileFillGenerator;

public abstract class AbstractTileFillGeneratorCLI implements TileFillGenerator {
    private static final TileFillGenerator DEFAULT_TILE_COLOR_FACTORY = new TileFillGeneratorCLIDigit();

    /**
     * Get the system default fills factory.
     *
     * @return AbstractTileFillGenerator
     */
    public static TileFillGenerator getFactory() {
        return DEFAULT_TILE_COLOR_FACTORY;
    }

}

