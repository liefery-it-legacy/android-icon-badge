package com.liefery.android.icon_badge;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.liefery.android.icon_badge.background.BackgroundProvider;
import com.liefery.android.icon_badge.foreground.ForegroundShapeDrawer;

public interface IconBadgeable {
    void setBackgroundShapeCircle();

    void setBackgroundShapeSquare();

    void setBackgroundShapePin();

    void setForegroundShapeArrowUp();

    void setForegroundShapeArrowDown();

    void setNumber( int number );

    void setForegroundDrawable( @NonNull Drawable drawable );

    void setForegroundDrawer( @Nullable ForegroundShapeDrawer drawer );

    @ColorInt
    int getBackgroundShapeColor();

    void setBackgroundShapeColor( @ColorInt int color );

    @ColorInt
    int getForegroundShapeColor();

    void setForegroundShapeColor( @ColorInt int color );

    float getAlpha();

    void setAlpha( @FloatRange( from = 0.0, to = 1.0 ) float alpha );

    float getElevation();

    void setElevation( float elevation );

    @Nullable
    BackgroundProvider getBackgroundProvider();

    void setBackgroundProvider( @Nullable BackgroundProvider provider );
}
