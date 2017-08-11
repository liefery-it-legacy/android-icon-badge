package com.liefery.android.stop_badge.drawer;

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
        int size,
        float shadowSizeX,
        float shadowSizeY ) {

        int drawingSize = size + (int) shadowSizeX;

        int offset = (int) Math.round( size * 0.2 );

        drawable.setBounds( (int) shadowSizeX + offset, (int) shadowSizeY
            + offset, drawingSize - offset, drawingSize - offset );
        drawable.setColorFilter( paint.getColor(), PorterDuff.Mode.MULTIPLY );
        drawable.draw( canvas );
    }

}
