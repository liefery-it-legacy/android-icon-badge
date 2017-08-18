package com.liefery.android.icon_badge.drawer;

import android.graphics.*;

public abstract class ForegroundShapeDrawer {

    public abstract void draw(
        Canvas canvas,
        Paint paint,
        int size,
        float shadowSizeX,
        float shadowSizeY );

}
