package com.liefery.android.icon_badge;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;

public class IconBadgeView extends View {
    private final IconBadge iconBadge = new IconBadge();

    private int width, height, size;

    public IconBadgeView( Context context ) {
        super( context );

        TypedArray styles = context
                        .obtainStyledAttributes( R.styleable.IconBadgeView );
        initialize( styles );
        styles.recycle();
    }

    public IconBadgeView( Context context, AttributeSet attrs ) {
        super( context, attrs );

        TypedArray styles = context.obtainStyledAttributes(
            attrs,
            R.styleable.IconBadgeView );
        initialize( styles );
        styles.recycle();
    }

    public IconBadgeView( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );

        TypedArray styles = context.obtainStyledAttributes(
            attrs,
            R.styleable.IconBadgeView,
            defStyleAttr,
            0 );
        initialize( styles );
        styles.recycle();
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    public IconBadgeView(
        Context context,
        AttributeSet attrs,
        int defStyleAttr,
        int defStyleRes ) {
        super( context, attrs, defStyleAttr, defStyleRes );

        TypedArray styles = context.obtainStyledAttributes(
            attrs,
            R.styleable.IconBadgeView,
            defStyleAttr,
            defStyleRes );
        initialize( styles );
        styles.recycle();
    }

    private void initialize( @NonNull TypedArray styles ) {
        int backgroundShapeColor = styles.getColor(
            R.styleable.IconBadgeView_iconBadge_backgroundShapeColor,
            Integer.MIN_VALUE );
        if ( backgroundShapeColor != Integer.MIN_VALUE )
            setBackgroundShapeColor( backgroundShapeColor );

        int foregroundShape = styles.getInt(
            R.styleable.IconBadgeView_iconBadge_foregroundShape,
            -1 );
        if ( foregroundShape == 0 )
            setShapeArrowUp();
        else if ( foregroundShape == 1 )
            setShapeArrowDown();

        int backgroundShape = styles.getInt(
            R.styleable.IconBadgeView_iconBadge_backgroundShape,
            IconBadge.BACKGROUND_SHAPE_ROUND );
        setBackgroundShape( backgroundShape );

        int shapeColor = styles.getColor(
            R.styleable.IconBadgeView_iconBadge_foregroundShapeColor,
            Integer.MIN_VALUE );
        if ( shapeColor != Integer.MIN_VALUE )
            setForegroundShapeColor( shapeColor );

        int shadowColor = styles.getColor(
            R.styleable.IconBadgeView_iconBadge_shadowColor,
            Integer.MIN_VALUE );
        if ( shadowColor != Integer.MIN_VALUE )
            setShadowColor( shadowColor );

        float shadowDx = styles.getDimension(
            R.styleable.IconBadgeView_iconBadge_shadowDx,
            Integer.MIN_VALUE );
        if ( shadowDx != Integer.MIN_VALUE )
            setShadowDx( shadowDx );

        float shadowDy = styles.getDimension(
            R.styleable.IconBadgeView_iconBadge_shadowDy,
            Integer.MIN_VALUE );
        if ( shadowDy != Integer.MIN_VALUE )
            setShadowDy( shadowDy );

        float shadowRadius = styles.getDimension(
            R.styleable.IconBadgeView_iconBadge_shadowRadius,
            -1 );
        if ( shadowRadius != -1 )
            setShadowRadius( shadowRadius );

        int number = styles.getInt(
            R.styleable.IconBadgeView_iconBadge_stopNumber,
            -1 );
        if ( number != -1 )
            setNumber( number );

        int foregroundDrawableResId = styles.getResourceId(
            R.styleable.IconBadgeView_iconBadge_foregroundShapeDrawable,
            -1 );
        if ( foregroundDrawableResId != -1 ) {
            Drawable drawable = ContextCompat.getDrawable(
                getContext(),
                foregroundDrawableResId );
            setForegroundDrawable( drawable );
        }
    }

    public void setBackgroundShapeColor( @ColorInt int color ) {
        iconBadge.setBackgroundShapeColor( color );
        invalidate();
    }

    public void setForegroundShapeColor( @ColorInt int color ) {
        iconBadge.setForegroundShapeColor( color );
        invalidate();
    }

    public void setShapeArrowUp() {
        iconBadge.setShapeArrowUp( getContext() );
        invalidate();
    }

    public void setShapeArrowDown() {
        iconBadge.setShapeArrowDown( getContext() );
        invalidate();
    }

    public void setForegroundDrawable( Drawable drawable ) {
        iconBadge.setForegroundDrawable( drawable );
        invalidate();
    }

    public void setNumber( int number ) {
        iconBadge.setNumber( number );
        invalidate();
    }

    public void setShadowColor( @ColorInt int color ) {
        iconBadge.setShadowColor( color );
        invalidate();
    }

    public void setShadowDx( float dx ) {
        iconBadge.setShadowDx( dx );
        invalidate();
    }

    public void setShadowDy( float dy ) {
        iconBadge.setShadowDy( dy );
        invalidate();
    }

    public void setShadowRadius( float radius ) {
        iconBadge.setShadowRadius( radius );
        invalidate();
    }

    public void setBackgroundShape( int backgroundShape ) {
        iconBadge.setBackgroundShape( backgroundShape );
        invalidate();
    }

    @Override
    protected void onLayout(
        boolean changed,
        int left,
        int top,
        int right,
        int bottom ) {
        super.onLayout( changed, left, top, right, bottom );

        width = right - left;
        height = bottom - top;
        size = Math.min( width, height );
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        // Hardware rendering causes transparent paint to be completely black,
        // that is why we are exporting the badge with a shadow and draw it as a
        // Bitmap.
        // For badges without a shadow we reduce processing time by rendering
        // directly to the canvas.
        if ( iconBadge.hasShadow() && (android.os.Build.VERSION.SDK_INT <= 14) ) {
            Bitmap export = iconBadge.export( size );
            canvas.drawBitmap(
                export,
                -iconBadge.getShadowSizeX(),
                -iconBadge.getShadowSizeY(),
                null );
        } else {
            iconBadge.draw(canvas, width, size + 200);
        }
    }
    
}
