package com.liefery.android.stop_badge.drawer;

import android.graphics.*;

public class ArrowDownShapeDrawer extends PathShapeDrawer {

    private static final Path SHAPE_ARROW_DOWN;

    static {
        Path arrowDown = new Path();
        arrowDown.moveTo( .4f, 0 );
        arrowDown.lineTo( .4f, .65f );
        arrowDown.lineTo( .1f, .35f );
        arrowDown.lineTo( .1f, .6f );
        arrowDown.lineTo( .5f, 1 );
        arrowDown.lineTo( .9f, .6f );
        arrowDown.lineTo( .9f, .35f );
        arrowDown.lineTo( .6f, .65f );
        arrowDown.lineTo( .6f, 0 );
        arrowDown.lineTo( .4f, 0 );
        arrowDown.close();
        SHAPE_ARROW_DOWN = arrowDown;
    }

    @Override
    public void draw(
        Canvas canvas,
        Paint paint,
        int size,
        float shadowSizeX,
        float shadowSizeY ) {

        Path copy = new Path( SHAPE_ARROW_DOWN );

        scalePath( copy, size );
        centerPath( copy, size, shadowSizeX, shadowSizeY );

        canvas.drawPath( copy, paint );
    }

}
