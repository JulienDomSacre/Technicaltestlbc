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

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by juliens on 15/10/2017.
 */

public class PhotoLocalDataSource implements PhotoDataSource {
    @Nullable
    private static PhotoLocalDataSource INSTANCE;

    @NonNull
    private final BriteDatabase mDatabaseHelper;

    @NonNull
    private Function<Cursor, Photo> mPhotoMapperFunction;

    private PhotoLocalDataSource(@NonNull Context context) {
        checkNotNull(context, "context cannot be null");
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

    @NonNull
    private Photo getPhoto(@NonNull Cursor c) {
        int id = c.getInt(c.getColumnIndexOrThrow(PhotoEntry.COLUMN_NAME_ID));
        int albumId = c.getInt(c.getColumnIndexOrThrow(PhotoEntry.COLUMN_NAME_ALBUM_ID));
        String title = c.getString(c.getColumnIndexOrThrow(PhotoEntry.COLUMN_NAME_TITLE));
        String url = c.getString(c.getColumnIndexOrThrow(PhotoEntry.COLUMN_NAME_URL));
        String thumbnailUrl = c.getString(c.getColumnIndexOrThrow(PhotoEntry.COLUMN_NAME_THUBNAIL_URL));
        return new Photo(id, albumId, title, url, thumbnailUrl);
    }

    @Override
    public void savePhoto(@NonNull List<Photo> photos) {
        checkNotNull(photos);

        //Not the best implementation :s
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
    public Flowable<List<Photo>> getPhotos() {
        return mDatabaseHelper.createQuery(PhotoEntry.TABLE_NAME,
                "SELECT * FROM " + PhotoEntry.TABLE_NAME)
                .mapToList(mPhotoMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }
}
