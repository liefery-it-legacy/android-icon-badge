package com.liefery.android.stop_badge;

import android.graphics.*;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import com.liefery.android.stop_badge.drawing.ArrowDownTarget;
import com.liefery.android.stop_badge.drawing.ArrowUpTarget;
import com.liefery.android.stop_badge.drawing.DrawTarget;
import com.liefery.android.stop_badge.drawing.NumberTarget;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class StopBadge {

    static final int BACKGROUND_ROUND = 0;

    static final int BACKGROUND_SQUARE = 1;

    private float alpha = 1;

    private Path backgroundPath = new Path();

    private int backgroundShape = 0;

    private DrawTarget foreground = new NumberTarget( 15 );

    private final Paint backgroundPaint = new Paint( ANTI_ALIAS_FLAG );

    private Paint shapePaint = new Paint( ANTI_ALIAS_FLAG );

    private int shadowColor = Color.argb( 125, 0, 0, 0 );

    private float shadowDx = 0;

    private float shadowDy = 0;

    private float shadowRadius = 0;

    private final Paint shadowPaint = new Paint( ANTI_ALIAS_FLAG );

    private final Paint textPaint = new Paint( ANTI_ALIAS_FLAG );

    public StopBadge() {
        textPaint.setTypeface( Typeface
                        .create( Typeface.DEFAULT, Typeface.BOLD ) );
        setShapeColor( Color.WHITE );
    }

    public void setShapeArrowUp() {
        foreground = new ArrowUpTarget();
    }

    public void setShapeArrowDown() {
        foreground = new ArrowDownTarget();
    }

    public void setNumber( int number ) {
        foreground = new NumberTarget( number );
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

    public int getAlpha() {
        return backgroundPaint.getAlpha();
    }

    public void setAlpha( @FloatRange( from = 0.0, to = 1.0 ) float alpha ) {
        float circleAlpha = backgroundPaint.getAlpha() / 255f;
        backgroundPaint.setAlpha( (int) ( 255 * alpha * circleAlpha ) );
        float shapeAlpha = shapePaint.getAlpha() / 255f;
        shapePaint.setAlpha( (int) ( 255 * alpha * shapeAlpha ) );
    }

    boolean isShapeTransparent() {
        return shapePaint.getAlpha() == 0;
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
        return (int) ( shadowRadius + Math.abs( shadowDx ) );
    }

    int shadowSizeY() {
        return (int) ( shadowRadius + Math.abs( shadowDy ) );
    }
    
    public void drawOnCanvas(Canvas canvas, float width, float height, int size) {
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
    
        foreground.draw( canvas, shapePaint, size );
    }

    public Bitmap export( int size ) {
        Bitmap bitmap = Bitmap.createBitmap( size + shadowSizeX() * 2, size
            + shadowSizeY() * 2, Bitmap.Config.ARGB_8888 );
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Canvas canvas = new Canvas( bitmap );

        drawOnCanvas(canvas, width, height, size);
        
        return bitmap;
    }

}