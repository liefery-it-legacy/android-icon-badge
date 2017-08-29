package com.liefery.android.icon_badge.drawer.background;

import android.graphics.Path;

public class PinBackgroundProvider extends BackgroundProvider {
    
    private static final Path SHAPE;
    
    static {
        SHAPE = new Path();
        SHAPE.moveTo(50.0f, 110.0f);
        SHAPE.lineTo(82.5f, 88.0f);
        SHAPE.cubicTo(93.21652f, 78.82952f, 100.0f, 65.21052f, 100.0f, 50.0f);
        SHAPE.cubicTo(100.0f, 22.385763f, 77.614235f, 0.0f, 50.0f, 0.0f);
        SHAPE.cubicTo(22.385763f, 0.0f, 0.0f, 22.385763f, 0.0f, 50.0f);
        SHAPE.cubicTo(0.0f, 65.21526f, 6.7776804f, 78.82944f, 17.5f, 88.0f);
        SHAPE.lineTo(50.0f, 110.0f);
    }
    
    public PinBackgroundProvider() {
        super(SHAPE);
    }
    
}
