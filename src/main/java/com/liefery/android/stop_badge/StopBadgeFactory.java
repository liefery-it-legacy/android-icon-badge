package com.liefery.android.stop_badge;

import android.annotation.SuppressLint;
import android.graphics.*;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.util.LruCache;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class StopBadgeFactory {

    private static StopBadgeFactory instance = null;
    private BadgeCache cache = new BadgeCache();

    protected StopBadgeFactory() {
        // Exists only to defeat instantiation.
    }

    public static StopBadgeFactory worker() {
        if ( instance == null ) {
            instance = new StopBadgeFactory();
        }
        return instance;
    }

    public static StopBadgeCommonAttributesEditor fromArrowUp() {
        return new StopBadgeCommonAttributesEditor(
            StopBadgeCommonAttributesEditor.ArrowDirection.UP );
    }

    public static StopBadgeCommonAttributesEditor fromArrowDown() {
        return new StopBadgeCommonAttributesEditor(
            StopBadgeCommonAttributesEditor.ArrowDirection.DOWN );
    }

    public static StopBadgeCommonAttributesEditor fromNumber( int number ) {
        return new StopBadgeCommonAttributesEditor( number );
    }

    public static StopBadgeCommonAttributesEditor fromPath( Path path ) {
        return new StopBadgeCommonAttributesEditor( path );
    }

    public static StopBadgeCommonAttributesEditor fromNothing() {
        return new StopBadgeCommonAttributesEditor();
    }

    public static class StopBadgeCommonAttributesEditor {

        static final Path SHAPE_ARROW_UP;

        static final Path SHAPE_ARROW_DOWN;

        static {
            Path arrowUp = new Path();
            arrowUp.moveTo( .5f, 0 );
            arrowUp.lineTo( .1f, .4f );
            arrowUp.lineTo( .1f, .65f );
            arrowUp.lineTo( .4f, .35f );
            arrowUp.lineTo( .4f, 1f );
            arrowUp.lineTo( .6f, 1f );
            arrowUp.lineTo( .6f, .35f );
            arrowUp.lineTo( .9f, .65f );
            arrowUp.lineTo( .9f, .4f );
            arrowUp.lineTo( .5f, 0 );
            arrowUp.close();
            SHAPE_ARROW_UP = arrowUp;

            Path arrowDown = new Path();
            arrowDown.moveTo( .4f, 0 );
            arrowDown.lineTo( .4f, .65f );
            arrowDown.lineTo( .1f, .35f );
            arrowDown.lineTo( .1f, .6f );
            arrowDown.lineTo( .5f, 1 );
            arrowDown.lineTo( .9f, .6f );
            arrowDown.lineTo( .9f, .35f );
            arrowDown.lineTo( .6f, .65f );
            arrowDown.lineTo( .6f, 0 );
            arrowDown.lineTo( .4f, 0 );
            arrowDown.close();
            SHAPE_ARROW_DOWN = arrowDown;
        }

        private float alpha = 1;

        private final Path circlePath = new Path();

        private final Paint circlePaint = new Paint( ANTI_ALIAS_FLAG );

        private Path shapePath = new Path();

        private Paint shapePaint = new Paint( ANTI_ALIAS_FLAG );

        private final Path adjustedShape = new Path();

        private int shadowColor = Color.argb( 125, 0, 0, 0 );

        private float shadowDx = 0;

        private float shadowDy = 0;

        private float shadowRadius = 0;

        private final Paint shadowPaint = new Paint( ANTI_ALIAS_FLAG );

        private final Paint textPaint = new Paint();

        private float scale = 1;

        /**
         * Cache object to be reused
         */
        private RectF bounds = new RectF();

        /**
         * Cache object to be reused
         */
        private Matrix matrix = new Matrix();

        enum ArrowDirection {
            UP, DOWN
        }

        StopBadgeCommonAttributesEditor() {
            setShapeColor( Color.TRANSPARENT );
        }

        StopBadgeCommonAttributesEditor( ArrowDirection arrowDirection ) {
            this.scale = .55f;
            switch ( arrowDirection ) {
                case UP:
                    this.shapePath = SHAPE_ARROW_DOWN;
                case DOWN:
                    this.shapePath = SHAPE_ARROW_DOWN;
            }

            setShapeColor( Color.TRANSPARENT );
        }

        StopBadgeCommonAttributesEditor( int stopNumber ) {
            if ( stopNumber < 0 ) {
                throw new IllegalArgumentException( "stopNumber must be >= 0" );
            }

            if ( stopNumber < 10 )
                this.scale = .5f;
            else if ( stopNumber < 100 )
                this.scale = .6f;
            else
                this.scale = .7f;

            Path path = new Path();
            numberToPath( stopNumber, path );
            this.shapePath = path;

            setShapeColor( Color.TRANSPARENT );
        }

        StopBadgeCommonAttributesEditor( Path path ) {
            this.scale = .55f;
            this.shapePath = path;

            setShapeColor( Color.TRANSPARENT );
        }

        public StopBadgeCommonAttributesEditor setCircleColor(
            @ColorInt int color ) {
            circlePaint.setColor( color );
            return this;
        }

        public StopBadgeCommonAttributesEditor setShapeColor(
            @ColorInt int color ) {
            shapePaint.setColor( color );
            return this;
        }

        public StopBadgeCommonAttributesEditor setShadowColor( int color ) {
            this.shadowColor = color;
            return this;
        }

        public StopBadgeCommonAttributesEditor setShadowDx( float dx ) {
            this.shadowDx = dx;
            return this;
        }

        public StopBadgeCommonAttributesEditor setShadowDy( float dy ) {
            this.shadowDy = dy;
            return this;
        }

        public StopBadgeCommonAttributesEditor setShadowRadius( float radius ) {
            this.shadowRadius = radius;
            return this;
        }

        public StopBadgeCommonAttributesEditor setAlpha(
            @FloatRange( from = 0.0, to = 1.0 ) float alpha ) {
            float circleAlpha = circlePaint.getAlpha() / 255f;
            circlePaint.setAlpha( (int) ( 255 * alpha * circleAlpha ) );
            float shapeAlpha = shapePaint.getAlpha() / 255f;
            shapePaint.setAlpha( (int) ( 255 * alpha * shapeAlpha ) );
            return this;
        }

        public Bitmap create( int size ) {
            Bitmap bitmap = Bitmap.createBitmap( size + shadowSizeX() * 2, size
                + shadowSizeY() * 2, Bitmap.Config.ARGB_8888 );
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Canvas canvas = new Canvas( bitmap );

            circlePath.reset();
            circlePath.addCircle(
                width / 2f,
                height / 2f,
                size / 2f,
                Path.Direction.CW );

            adjustPath( shapePath, width, height, size );

            circlePath.setFillType( Path.FillType.EVEN_ODD );
            circlePath.addPath( adjustedShape );
            drawShadow( canvas, circlePath );
            canvas.drawPath( circlePath, circlePaint );

            if ( !isShapeTransparent() ) {
                canvas.drawPath( adjustedShape, shapePaint );
            }

            return bitmap;
        }

        private int shadowSizeX() {
            return (int) ( shadowRadius + Math.abs( shadowDx ) );
        }

        private int shadowSizeY() {
            return (int) ( shadowRadius + Math.abs( shadowDy ) );
        }

        private boolean isShapeTransparent() {
            return shapePaint.getAlpha() == 0;
        }

        private void drawShadow( Canvas canvas, Path shape ) {
            if ( shadowRadius > 0 ) {
                shadowPaint.setShadowLayer(
                    shadowRadius,
                    shadowDx,
                    shadowDy,
                    shadowColor );
                canvas.drawPath( shape, shadowPaint );
                circlePaint.setXfermode( new PorterDuffXfermode(
                    PorterDuff.Mode.SRC_IN ) );
            } else
                circlePaint.setXfermode( null );
        }

        private void numberToPath( int stopNumber, Path path ) {
            Paint textPaint = new Paint();
            textPaint.setTypeface( Typeface.create(
                Typeface.DEFAULT,
                Typeface.BOLD ) );

            String value = String.valueOf( stopNumber );
            textPaint.setTextSize( 10 );
            textPaint.getTextPath( value, 0, value.length(), 0, 0, path );
        }

        private void adjustPath(
            Path shape,
            float outerWidth,
            float outerHeight,
            float circleSize ) {
            adjustedShape.reset();
            adjustedShape.addPath( shape );

            adjustedShape.computeBounds( bounds, true );
            float currentSize = Math.max( bounds.width(), bounds.height() );
            float scale = circleSize / currentSize * this.scale;
            matrix.reset();
            matrix.setScale( scale, scale );
            adjustedShape.transform( matrix );

            adjustedShape.computeBounds( bounds, true );
            float centerX = outerWidth / 2;
            float centerY = outerHeight / 2;
            matrix.reset();
            matrix.setTranslate(
                centerX - ( bounds.right + bounds.left ) / 2,
                centerY - ( bounds.bottom + bounds.top ) / 2 );
            adjustedShape.transform( matrix );
        }
    }

    @SuppressLint("NewApi")
    public class BadgeCache extends LruCache<String, Bitmap> {
        BadgeCache() {
            super( 2 * 1024 * 1024 ); // 2MiB
        }
    }
}
