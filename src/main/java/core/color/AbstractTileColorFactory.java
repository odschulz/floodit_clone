package core.color;

public abstract class AbstractTileColorFactory implements TileColorFactory {
    private static final EnumTileColorFactory DEFAULT_TILE_COLOR_FACTORY = new EnumTileColorFactory();

    /**
     * Get the system default color factory.
     *
     * @return AbstractTileColorFactory
     */
    public static AbstractTileColorFactory getFactory() {
        return DEFAULT_TILE_COLOR_FACTORY;
    }

}
