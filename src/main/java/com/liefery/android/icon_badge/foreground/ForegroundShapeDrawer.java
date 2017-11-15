package com.liefery.android.icon_badge.foreground;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;

public abstract class ForegroundShapeDrawer {
    public abstract void prepare( @ColorInt int color, int size, float elevation );

    public abstract void draw( Canvas canvas );
}
