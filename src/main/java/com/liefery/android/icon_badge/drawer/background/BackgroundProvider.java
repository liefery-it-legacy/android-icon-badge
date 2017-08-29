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
    
    public BackgroundProvider(Path path) {
        this.path = path;
    }
    
    public BackgroundProvider.Result export(int size) {
        Path adjustedPath = adjustPath(size);
        RectF bounds = new RectF();
        path.computeBounds(bounds, true);
        ViewOutlineProvider outline = new PathOutlineProvider(adjustedPath);
        return new BackgroundProvider.Result(adjustedPath, outline, bounds.width(), bounds.height());
    }
    
    protected Path adjustPath(int size) {
        Path copy = new Path(path);
        matrix.reset();
        
        copy.computeBounds(bounds, true);
    
        float scalar = Math.min(bounds.width(), bounds.height());
        matrix.setScale(size / scalar, size / scalar);
        copy.transform(matrix);
        
        return copy;
    }
    
    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    private static class PathOutlineProvider extends ViewOutlineProvider {
        
        Path path;
    
        PathOutlineProvider(Path path) {
            this.path = path;
        }
        
        @Override
        public void getOutline(View view, Outline outline) {
            outline.setConvexPath(path);
        }
        
    }
    
    public static class Result {
        
        public final Path path;
        public final ViewOutlineProvider outline;
        
        public final float width;
        public final float height;
        
        public Result(Path path, ViewOutlineProvider outline, float width, float height) {
            this.path = path;
            this.outline = outline;
            
            this.width = width;
            this.height = height;
        }
        
    }
    
}
