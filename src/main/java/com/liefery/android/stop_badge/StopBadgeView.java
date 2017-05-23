package com.liefery.android.stop_badge;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import com.liefery.android.stop.badge.R;

import static com.liefery.android.stop_badge.StopBadge.SHAPE_ARROW_DOWN;
import static com.liefery.android.stop_badge.StopBadge.SHAPE_ARROW_UP;

public class StopBadgeView extends View {
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

        int stopNumber = styles.getInt(
            R.styleable.StopBadgeView_stopBadge_stopNumber,
            -1 );
        if ( stopNumber != -1 )
            setStopNumber( stopNumber );

        setAlpha( getAlpha() );
    }

    @Override
    public void setAlpha( float alpha ) {
        super.setAlpha( alpha );

        if ( stopBadge != null ) {
            stopBadge.setAlpha( alpha );
        }
    }

    public void setCircleColor( @ColorInt int color ) {
        stopBadge.setCircleColor( color );
        invalidateAndResetCache();
    }

    public void setShapeColor( @ColorInt int color ) {
        stopBadge.setShapeColor( color );
        invalidateAndResetCache();
    }

    public void setShape( Path path ) {
        stopBadge.setShape( path );
        invalidateAndResetCache();
    }

    public void setShapeArrowUp() {
        stopBadge.setShapeArrowUp();
        invalidateAndResetCache();
    }

    public void setShapeArrowDown() {
        stopBadge.setShapeArrowDown();
        invalidateAndResetCache();
    }

    public void setStopNumber( int stopNumber ) {
        stopBadge.setStopNumber( stopNumber );
        invalidateAndResetCache();
    }

    public void setShadowColor( @ColorInt int color ) {
        stopBadge.setShadowColor( color );
        invalidateAndResetCache();
    }

    public void setShadowDx( float dx ) {
        stopBadge.setShadowDx( dx );
        invalidateAndResetCache();
    }

    public void setShadowDy( float dy ) {
        stopBadge.setShadowDy( dy );
        invalidateAndResetCache();
    }

    public void setShadowRadius( float radius ) {
        stopBadge.setShadowRadius( radius );
        invalidateAndResetCache();
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        if ( cache == null ) {
            int size = Math.min( canvas.getWidth(), canvas.getHeight() );
            cache = stopBadge.export( size );
        }

        canvas.drawBitmap(
            cache,
            -stopBadge.shadowSizeX(),
            -stopBadge.shadowSizeY(),
            null );
    }

    private void invalidateAndResetCache() {
        this.cache = null;
        invalidate();
    }
}