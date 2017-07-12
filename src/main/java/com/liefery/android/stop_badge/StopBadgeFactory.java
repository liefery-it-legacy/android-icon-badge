package com.liefery.android.stop_badge;

import android.annotation.SuppressLint;
import android.graphics.*;
import android.util.LruCache;

@SuppressLint( "NewApi" )
public class StopBadgeFactory {

    private static StopBadgeFactory instance = null;

    private BadgeCache cache = new BadgeCache();

    protected StopBadgeFactory() {
        // Exists only to defeat instantiation.
    }

    public static Bitmap getBitmap( StopBadge badge, int size ) {
        if ( instance == null ) {
            instance = new StopBadgeFactory();
        }

        if ( instance.cache.get( badge.toKey( size ) ) == null ) {
            Bitmap newBadge = badge.export( size );
            instance.cache.put( badge.toKey( size ), newBadge );
            return newBadge;
        } else {
            return instance.cache.get( badge.toKey( size ) );
        }
    }

    public class BadgeCache extends LruCache<String, Bitmap> {
        BadgeCache() {
            super( 2 * 1024 * 1024 ); // 2MiB
        }
    }
}
