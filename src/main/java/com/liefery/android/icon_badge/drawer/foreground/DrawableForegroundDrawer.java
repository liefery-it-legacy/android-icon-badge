package com.liefery.android.icon_badge.drawer.foreground;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

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
