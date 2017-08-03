package com.liefery.android.stop_badge;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.*;
import android.util.LruCache;

public class StopBadgeFactory {

    private static StopBadgeFactory instance = null;

    private LruCache<String, Bitmap> cache;

    protected StopBadgeFactory() {
        // Exists only to defeat instantiation.
    }

    public static Bitmap getBitmap( Context context, StopBadge badge, int size ) {
        if ( instance == null ) {
            instance = new StopBadgeFactory();

            int memClass = ( (ActivityManager) context
                            .getSystemService( Context.ACTIVITY_SERVICE ) )
                            .getMemoryClass();
            int cacheSize = 1024 * 1024 * memClass / 8;
            instance.cache = new LruCache<String, Bitmap>( cacheSize );
        }

        if ( instance.cache.get( badge.toKey( size ) ) == null ) {
            Bitmap newBadge = badge.export( size );
            instance.cache.put( badge.toKey( size ), newBadge );
            return newBadge;
        } else {
            return instance.cache.get( badge.toKey( size ) );
        }
    }
}
