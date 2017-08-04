package com.liefery.android.stop_badge.drawer;

import android.graphics.*;

public class ArrowUpShapeDrawer extends PathShapeDrawer {

    private static final Path SHAPE_ARROW_UP;

    static {
        Path path = new Path();
        path.moveTo( .5f, 0 );
        path.lineTo( .1f, .4f );
        path.lineTo( .1f, .65f );
        path.lineTo( .4f, .35f );
        path.lineTo( .4f, 1f );
        path.lineTo( .6f, 1f );
        path.lineTo( .6f, .35f );
        path.lineTo( .9f, .65f );
        path.lineTo( .9f, .4f );
        path.lineTo( .5f, 0 );
        path.close();
        SHAPE_ARROW_UP = path;
    }

    @Override
    public void draw(
        Canvas canvas,
        Paint paint,
        int size,
        float shadowSizeX,
        float shadowSizeY ) {

        Path copy = new Path( SHAPE_ARROW_UP );

        scalePath( copy, size );
        centerPath( copy, size, shadowSizeX, shadowSizeY );

        canvas.drawPath( copy, paint );
    }

}