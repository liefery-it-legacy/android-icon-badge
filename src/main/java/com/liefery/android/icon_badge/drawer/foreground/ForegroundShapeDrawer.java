package com.liefery.android.icon_badge.drawer.foreground;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;

public abstract class ForegroundShapeDrawer {
    public abstract void prepare( @ColorInt int color, int size );

    @ColorInt
    public abstract int getColor();

    public abstract int getSize();

    public abstract void draw( Canvas canvas );
}
