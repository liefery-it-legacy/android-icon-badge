package com.liefery.android.icon_badge.drawer.foreground;

import android.graphics.*;

public abstract class ForegroundShapeDrawer {

    public abstract void draw(
        Canvas canvas,
        Paint paint,
        int size);

}
