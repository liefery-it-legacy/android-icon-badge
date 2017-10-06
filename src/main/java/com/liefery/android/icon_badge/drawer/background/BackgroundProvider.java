package com.liefery.android.icon_badge.drawer.background;

import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;

public abstract class BackgroundProvider {

    private Path path;

    private RectF bounds = new RectF();

    private Matrix matrix = new Matrix();

    public BackgroundProvider( Path path ) {
        this.path = path;
    }

    public BackgroundProvider.Result export( int size, int padding ) {
        Path adjustedPath = adjustPath( size, padding );
        adjustedPath.computeBounds( bounds, true );

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            return exportLollipop( adjustedPath );
        } else {
            return new BackgroundProvider.Result(
                adjustedPath,
                bounds.width(),
                bounds.height() );
        }
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    private BackgroundProvider.Result exportLollipop( Path adjustedPath ) {
        ViewOutlineProvider outline = new PathOutlineProvider( adjustedPath );
        return new BackgroundProvider.Result(
            adjustedPath,
            outline,
            bounds.width(),
            bounds.height() );
    }

    protected Path adjustPath( int size, int padding ) {
        Path copy = new Path( path );
        matrix.reset();

        copy.computeBounds( bounds, true );

        float scalar = Math.min( bounds.width() + padding, bounds.height()
            + padding );
        matrix.setScale( size / scalar, size / scalar );
        matrix.postTranslate( padding, padding );
        copy.transform( matrix );

        return copy;
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    private static class PathOutlineProvider extends ViewOutlineProvider {

        private Path path;

        PathOutlineProvider( Path path ) {
            this.path = path;
        }

        @Override
        public void getOutline( View view, Outline outline ) {
            outline.setConvexPath( path );
        }

    }

    public static class Result {

        public final Path path;

        public ViewOutlineProvider outline = null;

        public final float width;

        public final float height;

        @TargetApi( Build.VERSION_CODES.LOLLIPOP )
        public Result(
            Path path,
            ViewOutlineProvider outline,
            float width,
            float height ) {
            this.path = path;
            this.outline = outline;

            this.width = width;
            this.height = height;
        }

        public Result( Path path, float width, float height ) {
            this.path = path;

            this.width = width;
            this.height = height;
        }

    }

}
