package com.liefery.android.stop_badge.drawing;

import android.graphics.*;

public class ArrowDownTarget extends DrawTarget {

    private Path SHAPE_ARROW_DOWN = new Path();

    @Override
    public void draw( Canvas canvas, Paint paint, int size ) {

        SHAPE_ARROW_DOWN.moveTo( .4f, 0 );
        SHAPE_ARROW_DOWN.lineTo( .4f, .65f );
        SHAPE_ARROW_DOWN.lineTo( .1f, .35f );
        SHAPE_ARROW_DOWN.lineTo( .1f, .6f );
        SHAPE_ARROW_DOWN.lineTo( .5f, 1 );
        SHAPE_ARROW_DOWN.lineTo( .9f, .6f );
        SHAPE_ARROW_DOWN.lineTo( .9f, .35f );
        SHAPE_ARROW_DOWN.lineTo( .6f, .65f );
        SHAPE_ARROW_DOWN.lineTo( .6f, 0 );
        SHAPE_ARROW_DOWN.lineTo( .4f, 0 );
        SHAPE_ARROW_DOWN.close();

        RectF bounds = new RectF();
        Matrix matrix = new Matrix();

        SHAPE_ARROW_DOWN.computeBounds( bounds, true );
        float scale = 90;
        matrix.reset();
        matrix.setScale( scale, scale );
        SHAPE_ARROW_DOWN.transform( matrix );

        SHAPE_ARROW_DOWN.computeBounds( bounds, true );
        float centerX = size / 2;
        float centerY = size / 2;
        matrix.reset();
        matrix.setTranslate(
            centerX - ( bounds.right + bounds.left ) / 2,
            centerY - ( bounds.bottom + bounds.top ) / 2 );
        SHAPE_ARROW_DOWN.transform( matrix );

        canvas.drawPath( SHAPE_ARROW_DOWN, paint );
    }

}
