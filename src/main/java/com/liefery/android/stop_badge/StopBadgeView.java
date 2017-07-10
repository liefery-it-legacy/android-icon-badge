package com.liefery.android.stop_badge;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;

public class StopBadgeView extends ImageView {
    private StopBadgeFactory.StopBadgeCommonAttributesEditor stopBadgeBuilder;

    private Bitmap cache;

    public StopBadgeView( Context context ) {
        super( context );

        TypedArray styles = context
                        .obtainStyledAttributes( R.styleable.StopBadgeView );
        initialize( styles );
        styles.recycle();
    }

    public StopBadgeView( Context context, AttributeSet attrs ) {
        super( context, attrs );

        TypedArray styles = context.obtainStyledAttributes(
            attrs,
            R.styleable.StopBadgeView );
        initialize( styles );
        styles.recycle();
    }

    public StopBadgeView( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );

        TypedArray styles = context.obtainStyledAttributes(
            attrs,
            R.styleable.StopBadgeView,
            defStyleAttr,
            0 );
        initialize( styles );
        styles.recycle();
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    public StopBadgeView(
        Context context,
        AttributeSet attrs,
        int defStyleAttr,
        int defStyleRes ) {
        super( context, attrs, defStyleAttr, defStyleRes );

        TypedArray styles = context.obtainStyledAttributes(
            attrs,
            R.styleable.StopBadgeView,
            defStyleAttr,
            defStyleRes );
        initialize( styles );
        styles.recycle();
    }

    private void initialize( @NonNull TypedArray styles ) {
        setScaleType( ScaleType.CENTER );

        stopBadgeBuilder = StopBadgeFactory.fromNothing();

        int shape = styles.getInt(
            R.styleable.StopBadgeView_stopBadge_shape,
            -1 );
        if ( shape == 0 )
            stopBadgeBuilder = StopBadgeFactory.fromArrowUp();
        else if ( shape == 1 )
            stopBadgeBuilder = StopBadgeFactory.fromArrowDown();

        int stopNumber = styles.getInt(
            R.styleable.StopBadgeView_stopBadge_stopNumber,
            -1 );
        if ( stopNumber != -1 )
            stopBadgeBuilder = StopBadgeFactory.fromNumber( stopNumber );

        int circleColor = styles.getColor(
            R.styleable.StopBadgeView_stopBadge_circleColor,
            Integer.MIN_VALUE );
        if ( circleColor != Integer.MIN_VALUE )
            stopBadgeBuilder.setCircleColor( circleColor );

        int shapeColor = styles.getColor(
            R.styleable.StopBadgeView_stopBadge_shapeColor,
            Integer.MIN_VALUE );
        if ( shapeColor != Integer.MIN_VALUE )
            stopBadgeBuilder.setShapeColor( shapeColor );

        int shadowColor = styles.getColor(
            R.styleable.StopBadgeView_stopBadge_shadowColor,
            Integer.MIN_VALUE );
        if ( shadowColor != Integer.MIN_VALUE )
            stopBadgeBuilder.setShadowColor( shadowColor );

        float shadowDx = styles.getDimension(
            R.styleable.StopBadgeView_stopBadge_shadowDx,
            Integer.MIN_VALUE );
        if ( shadowDx != Integer.MIN_VALUE )
            stopBadgeBuilder.setShadowDx( shadowDx );

        float shadowDy = styles.getDimension(
            R.styleable.StopBadgeView_stopBadge_shadowDy,
            Integer.MIN_VALUE );
        if ( shadowDy != Integer.MIN_VALUE )
            stopBadgeBuilder.setShadowDy( shadowDy );

        float shadowRadius = styles.getDimension(
            R.styleable.StopBadgeView_stopBadge_shadowRadius,
            -1 );
        if ( shadowRadius != -1 )
            stopBadgeBuilder.setShadowRadius( shadowRadius );
    }

    public void setCircleColor( @ColorInt int color ) {
        stopBadgeBuilder.setCircleColor( color );
        invalidateAndReset();
    }

    public void setShapeColor( @ColorInt int color ) {
        stopBadgeBuilder.setShapeColor( color );
        invalidateAndReset();
    }

    public void setShape( Path path ) {
        stopBadgeBuilder = StopBadgeFactory.fromPath( path );
        invalidateAndReset();
    }

    public void setShapeArrowUp() {
        stopBadgeBuilder = StopBadgeFactory.fromArrowUp();
        invalidateAndReset();
    }

    public void setShapeArrowDown() {
        stopBadgeBuilder = StopBadgeFactory.fromArrowDown();
        invalidateAndReset();
    }

    public void setStopNumber( int stopNumber ) {
        stopBadgeBuilder = StopBadgeFactory.fromNumber( stopNumber );
        invalidateAndReset();
    }

    public void setShadowColor( @ColorInt int color ) {
        stopBadgeBuilder.setShadowColor( color );
        invalidateAndReset();
    }

    public void setShadowDx( float dx ) {
        stopBadgeBuilder.setShadowDx( dx );
        invalidateAndReset();
    }

    public void setShadowDy( float dy ) {
        stopBadgeBuilder.setShadowDy( dy );
        invalidateAndReset();
    }

    public void setShadowRadius( float radius ) {
        stopBadgeBuilder.setShadowRadius( radius );
        invalidateAndReset();
    }

    @Override
    protected void onLayout(
        boolean changed,
        int left,
        int top,
        int right,
        int bottom ) {
        super.onLayout( changed, left, top, right, bottom );

        if ( cache == null ) {
            int width = right - left;
            int height = bottom - top;
            int size = Math.min( width, height );
            cache = stopBadgeBuilder.create( size );
            setImageBitmap( cache );
        }
    }

    private void invalidateAndReset() {
        if ( cache != null ) {
            cache.recycle();
            cache = null;
        }

        invalidate();
    }
}