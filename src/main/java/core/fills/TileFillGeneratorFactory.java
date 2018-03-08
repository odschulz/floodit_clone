package core.fills;

import core.interfaces.TileFill;
import core.interfaces.TileFillGenerator;

public abstract class TileFillGeneratorFactory implements TileFillGenerator {

    /**
     * Get the system default fills factory.
     *
     * @return TileFillGeneratorUtilRandom
     */
    public static TileFillGenerator getFillGenerator(TileFill[] tileFills) {
        // Wrap all tile fill objects in the API tile fill wrapper.
        TileFill[] wrappedTileFills = new TileFill[tileFills.length];
        for (int i = 0; i < tileFills.length; i++) {
            if (tileFills[i] == null) {
                throw new IllegalArgumentException("TileFills array cannot contain null values.");
            }

            wrappedTileFills[i] = new TileFillWrapper(tileFills[i]);
        }
        return new TileFillGeneratorUtilRandom(wrappedTileFills);
    }

}

