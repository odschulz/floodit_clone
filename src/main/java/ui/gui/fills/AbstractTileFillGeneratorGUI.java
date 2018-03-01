package ui.gui.fills;

import core.interfaces.TileFillGenerator;

public abstract class AbstractTileFillGeneratorGUI implements TileFillGenerator {
    private static final TileFillGenerator DEFAULT_TILE_COLOR_FACTORY = new TileFillGeneratorGUIColor();

    /**
     * Get the system default fills factory.
     *
     * @return AbstractTileFillGeneratorGUI
     */
    public static TileFillGenerator getFactory() {
        return DEFAULT_TILE_COLOR_FACTORY;
    }

}
