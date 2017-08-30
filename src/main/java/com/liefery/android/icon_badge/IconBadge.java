package com.liefery.android.icon_badge;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.v4.content.ContextCompat;
import com.liefery.android.icon_badge.drawer.background.BackgroundProvider;
import com.liefery.android.icon_badge.drawer.background.RoundBackgroundProvider;
import com.liefery.android.icon_badge.drawer.foreground.DrawableForegroundDrawer;
import com.liefery.android.icon_badge.drawer.foreground.ForegroundShapeDrawer;
import com.liefery.android.icon_badge.drawer.foreground.NumberShapeDrawer;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class IconBadge {
    
    private float alpha = 1;
    
    private int elevation = 0;
    
    private BackgroundProvider backgroundProvider = new RoundBackgroundProvider();

    private ForegroundShapeDrawer foregroundShapeDrawer;

    private final Paint backgroundShapePaint = new Paint( ANTI_ALIAS_FLAG );

    private final Paint foregroundShapePaint = new Paint( ANTI_ALIAS_FLAG );
    
    private final Paint shadowPaint = new Paint( ANTI_ALIAS_FLAG );

    public IconBadge() {
        setForegroundShapeColor( Color.WHITE );
        setBackgroundShapeColor( Color.BLACK );
    
        shadowPaint.setAlpha(70);
    }

    public void setShapeArrowUp( Context context ) {
        foregroundShapeDrawer = new DrawableForegroundDrawer(
            ContextCompat.getDrawable( context, R.drawable.ic_arrow_up ) );
    }

    public void setShapeArrowDown( Context context ) {
        foregroundShapeDrawer = new DrawableForegroundDrawer(
            ContextCompat.getDrawable( context, R.drawable.ic_arrow_down ) );
    }

    public void setNumber( int number ) {
        foregroundShapeDrawer = new NumberShapeDrawer( number );
    }

    public void setForegroundDrawable( Drawable drawable ) {
        foregroundShapeDrawer = new DrawableForegroundDrawer( drawable );
    }

    public void setForegroundDrawer( ForegroundShapeDrawer drawer ) {
        foregroundShapeDrawer = drawer;
    }

    public int getBackgroundShapeColor() {
        return backgroundShapePaint.getColor();
    }

    public void setBackgroundShapeColor( @ColorInt int color ) {
        backgroundShapePaint.setColor( color );
    }

    public int getForgroundShapeColor() {
        return foregroundShapePaint.getColor();
    }

    public void setForegroundShapeColor( @ColorInt int color ) {
        foregroundShapePaint.setColor( color );
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha( @FloatRange( from = 0.0, to = 1.0 ) float alpha ) {
        this.alpha = alpha;
    }
    
    public int getElevation() {
        return elevation;
    }
    
    public void setElevation(int elevation) {
        this.elevation = elevation;
    }
    
    public void setBackgroundProvider(BackgroundProvider provider) {
        this.backgroundProvider = provider;
    }
    
    public BackgroundProvider getBackgroundProvider() {
        return backgroundProvider;
    }
    
    public void draw(Canvas canvas, int shapeSize, BackgroundProvider.Result backgroundResult ) {
        canvas.drawPath( backgroundResult.path, backgroundShapePaint );

        if ( foregroundShapeDrawer != null ) {
            foregroundShapeDrawer.draw(
                canvas,
                foregroundShapePaint,
                shapeSize);
        }
    }

    public Bitmap export( int size ) {
        Bitmap renderedBitmap = renderBitmap( size );

        if ( alpha < 1 ) {
            Bitmap alphaBitmap = makeBitmapTransparent( renderedBitmap, alpha );
            renderedBitmap.recycle();
            return alphaBitmap;
        }
        return renderedBitmap;
    }

    private Bitmap renderBitmap( int size ) {
        int padding = 2 * elevation;
        
        BackgroundProvider.Result result = backgroundProvider.export(size, padding);
        
        Bitmap bitmap = Bitmap.createBitmap( (int)(result.width + padding), (int)(result.height + padding), Bitmap.Config.ARGB_8888 );
        Canvas canvas = new Canvas( bitmap );
    
        drawShadow( canvas, result.path );
        draw( canvas, size, result );
        
        return bitmap;
    }
    
    private void drawShadow( Canvas canvas, Path shape ) {
        if(elevation > 0) {
            shadowPaint.setShadowLayer(
                    elevation * 1.5f,
                    0f,
                    elevation,
                    Color.BLACK );
            canvas.drawPath( shape, shadowPaint );
        }
    }
    
    private Bitmap makeBitmapTransparent( Bitmap originalBitmap, float alpha ) {
        Bitmap bitmap = Bitmap.createBitmap(
            originalBitmap.getWidth(),
            originalBitmap.getHeight(),
            Bitmap.Config.ARGB_8888 );
        Canvas canvas = new Canvas( bitmap );

        Paint alphaPaint = new Paint();
        alphaPaint.setAlpha( (int) ( alpha * 255 ) );
        canvas.drawBitmap( originalBitmap, 0, 0, alphaPaint );

        return bitmap;
    }

}
