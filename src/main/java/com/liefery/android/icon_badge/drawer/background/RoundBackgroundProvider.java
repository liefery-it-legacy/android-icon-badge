package com.liefery.android.icon_badge.drawer.background;

import android.graphics.Path;

public class RoundBackgroundProvider extends BackgroundProvider {
    
    private static final Path SHAPE;
    
    static {
        SHAPE = new Path();
        SHAPE.addCircle(0.5f, 0.5f, 0.5f, Path.Direction.CW);
    }
    
    public RoundBackgroundProvider() {
        super(SHAPE);
    }
    
}
