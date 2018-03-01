package core.interfaces;

import core.interfaces.TileColor;

public interface TileColorGenerator {
    TileColor getRandomTileColor();
    TileColor[] getAllTileColors();
}
