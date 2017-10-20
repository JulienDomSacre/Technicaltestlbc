package com.juliens.technicaltestlbc.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by juliens on 15/10/2017.
 *
 * Data base helper for open connection, create and upgrade the base
 */
public class PhotoDbHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;

    private  static final String DATABASE_NAME = "Photo.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String INT_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    /**
     * Structure of the base
     */
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PhotoPersistenceContract.PhotoEntry.TABLE_NAME + " (" +
                    PhotoPersistenceContract.PhotoEntry.COLUMN_NAME_ID + INT_TYPE + " PRIMARY KEY," +
                    PhotoPersistenceContract.PhotoEntry.COLUMN_NAME_ALBUM_ID + INT_TYPE + COMMA_SEP +
                    PhotoPersistenceContract.PhotoEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    PhotoPersistenceContract.PhotoEntry.COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
                    PhotoPersistenceContract.PhotoEntry.COLUMN_NAME_THUBNAIL_URL + TEXT_TYPE +
                    " )";


    /**
     * open the connection to the DB
     * @param context
     */
    PhotoDbHelper(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //not required
    }
}
