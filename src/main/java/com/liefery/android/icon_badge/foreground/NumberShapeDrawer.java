package com.liefery.android.icon_badge.foreground;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class NumberShapeDrawer extends ForegroundShapeDrawer {
    private final int number;

    private final String text;

    private final Paint paint = new Paint( ANTI_ALIAS_FLAG );

    private Rect rect = new Rect();

    private float y;

    private int size;

    public NumberShapeDrawer( int number ) {
        this.number = number;
        this.text = Integer.toString( number );

        paint.setTextAlign( Paint.Align.CENTER );
        paint.setTypeface( Typeface.create( Typeface.DEFAULT, Typeface.BOLD ) );
    }

    @Override
    public void prepare( int color, int size, float elevation ) {
        paint.setColor( color );

        float scale = calculateScale( number );
        paint.setTextSize( size * 0.8f * scale );

        this.size = size;
        this.y = calculateCenterVertical( size ) + elevation;
    }

    @Override
    public void draw( Canvas canvas ) {
        int x = canvas.getWidth() / 2;
        canvas.drawText( text, x, y, paint );
    }

    private float calculateScale( int number ) {
        if ( number < 0 )
            throw new IllegalArgumentException(
                "Number too small - 0 allowed as min" );
        else if ( number <= 9 )
            return .7f;
        else if ( number <= 99 )
            return .6f;
        else if ( number <= 999 )
            return .5f;
        else if ( number <= 9999 )
            return .4f;
        else
            throw new IllegalArgumentException(
                "Number too big - 9999 allowed as max" );
    }

    private float calculateCenterVertical( int size ) {
        paint.getTextBounds( text, 0, text.length(), rect );
        return ( size + rect.height() ) / 2f;
    }
}