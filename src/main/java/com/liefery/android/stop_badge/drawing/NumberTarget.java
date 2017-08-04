package com.liefery.android.stop_badge.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class NumberTarget extends DrawTarget {

    private int number;

    private float scale;

    private Rect r = new Rect();

    public NumberTarget( int number ) {
        this.number = number;

        if ( number < 0 ) {
            throw new IllegalArgumentException( "stopNumber must be >= 0" );
        }

        if ( number < 10 )
            this.scale = .7f;
        else if ( number < 100 )
            this.scale = .6f;
        else
            this.scale = .5f;
    }

    @Override
    public void draw( Canvas canvas, Paint paint, int size ) {
        paint.setTextSize( 90 * scale );
        drawCenter( canvas, paint, Integer.toString( number ) );
    }

    private void drawCenter( Canvas canvas, Paint paint, String text ) {
        canvas.getClipBounds( r );
        int cHeight = r.height();
        int cWidth = r.width();
        paint.setTextAlign( Paint.Align.CENTER );
        paint.getTextBounds( text, 0, text.length(), r );
        float x = cWidth / 2f;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText( text, x, y, paint );
    }

}
