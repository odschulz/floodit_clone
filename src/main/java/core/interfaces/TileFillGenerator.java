package core.interfaces;

/**
 * Generate visual representations for tiles as fills.
 */
public interface TileFillGenerator {

    /**
     * Get all possible tile fills for this generator.
     *
     * @return all available tile fills
     */
    TileFill[] getTileFills();

    /**
     * Generate a random tile fill from the available collection.
     *
     * @return a random tile fill
     */
    TileFill getRandomTileFill();
}
