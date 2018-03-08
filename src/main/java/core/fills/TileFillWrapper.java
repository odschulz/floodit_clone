package core.fills;

import core.interfaces.TileFill;

/**
 * Use this class to wrap the passed by clients tile fills. This will protect
 * by exposing only API defined functionality and guide against unwanted
 * behaviour in client defined tile fills.
 */
public class TileFillWrapper implements TileFill {
    private final TileFill tileFill;

    public TileFillWrapper(TileFill tileFill) {
        this.tileFill = tileFill;
    }

    private TileFill getTileFill() {
        return this.tileFill;
    }

    @Override
    public String getValue() {
        return this.getTileFill().getValue();
    }
}
