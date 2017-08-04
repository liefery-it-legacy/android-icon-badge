package com.liefery.android.stop_badge.drawer;

import android.graphics.*;

public abstract class ForegroundShapeDrawer {

    public abstract void draw(
        Canvas canvas,
        Paint paint,
        int size,
        float shadowSizeX,
        float shadowSizeY );

}
