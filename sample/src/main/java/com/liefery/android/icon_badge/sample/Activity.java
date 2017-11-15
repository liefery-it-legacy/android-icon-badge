package com.liefery.android.icon_badge.sample;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import com.liefery.android.icon_badge.IconBadge;

public class Activity extends android.app.Activity {
    private static int dpToPx( int dp ) {
        return (int) ( dp * Resources.getSystem().getDisplayMetrics().density );
    }

    @Override
    public void onCreate( Bundle state ) {
        super.onCreate( state );

        setContentView( R.layout.main );

        ImageView image = findViewById( R.id.image );
        IconBadge badge = new IconBadge( this );
        badge.setBackgroundShapePin();
        badge.setBackgroundShapeColor( Color.RED );
        badge.setNumber(1234);
//        badge.setForegroundShapeArrowUp();
        badge.setForegroundShapeColor( Color.WHITE );
        badge.setElevation( dpToPx( 5 ) );
        image.setImageBitmap( badge.export( dpToPx( 50 ) ) );
    }
}
