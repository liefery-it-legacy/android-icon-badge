package com.liefery.android.stop_badge;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;

public class StopBadgeView extends ImageView {
    private final StopBadge stopBadge = new StopBadge();

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

        int circleColor = styles.getColor(
            R.styleable.StopBadgeView_stopBadge_circleColor,
            Integer.MIN_VALUE );
        if ( circleColor != Integer.MIN_VALUE )
            setCircleColor( circleColor );

        int shape = styles.getInt(
            R.styleable.StopBadgeView_stopBadge_shape,
            -1 );
        if ( shape == 0 )
            setShapeArrowUp();
        else if ( shape == 1 )
            setShapeArrowDown();

        int backgroundShape = styles.getInt(
            R.styleable.StopBadgeView_stopBadge_backgroundShape,
            StopBadge.BACKGROUND_ROUND );
        setBackgroundShape( backgroundShape );

        int shapeColor = styles.getColor(
            R.styleable.StopBadgeView_stopBadge_shapeColor,
            Integer.MIN_VALUE );
        if ( shapeColor != Integer.MIN_VALUE )
            setShapeColor( shapeColor );

        int shadowColor = styles.getColor(
            R.styleable.StopBadgeView_stopBadge_shadowColor,
            Integer.MIN_VALUE );
        if ( shadowColor != Integer.MIN_VALUE )
            setShadowColor( shadowColor );

        float shadowDx = styles.getDimension(
            R.styleable.StopBadgeView_stopBadge_shadowDx,
            Integer.MIN_VALUE );
        if ( shadowDx != Integer.MIN_VALUE )
            setShadowDx( shadowDx );

        float shadowDy = styles.getDimension(
            R.styleable.StopBadgeView_stopBadge_shadowDy,
            Integer.MIN_VALUE );
        if ( shadowDy != Integer.MIN_VALUE )
            setShadowDy( shadowDy );

        float shadowRadius = styles.getDimension(
            R.styleable.StopBadgeView_stopBadge_shadowRadius,
            -1 );
        if ( shadowRadius != -1 )
            setShadowRadius( shadowRadius );

        int number = styles.getInt(
            R.styleable.StopBadgeView_stopBadge_stopNumber,
            -1 );
        if ( number != -1 )
            setNumber( number );
    }

    public void setCircleColor( @ColorInt int color ) {
        stopBadge.setCircleColor( color );
        invalidateAndReset();
    }

    public void setShapeColor( @ColorInt int color ) {
        stopBadge.setShapeColor( color );
        invalidateAndReset();
    }

    public void setShapeArrowUp() {
        stopBadge.setShapeArrowUp();
        invalidateAndReset();
    }

    public void setShapeArrowDown() {
        stopBadge.setShapeArrowDown();
        invalidateAndReset();
    }

    public void setNumber( int number ) {
        stopBadge.setNumber( number );
        invalidateAndReset();
    }

    public void setShadowColor( @ColorInt int color ) {
        stopBadge.setShadowColor( color );
        invalidateAndReset();
    }

    public void setShadowDx( float dx ) {
        stopBadge.setShadowDx( dx );
        invalidateAndReset();
    }

    public void setShadowDy( float dy ) {
        stopBadge.setShadowDy( dy );
        invalidateAndReset();
    }

    public void setShadowRadius( float radius ) {
        stopBadge.setShadowRadius( radius );
        invalidateAndReset();
    }

    public void setBackgroundShape( int backgroundShape ) {
        stopBadge.setBackgroundShape( backgroundShape );
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
            cache = stopBadge.export( size );
            setImageBitmap( cache );
        }
    }

    private void invalidateAndReset() {
        if ( cache != null ) {
            cache.recycle();
            cache = null;
            setImageDrawable( null );
        } else
            invalidate();
    }
}