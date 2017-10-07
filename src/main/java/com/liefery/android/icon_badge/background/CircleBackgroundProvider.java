package com.liefery.android.icon_badge.background;

import android.graphics.Path;

public class CircleBackgroundProvider extends BackgroundProvider {

    private static final Path SHAPE;

    static {
        SHAPE = new Path();
        SHAPE.addCircle( 0.5f, 0.5f, 0.5f, Path.Direction.CW );
    }

    public CircleBackgroundProvider() {
        super( SHAPE );
    }

}
