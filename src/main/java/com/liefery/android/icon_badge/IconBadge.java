package com.liefery.android.icon_badge;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.content.res.AppCompatResources;
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

    @Nullable
    private BackgroundProvider backgroundProvider;

    @Nullable
    BackgroundProvider.Result backgroundProviderDrawingResult;

    @Nullable
    private ForegroundShapeDrawer foregroundShapeDrawer;

    private final Paint backgroundShapePaint = new Paint( ANTI_ALIAS_FLAG );

    @ColorInt
    private int foregroundShapeColor;

    private int size = -1;

    private final Paint shadowPaint = new Paint( ANTI_ALIAS_FLAG );

    private Matrix matrix = new Matrix();

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
            AppCompatResources.getDrawable( context, R.drawable.ic_arrow_up ) ) );
    }

    @Override
    public void setForegroundShapeArrowDown() {
        setForegroundDrawer( new DrawableForegroundDrawer(
            AppCompatResources.getDrawable( context, R.drawable.ic_arrow_down ) ) );
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

        if ( getBackgroundProvider() != null ) {
            backgroundProviderDrawingResult = getBackgroundProvider().export(
                size,
                0 );
        } else {
            backgroundProviderDrawingResult = null;
        }

        if ( foregroundShapeDrawer != null )
            foregroundShapeDrawer.prepare(
                getForegroundShapeColor(),
                size,
                elevation );
    }

    public void draw( Canvas canvas ) {
        if ( backgroundProviderDrawingResult != null ) {
            Path adjustedPath = adjustPathCenter(
                canvas.getWidth(),
                backgroundProviderDrawingResult );
            canvas.drawPath( adjustedPath, backgroundShapePaint );
        }

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
        } else
            return renderedBitmap;
    }

    private Bitmap renderBitmap( int size ) {
        Bitmap bitmap;
        Canvas canvas;

        if ( backgroundProvider != null
            && backgroundProviderDrawingResult != null ) {
            int actualSize = (int) Math.max(
                backgroundProviderDrawingResult.width,
                backgroundProviderDrawingResult.height );

            int padding = (int) ( 3 * elevation );

            bitmap = Bitmap.createBitmap( actualSize + padding, actualSize
                + padding, Bitmap.Config.ARGB_8888 );

            canvas = new Canvas( bitmap );

            if ( elevation > 0 )
                drawShadow( canvas, backgroundProviderDrawingResult );
        } else {
            bitmap = Bitmap.createBitmap( size, size, Bitmap.Config.ARGB_8888 );
            canvas = new Canvas( bitmap );
        }

        draw( canvas );

        return bitmap;
    }

    private void drawShadow(
        Canvas canvas,
        BackgroundProvider.Result backgroundResult ) {
        shadowPaint.setShadowLayer(
            elevation * 1.5f,
            0f,
            elevation,
            Color.BLACK );
        Path adjustedPath = adjustPathCenter(
            canvas.getWidth(),
            backgroundResult );
        canvas.drawPath( adjustedPath, shadowPaint );
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

    private Path adjustPathCenter(
        int canvasWidth,
        BackgroundProvider.Result backgroundProviderDrawingResult ) {
        matrix.reset();
        matrix.setTranslate(
            ( canvasWidth - backgroundProviderDrawingResult.width ) / 2,
            elevation );
        Path adjustedPath = new Path( backgroundProviderDrawingResult.path );
        adjustedPath.transform( matrix );
        return adjustedPath;
    }
}