package com.liefery.android.stop_badge;

import android.content.res.Resources;
import android.graphics.*;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.util.DisplayMetrics;
import com.liefery.android.stop_badge.drawer.ArrowDownShapeDrawer;
import com.liefery.android.stop_badge.drawer.ArrowUpShapeDrawer;
import com.liefery.android.stop_badge.drawer.ForegroundShapeDrawer;
import com.liefery.android.stop_badge.drawer.NumberShapeDrawer;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class StopBadge {

    static final int BACKGROUND_ROUND = 0;

    static final int BACKGROUND_SQUARE = 1;

    private float alpha = 1;

    private Path backgroundPath = new Path();

    private int backgroundShape = 0;

    private ForegroundShapeDrawer foregroundShapeDrawer;

    private final Paint backgroundPaint = new Paint( ANTI_ALIAS_FLAG );

    private Paint shapePaint = new Paint( ANTI_ALIAS_FLAG );

    private int shadowColor = Color.argb( 125, 0, 0, 0 );

    private float shadowDx = 0;

    private float shadowDy = 0;

    private float shadowRadius = 0;

    private final Paint shadowPaint = new Paint( ANTI_ALIAS_FLAG );

    public StopBadge() {
        setShapeColor( Color.WHITE );
    }

    public void setShapeArrowUp() {
        foregroundShapeDrawer = new ArrowUpShapeDrawer();
    }

    public void setShapeArrowDown() {
        foregroundShapeDrawer = new ArrowDownShapeDrawer();
    }

    public void setNumber( int number ) {
        foregroundShapeDrawer = new NumberShapeDrawer( number );
    }

    public void setForegroundDrawer( ForegroundShapeDrawer drawer ) {
        foregroundShapeDrawer = drawer;
    }

    public int getCircleColor() {
        return backgroundPaint.getColor();
    }

    public void setCircleColor( @ColorInt int color ) {
        backgroundPaint.setColor( color );
    }

    public int getShapeColor() {
        return shapePaint.getColor();
    }

    public void setShapeColor( @ColorInt int color ) {
        shapePaint.setColor( color );
    }

    public int getShadowColor() {
        return shadowColor;
    }

    public void setShadowColor( int color ) {
        this.shadowColor = color;
    }

    public float getShadowDx() {
        return shadowDx;
    }

    public void setShadowDx( float dx ) {
        this.shadowDx = dx;
    }

    public float getShadowDy() {
        return shadowDy;
    }

    public void setShadowDy( float dy ) {
        this.shadowDy = dy;
    }

    public float getShadowRadius() {
        return shadowRadius;
    }

    public void setShadowRadius( float radius ) {
        this.shadowRadius = radius;
    }
  
    public float getAlpha() {
        return alpha;
    }

    public void setAlpha( @FloatRange( from = 0.0, to = 1.0 ) float alpha ) {
        this.alpha = alpha;
    }

    public void setBackgroundShape( int shape ) {
        if ( shape != StopBadge.BACKGROUND_ROUND
            && shape != StopBadge.BACKGROUND_SQUARE ) {
            throw new IllegalArgumentException( "Shape "
                + shape + " is invalid!" );
        }

        this.backgroundShape = shape;
    }

    public void setBackgroundShape( int shape ) {
        if ( shape != StopBadge.BACKGROUND_ROUND
            && shape != StopBadge.BACKGROUND_SQUARE ) {
            throw new IllegalArgumentException( "Shape "
                + shape + " is invalid!" );
        }

        this.backgroundShape = shape;
    }

    private void drawShadow( Canvas canvas, Path shape ) {
        if ( shadowRadius > 0 ) {
            shadowPaint.setShadowLayer(
                shadowRadius,
                shadowDx,
                shadowDy,
                shadowColor );
            canvas.drawPath( shape, shadowPaint );
            backgroundPaint.setXfermode( new PorterDuffXfermode(
                PorterDuff.Mode.SRC_IN ) );
        } else
            backgroundPaint.setXfermode( null );
    }

    int shadowSizeX() {
        return (int) ( shadowRadius + Math.abs( shadowDx ) * 1.5f );
    }

    int shadowSizeY() {
        return (int) ( shadowRadius + Math.abs( shadowDy ) * 1.5f );
    }

    public boolean hasShadow() {
        return shadowSizeX() > 0 || shadowSizeY() > 0;
    }

    public void draw( Canvas canvas, float width, float height, int size ) {
        backgroundPath.reset();
        switch ( backgroundShape ) {
            case BACKGROUND_ROUND:
                backgroundPath.addCircle(
                    width / 2f,
                    height / 2f,
                    size / 2f,
                    Path.Direction.CW );
            break;
            case BACKGROUND_SQUARE:
                backgroundPath.addRect( shadowSizeX(), shadowSizeY(), size
                    + shadowSizeX(), size + shadowSizeY(), Path.Direction.CW );
            break;
        }

        drawShadow( canvas, backgroundPath );
        canvas.drawPath( backgroundPath, backgroundPaint );

        if ( foregroundShapeDrawer != null ) {
            foregroundShapeDrawer.draw(
                canvas,
                shapePaint,
                size,
                shadowSizeX(),
                shadowSizeY() );
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
        Bitmap bitmap = Bitmap.createBitmap( size + shadowSizeX() * 2, size
            + shadowSizeY() * 2, Bitmap.Config.ARGB_8888 );
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Canvas canvas = new Canvas( bitmap );
      
        draw( canvas, width, height, size );

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

    public static float dpToPx( float dp ) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * ( metrics.densityDpi / 160f );
        return Math.round( px );
    }

}