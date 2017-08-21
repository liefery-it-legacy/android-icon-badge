package com.liefery.android.icon_badge;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import com.liefery.android.icon_badge.drawer.*;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class IconBadge {

    static final int BACKGROUND_SHAPE_ROUND = 0;

    static final int BACKGROUND_SHAPE_SQUARE = 1;

    static final int BACKGROUND_SHAPE_PIN = 2;

    private float alpha = 1;

    private Path backgroundPath = new Path();

    private int backgroundShape = 0;

    private ForegroundShapeDrawer foregroundShapeDrawer;

    private final Paint backgroundShapePaint = new Paint( ANTI_ALIAS_FLAG );

    private Paint foregroundShapePaint = new Paint( ANTI_ALIAS_FLAG );

    private int shadowColor = Color.argb( 125, 0, 0, 0 );

    private float shadowDx = 0;

    private float shadowDy = 0;

    private float shadowRadius = 0;

    private final Paint shadowPaint = new Paint( ANTI_ALIAS_FLAG );

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
        if ( shape != IconBadge.BACKGROUND_SHAPE_ROUND
            && shape != IconBadge.BACKGROUND_SHAPE_SQUARE
            && shape != IconBadge.BACKGROUND_SHAPE_PIN ) {
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
        }
    }

    int getShadowSizeX() {
        return (int) ( shadowRadius + Math.abs( shadowDx ) * 1.5f );
    }

    int getShadowSizeY() {
        return (int) ( shadowRadius + Math.abs( shadowDy ) * 1.5f );
    }

    public boolean hasShadow() {
        return getShadowSizeX() > 0 || getShadowSizeY() > 0;
    }
    
    public int getBackgroundShape() {
        return this.backgroundShape;
    }

    public void draw( Canvas canvas, float drawingAreaSize, int shapeSize ) {
        backgroundPath.reset();
        switch ( backgroundShape ) {
            case BACKGROUND_SHAPE_ROUND:
                addCirclePath( drawingAreaSize, shapeSize );
            break;
            case BACKGROUND_SHAPE_SQUARE:
                addSquarePath( shapeSize );
            break;
            case BACKGROUND_SHAPE_PIN:
                addPinPath( drawingAreaSize, shapeSize );
            break;
        }

        drawShadow( canvas, backgroundPath );
        canvas.drawPath( backgroundPath, backgroundShapePaint );

        if ( foregroundShapeDrawer != null ) {
            foregroundShapeDrawer.draw(
                canvas,
                foregroundShapePaint,
                shapeSize,
                getShadowSizeX(),
                getShadowSizeY() );
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
        // The heightModifier mutates the height of the Bitmap to make space for
        // the lower part of the Pin shape. Canvas drawing size is not changed
        int heightModifier = ( backgroundShape == BACKGROUND_SHAPE_PIN ) ? Math
                        .round( size * 1.5f ) : 0;

        Bitmap bitmap = Bitmap.createBitmap( size + getShadowSizeX() * 2, size
            + getShadowSizeY() * 2 + heightModifier, Bitmap.Config.ARGB_8888 );
        int drawingAreaSize = bitmap.getWidth();
        Canvas canvas = new Canvas( bitmap );

        draw( canvas, drawingAreaSize, size );

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

    private void addCirclePath( float drawingAreaSize, float shapeSize ) {
        backgroundPath.addCircle(
            drawingAreaSize / 2f,
            drawingAreaSize / 2f,
            shapeSize / 2f,
            Path.Direction.CW );
    }

    private void addSquarePath( float shapeSize ) {
        backgroundPath.addRect( getShadowSizeX(), getShadowSizeY(), shapeSize
            + getShadowSizeX(), shapeSize + getShadowSizeY(), Path.Direction.CW );
    }

    private void addPinPath( float drawingAreaSize, float shapeSize ) {
        backgroundPath.addCircle(
            drawingAreaSize / 2f,
            drawingAreaSize / 2f,
            shapeSize / 2f,
            Path.Direction.CW );
        Path trianglePath = new Path();
        trianglePath.moveTo( shapeSize * 0.2f + getShadowSizeX(), shapeSize
            * 0.9f + getShadowSizeY() ); // Top Left
        trianglePath.lineTo( shapeSize * 0.8f + getShadowSizeX(), shapeSize
            * 0.9f + getShadowSizeY() ); // Top Right
        trianglePath.lineTo( shapeSize / 2 + getShadowSizeX(), shapeSize
            * 1.09f + getShadowSizeY() ); // Bottom Middle
        trianglePath.close();
        backgroundPath.addPath( trianglePath );
    }

}
