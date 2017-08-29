package com.liefery.android.icon_badge;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.v4.content.ContextCompat;
import com.liefery.android.icon_badge.drawer.background.BackgroundProvider;
import com.liefery.android.icon_badge.drawer.background.PinBackgroundProvider;
import com.liefery.android.icon_badge.drawer.foreground.DrawableForegroundDrawer;
import com.liefery.android.icon_badge.drawer.foreground.ForegroundShapeDrawer;
import com.liefery.android.icon_badge.drawer.foreground.NumberShapeDrawer;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class IconBadge {
    
    private float alpha = 1;
    
    private BackgroundProvider backgroundProvider;

    private ForegroundShapeDrawer foregroundShapeDrawer;

    private final Paint backgroundShapePaint = new Paint( ANTI_ALIAS_FLAG );

    private final Paint foregroundShapePaint = new Paint( ANTI_ALIAS_FLAG );

    public IconBadge() {
        setForegroundShapeColor( Color.WHITE );
        setBackgroundShapeColor( Color.BLACK );
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
    
    public void setBackgroundDrawer(BackgroundProvider drawer) {
        this.backgroundProvider = drawer;
    }
    
    public BackgroundProvider getBackgroundDrawer() {
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
        BackgroundProvider.Result result = backgroundProvider.export(size);
        int heightModifier = (int) Math.ceil(size / Math.max(result.width, result.height));
        
        Bitmap bitmap = Bitmap.createBitmap( size, size + heightModifier, Bitmap.Config.ARGB_8888 );
        Canvas canvas = new Canvas( bitmap );

        draw( canvas, size, result );

        return bitmap;
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
