package com.liefery.android.icon_badge.drawer.foreground;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

public class DrawableForegroundDrawer extends ForegroundShapeDrawer {

    private Drawable drawable;

    public DrawableForegroundDrawer( Drawable drawable ) {
        this.drawable = drawable;
    }

    @Override
    public void draw(
        Canvas canvas,
        Paint paint,
        int size) {

        int offset = (int) Math.round( size * 0.2 );

        drawable.setBounds( offset, offset, size - offset, size - offset );
        drawable.setColorFilter( paint.getColor(), PorterDuff.Mode.MULTIPLY );
        drawable.draw( canvas );
    }

}
