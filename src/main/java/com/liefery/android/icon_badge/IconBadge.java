package com.liefery.android.icon_badge;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.liefery.android.icon_badge.drawer.background.BackgroundProvider;
import com.liefery.android.icon_badge.drawer.background.CircleBackgroundProvider;
import com.liefery.android.icon_badge.drawer.foreground.DrawableForegroundDrawer;
import com.liefery.android.icon_badge.drawer.foreground.ForegroundShapeDrawer;
import com.liefery.android.icon_badge.drawer.foreground.NumberShapeDrawer;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class IconBadge implements IconBadgeable {
    private final Context context;

    private float alpha = 1;

    private float elevation = 0;

    private BackgroundProvider backgroundProvider;

    private ForegroundShapeDrawer foregroundShapeDrawer;

    private final Paint backgroundShapePaint = new Paint( ANTI_ALIAS_FLAG );

    private final Paint foregroundShapePaint = new Paint( ANTI_ALIAS_FLAG );

    private final Paint shadowPaint = new Paint( ANTI_ALIAS_FLAG );

    public IconBadge( Context context ) {
        this.context = context;
        shadowPaint.setAlpha( 70 );
    }

    @Override
    public void setShapeArrowUp() {
        foregroundShapeDrawer = new DrawableForegroundDrawer(
            ContextCompat.getDrawable( context, R.drawable.ic_arrow_up ) );
    }

    @Override
    public void setShapeArrowDown() {
        foregroundShapeDrawer = new DrawableForegroundDrawer(
            ContextCompat.getDrawable( context, R.drawable.ic_arrow_down ) );
    }

    @Override
    public void setNumber( int number ) {
        foregroundShapeDrawer = new NumberShapeDrawer( number );
    }

    @Override
    public void setForegroundDrawable( @NonNull Drawable drawable ) {
        foregroundShapeDrawer = new DrawableForegroundDrawer( drawable );
    }

    @Override
    public void setForegroundDrawer( @Nullable ForegroundShapeDrawer drawer ) {
        foregroundShapeDrawer = drawer;
    }

    @Override
    public int getBackgroundShapeColor() {
        return backgroundShapePaint.getColor();
    }

    @Override
    public void setBackgroundShapeColor( @ColorInt int color ) {
        backgroundShapePaint.setColor( color );
    }

    @Override
    public int getForegroundShapeColor() {
        return foregroundShapePaint.getColor();
    }

    @Override
    public void setForegroundShapeColor( @ColorInt int color ) {
        foregroundShapePaint.setColor( color );
    }

    @Override
    public float getAlpha() {
        return alpha;
    }

    @Override
    public void setAlpha( @FloatRange( from = 0.0, to = 1.0 ) float alpha ) {
        this.alpha = alpha;
    }

    @Override
    public float getElevation() {
        return elevation;
    }

    @Override
    public void setElevation( float elevation ) {
        this.elevation = elevation;
    }

    @Nullable
    @Override
    public BackgroundProvider getBackgroundProvider() {
        return backgroundProvider;
    }

    @Override
    public void setBackgroundProvider( @Nullable BackgroundProvider provider ) {
        this.backgroundProvider = provider;
    }

    public void draw(
        Canvas canvas,
        int shapeSize,
        BackgroundProvider.Result backgroundResult ) {
        canvas.drawPath( backgroundResult.path, backgroundShapePaint );

        Log.wtf( "WTF", "Drawing foreground: " + foregroundShapeDrawer );
        if ( foregroundShapeDrawer != null ) {
            foregroundShapeDrawer
                            .draw( canvas, foregroundShapePaint, shapeSize );
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
        int padding = (int) ( 2 * elevation );

        BackgroundProvider.Result result = backgroundProvider.export(
            size,
            padding );

        Bitmap bitmap = Bitmap.createBitmap(
            (int) ( result.width + padding ),
            (int) ( result.height + padding ),
            Bitmap.Config.ARGB_8888 );
        Canvas canvas = new Canvas( bitmap );

        drawShadow( canvas, result.path );
        draw( canvas, size, result );

        return bitmap;
    }

    private void drawShadow( Canvas canvas, Path shape ) {
        // TODO draw actual elevation when possible?
        if ( elevation > 0 ) {
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
