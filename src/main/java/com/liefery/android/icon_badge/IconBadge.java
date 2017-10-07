package com.liefery.android.icon_badge;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import com.liefery.android.icon_badge.background.BackgroundProvider;
import com.liefery.android.icon_badge.background.CircleBackgroundProvider;
import com.liefery.android.icon_badge.background.PinBackgroundProvider;
import com.liefery.android.icon_badge.background.SquareBackgroundProvider;
import com.liefery.android.icon_badge.foreground.DrawableForegroundDrawer;
import com.liefery.android.icon_badge.foreground.ForegroundShapeDrawer;
import com.liefery.android.icon_badge.foreground.NumberShapeDrawer;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class IconBadge implements IconBadgeable {
    private final Context context;

    private float alpha = 1;

    private float elevation = 0;

    private BackgroundProvider backgroundProvider;

    private BackgroundProvider.Result backgroundProviderResult;

    private ForegroundShapeDrawer foregroundShapeDrawer;

    private final Paint backgroundShapePaint = new Paint( ANTI_ALIAS_FLAG );

    @ColorInt
    private int foregroundShapeColor;

    private int size = -1;

    private final Paint shadowPaint = new Paint( ANTI_ALIAS_FLAG );

    public IconBadge( Context context ) {
        this.context = context;
        shadowPaint.setAlpha( 70 );
    }

    @Override
    public void setBackgroundShapeCircle() {
        backgroundProvider = new CircleBackgroundProvider();
    }

    @Override
    public void setBackgroundShapeSquare() {
        backgroundProvider = new SquareBackgroundProvider();
    }

    @Override
    public void setBackgroundShapePin() {
        backgroundProvider = new PinBackgroundProvider();
    }

    @Nullable
    @Override
    public BackgroundProvider getBackgroundProvider() {
        return backgroundProvider;
    }

    @Nullable
    BackgroundProvider.Result getBackgroundProviderResult() {
        return backgroundProviderResult;
    }

    @Override
    public void setBackgroundProvider( @Nullable BackgroundProvider provider ) {
        if ( provider == null ) {
            backgroundProvider = null;
            return;
        }

        backgroundProvider = provider;

        if ( size > 0 )
            prepare( size );
    }

    @Override
    public void setForegroundShapeArrowUp() {
        setForegroundDrawer( new DrawableForegroundDrawer(
            ContextCompat.getDrawable( context, R.drawable.ic_arrow_up ) ) );
    }

    @Override
    public void setForegroundShapeArrowDown() {
        setForegroundDrawer( new DrawableForegroundDrawer(
            ContextCompat.getDrawable( context, R.drawable.ic_arrow_down ) ) );
    }

    @Override
    public void setNumber( int number ) {
        setForegroundDrawer( new NumberShapeDrawer( number ) );
    }

    @Override
    public void setForegroundDrawable( @NonNull Drawable drawable ) {
        setForegroundDrawer( new DrawableForegroundDrawer( drawable ) );
    }

    @Override
    public void setForegroundDrawer( @Nullable ForegroundShapeDrawer drawer ) {
        if ( drawer == null ) {
            foregroundShapeDrawer = null;
            return;
        }

        foregroundShapeDrawer = drawer;

        if ( size > 0 )
            prepare( size );
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
        return foregroundShapeColor;
    }

    @Override
    public void setForegroundShapeColor( @ColorInt int color ) {
        foregroundShapeColor = color;
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

    void prepare( int size ) {
        this.size = size;

        if ( getBackgroundProvider() != null )
            backgroundProviderResult = getBackgroundProvider().export( size, 0 );
        else
            backgroundProviderResult = null;

        if ( foregroundShapeDrawer != null )
            foregroundShapeDrawer.prepare( getForegroundShapeColor(), size );
    }

    public void draw( Canvas canvas ) {
        if ( backgroundProviderResult != null )
            canvas.drawPath(
                backgroundProviderResult.path,
                backgroundShapePaint );

        if ( foregroundShapeDrawer != null )
            foregroundShapeDrawer.draw( canvas );
    }

    public Bitmap export( int size ) {
        prepare( size );
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
        draw( canvas );

        return bitmap;
    }

    private void drawShadow( Canvas canvas, Path shape ) {
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