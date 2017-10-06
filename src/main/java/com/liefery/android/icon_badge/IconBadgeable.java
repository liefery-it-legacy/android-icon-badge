package com.liefery.android.icon_badge;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import com.liefery.android.icon_badge.drawer.background.BackgroundProvider;
import com.liefery.android.icon_badge.drawer.foreground.ForegroundShapeDrawer;

public interface IconBadgeable {
    void setShapeArrowUp();

    void setShapeArrowDown();

    void setNumber( int number );

    void setForegroundDrawable( Drawable drawable );

    void setForegroundDrawer( ForegroundShapeDrawer drawer );

    int getBackgroundShapeColor();

    void setBackgroundShapeColor( @ColorInt int color );

    int getForegroundShapeColor();

    void setForegroundShapeColor( @ColorInt int color );

    float getAlpha();

    void setAlpha( @FloatRange( from = 0.0, to = 1.0 ) float alpha );

    float getElevation();

    void setElevation( float elevation );

    BackgroundProvider getBackgroundProvider();

    void setBackgroundProvider( BackgroundProvider provider );
}
