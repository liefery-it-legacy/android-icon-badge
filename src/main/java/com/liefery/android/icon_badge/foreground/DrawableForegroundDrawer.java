package com.liefery.android.icon_badge.foreground;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;

public class DrawableForegroundDrawer extends ForegroundShapeDrawer {
    private Drawable drawable;

    public DrawableForegroundDrawer( @NonNull Drawable drawable ) {
        this.drawable = DrawableCompat.wrap( drawable ).mutate();
    }

    @Override
    public void prepare( int color, int size ) {
        DrawableCompat.setTint( drawable, color );
        int offset = (int) Math.round( size * 0.2 );
        drawable.setBounds( offset, offset, size - offset, size - offset );
    }

    @Override
    public void draw( Canvas canvas ) {
        drawable.draw( canvas );
    }
}
