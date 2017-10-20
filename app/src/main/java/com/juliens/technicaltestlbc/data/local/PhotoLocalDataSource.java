package com.juliens.technicaltestlbc.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.juliens.technicaltestlbc.data.Photo;
import com.juliens.technicaltestlbc.data.local.PhotoPersistenceContract.PhotoEntry;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by juliens on 15/10/2017.
 *
 * Implementation of the data source
 */
public class PhotoLocalDataSource implements PhotoDataSource {
    @Nullable
    private static PhotoLocalDataSource INSTANCE;

    @NonNull
    private final BriteDatabase mDatabaseHelper;

    @NonNull
    private Function<Cursor, Photo> mPhotoMapperFunction;

    /**
     * Init the data source. Use the SqlBrite for the reactive stream in the queries
     * @param context
     */
    private PhotoLocalDataSource(@NonNull Context context) {
        PhotoDbHelper dbHelper = new PhotoDbHelper(context);
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(dbHelper, Schedulers.io());
        mPhotoMapperFunction = this::getPhoto;
    }

    public static PhotoLocalDataSource getInstance(
            @NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PhotoLocalDataSource(context);
        }
        return INSTANCE;
    }

    /**
     * Transform an item into a photo
     * @param c the sql cursor
     * @return a photo
     */
    @NonNull
    private Photo getPhoto(@NonNull Cursor c) {
        int id = c.getInt(c.getColumnIndexOrThrow(PhotoEntry.COLUMN_NAME_ID));
        int albumId = c.getInt(c.getColumnIndexOrThrow(PhotoEntry.COLUMN_NAME_ALBUM_ID));
        String title = c.getString(c.getColumnIndexOrThrow(PhotoEntry.COLUMN_NAME_TITLE));
        String url = c.getString(c.getColumnIndexOrThrow(PhotoEntry.COLUMN_NAME_URL));
        String thumbnailUrl = c.getString(c.getColumnIndexOrThrow(PhotoEntry.COLUMN_NAME_THUBNAIL_URL));
        return new Photo(id, albumId, title, url, thumbnailUrl);
    }

    /**
     * Save a list of photos in asynchronous
     * @param photos List of photos
     */
    @Override
    public void savePhotos(@NonNull List<Photo> photos) {
        //Not the best implementation :s
        //TODO use RX
        Runnable runnable = () -> {
            photos.forEach(photo -> {
                ContentValues values = new ContentValues();
                values.put(PhotoEntry.COLUMN_NAME_ID, photo.getId());
                values.put(PhotoEntry.COLUMN_NAME_ALBUM_ID, photo.getId());
                values.put(PhotoEntry.COLUMN_NAME_TITLE, photo.getTitle());
                values.put(PhotoEntry.COLUMN_NAME_URL, photo.getUrl());
                values.put(PhotoEntry.COLUMN_NAME_THUBNAIL_URL, photo.getThumbnailUrl());
                mDatabaseHelper.insert(PhotoEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);
            });
        };
        new Thread(runnable).start();
    }

    @Override
    public Observable<List<Photo>> getPhotos() {
        return mDatabaseHelper.createQuery(PhotoEntry.TABLE_NAME,
                "SELECT * FROM " + PhotoEntry.TABLE_NAME)
                .mapToList(mPhotoMapperFunction);
    }
}
