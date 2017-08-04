package com.liefery.android.stop_badge.drawing;

import android.graphics.*;

public class ArrowUpTarget extends DrawTarget {

    private Path SHAPE_ARROW_UP = new Path();

    @Override
    public void draw( Canvas canvas, Paint paint, int size ) {

        SHAPE_ARROW_UP.moveTo( .5f, 0 );
        SHAPE_ARROW_UP.lineTo( .1f, .4f );
        SHAPE_ARROW_UP.lineTo( .1f, .65f );
        SHAPE_ARROW_UP.lineTo( .4f, .35f );
        SHAPE_ARROW_UP.lineTo( .4f, 1f );
        SHAPE_ARROW_UP.lineTo( .6f, 1f );
        SHAPE_ARROW_UP.lineTo( .6f, .35f );
        SHAPE_ARROW_UP.lineTo( .9f, .65f );
        SHAPE_ARROW_UP.lineTo( .9f, .4f );
        SHAPE_ARROW_UP.lineTo( .5f, 0 );
        SHAPE_ARROW_UP.close();

        RectF bounds = new RectF();
        Matrix matrix = new Matrix();

        SHAPE_ARROW_UP.computeBounds( bounds, true );
        float scale = 90;
        matrix.reset();
        matrix.setScale( scale, scale );
        SHAPE_ARROW_UP.transform( matrix );

        SHAPE_ARROW_UP.computeBounds( bounds, true );
        float centerX = size / 2;
        float centerY = size / 2;
        matrix.reset();
        matrix.setTranslate(
            centerX - ( bounds.right + bounds.left ) / 2,
            centerY - ( bounds.bottom + bounds.top ) / 2 );
        SHAPE_ARROW_UP.transform( matrix );

        canvas.drawPath( SHAPE_ARROW_UP, paint );
    }

}
