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
import android.view.View;
import android.view.ViewOutlineProvider;
import com.liefery.android.icon_badge.drawer.background.BackgroundProvider;
import com.liefery.android.icon_badge.drawer.background.PinBackgroundProvider;
import com.liefery.android.icon_badge.drawer.background.RoundBackgroundProvider;
import com.liefery.android.icon_badge.drawer.background.SquareBackgroundProvider;

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
            0 );
        switch(backgroundShape) {
            case 0:
                setBackgroundShape(new RoundBackgroundProvider());
                break;
            case 1:
                setBackgroundShape(new SquareBackgroundProvider());
                break;
            case 2:
                setBackgroundShape(new PinBackgroundProvider());
                break;
        }

        int shapeColor = styles.getColor(
            R.styleable.IconBadgeView_iconBadge_foregroundShapeColor,
            Integer.MIN_VALUE );
        if ( shapeColor != Integer.MIN_VALUE )
            setForegroundShapeColor( shapeColor );

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

    public void setBackgroundShape( BackgroundProvider backgroundShape ) {
        iconBadge.setBackgroundDrawer( backgroundShape );
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
        BackgroundProvider.Result result = iconBadge.getBackgroundDrawer().export(size);
        iconBadge.draw(canvas, size, result);
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        
        //TODO: Api Version Check
        int size = Math.min(w, h);
        ViewOutlineProvider outline = iconBadge.getBackgroundDrawer().export(size).outline;
        setOutlineProvider(outline);
    }
}
