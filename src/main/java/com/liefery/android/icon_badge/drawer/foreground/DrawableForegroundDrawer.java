package com.liefery.android.icon_badge.drawer.foreground;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;

public class DrawableForegroundDrawer extends ForegroundShapeDrawer {

    private Drawable drawable;

    public DrawableForegroundDrawer( Drawable drawable ) {
        this.drawable = drawable;
    }

    @Override
    public void draw( Canvas canvas, Paint paint, int size ) {
        int offset = (int) Math.round( size * 0.2 );

        Drawable wrapped = DrawableCompat.wrap( drawable );
        wrapped.mutate();
        DrawableCompat.setTint( drawable, paint.getColor() );
        drawable.setBounds( offset, offset, size - offset, size - offset );
        wrapped.draw( canvas );
    }

}
