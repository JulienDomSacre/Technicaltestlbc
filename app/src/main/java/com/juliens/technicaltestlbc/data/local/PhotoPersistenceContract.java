package com.juliens.technicaltestlbc.data.local;

import android.provider.BaseColumns;

/**
 * Created by juliens on 15/10/2017.
 */

public class PhotoPersistenceContract {
    private PhotoPersistenceContract(){
    }

    protected static abstract class PhotoEntry implements BaseColumns{
        public static final String TABLE_NAME = "photos";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_ALBUM_ID = "albumId";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_THUBNAIL_URL = "thumbnailUrl";
    }
}
