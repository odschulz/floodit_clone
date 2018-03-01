package core.color;

import core.interfaces.TileColor;

import java.util.Random;

public class EnumTileColorGenerator extends AbstractTileColorGenerator {

    private Random random;

    EnumTileColorGenerator() {
        this.setRandom(new Random());
    }

    private Random getRandom() {
        return this.random;
    }

    private void setRandom(Random random) {
        this.random = random;
    }

    @Override
    public TileColor getRandomTileColor() {
        TileColorEnum[] colors = TileColorEnum.values();
        int ordinal = this.random.nextInt(colors.length);
        return colors[ordinal];
    }

    @Override
    public TileColor[] getAllTileColors() {
        return TileColorEnum.values();
    }
}
