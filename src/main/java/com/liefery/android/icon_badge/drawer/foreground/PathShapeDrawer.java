package com.liefery.android.icon_badge.drawer.foreground;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;

public abstract class PathShapeDrawer extends ForegroundShapeDrawer {

    private static float SCALE_FACTOR = 0.5f;

    private RectF bounds = new RectF();

    private Matrix matrix = new Matrix();

    protected void scalePath( Path path, int size ) {
        float scale = size * SCALE_FACTOR;
        matrix.reset();
        matrix.setScale( scale, scale );
        path.transform( matrix );
    }

    protected void centerPath( Path path, int size ) {
        path.computeBounds( bounds, true );
        float centerX = size / 2;
        float centerY = size / 2;
        matrix.reset();
        matrix.setTranslate(
            centerX - ( bounds.right + bounds.left ) / 2,
            centerY - ( bounds.bottom + bounds.top ) / 2 );
        path.transform( matrix );
    }
}
