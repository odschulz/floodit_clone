package core.color;

import core.interfaces.TileColorGenerator;

public abstract class AbstractTileColorGenerator implements TileColorGenerator {
    private static final EnumTileColorGenerator DEFAULT_TILE_COLOR_FACTORY = new EnumTileColorGenerator();

    /**
     * Get the system default color factory.
     *
     * @return AbstractTileColorGenerator
     */
    public static AbstractTileColorGenerator getFactory() {
        return DEFAULT_TILE_COLOR_FACTORY;
    }

}
