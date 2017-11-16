package com.liefery.android.icon_badge.foreground;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;

public class DrawableForegroundDrawer extends ForegroundShapeDrawer {
    private Drawable drawable;

    private int size;

    private int offset;

    private int elevation;

    public DrawableForegroundDrawer( @NonNull Drawable drawable ) {
        this.drawable = DrawableCompat.wrap( drawable ).mutate();
    }

    @Override
    public void prepare( int color, int size, float elevation ) {
        DrawableCompat.setTint( drawable, color );
        this.offset = (int) Math.round( size * 0.2 );
        this.size = size;
        this.elevation = (int) elevation;
    }

    @Override
    public void draw( Canvas canvas ) {
        int paddingWidth = ( canvas.getWidth() - size ) / 2;

        drawable.setBounds( offset + paddingWidth, offset + elevation, size
            - offset + paddingWidth, size - offset + elevation );
        drawable.draw( canvas );
    }
}
