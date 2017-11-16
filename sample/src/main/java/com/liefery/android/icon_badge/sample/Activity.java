package com.liefery.android.icon_badge.sample;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.support.v7.content.res.AppCompatResources;
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
        badge.setForegroundShapeArrowUp();
        badge.setForegroundShapeColor( Color.WHITE );
        badge.setElevation( dpToPx( 5 ) );
        image.setImageBitmap( badge.export( dpToPx( 50 ) ) );
    
        ImageView image2 = findViewById( R.id.image2 );
        IconBadge badge2 = new IconBadge( this );
        badge2.setBackgroundShapeCircle();
        badge2.setBackgroundShapeColor( Color.parseColor("#4CAF50") );
        badge2.setForegroundDrawable(AppCompatResources.getDrawable( this, R.drawable.ic_checkmark_all ));
        badge2.setForegroundShapeColor( Color.parseColor("#87000000") );
        badge2.setElevation( dpToPx( 2 ) );
        image2.setImageBitmap( badge2.export( dpToPx( 50 ) ) );
    
        ImageView image3 = findViewById( R.id.image3 );
        IconBadge badge3 = new IconBadge( this );
        badge3.setBackgroundShapePin();
        badge3.setBackgroundShapeColor( Color.parseColor("#FFEB3B") );
        badge3.setForegroundShapeArrowDown();
        badge3.setForegroundShapeColor( Color.parseColor("#87000000") );
        image3.setImageBitmap( badge3.export( dpToPx( 50 ) ) );
    
        ImageView image4 = findViewById( R.id.image4 );
        IconBadge badge4 = new IconBadge( this );
        badge4.setBackgroundShapeSquare();
        badge4.setBackgroundShapeColor( Color.parseColor("#F44336") );
        badge4.setNumber(69);
        badge4.setForegroundShapeColor( Color.parseColor("#87ffffff") );
        badge4.setElevation( dpToPx( 4 ) );
        image4.setImageBitmap( badge4.export( dpToPx( 50 ) ) );
    }
}
