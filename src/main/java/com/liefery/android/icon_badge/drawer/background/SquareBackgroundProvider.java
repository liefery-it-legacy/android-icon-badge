package com.liefery.android.icon_badge.drawer.background;

import android.graphics.Path;

public class SquareBackgroundProvider extends BackgroundProvider {
    
    private static final Path SHAPE;
    
    static {
        SHAPE = new Path();
        SHAPE.addRect(0f, 0f, 1f, 1f, Path.Direction.CW);
    }
    
    public SquareBackgroundProvider() {
        super(SHAPE);
    }
    
}
