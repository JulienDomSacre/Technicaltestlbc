package com.juliens.technicaltestlbc.data.local;

import android.support.annotation.NonNull;

import com.juliens.technicaltestlbc.data.Photo;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by juliens on 15/10/2017.
 *
 * Entry point for accessing photo data
 */
public interface PhotoDataSource {
    void savePhotos(@NonNull List<Photo> photos);

    /**
     * Get all photos stored in the DB
     * @return Stream of list of photo
     */
    Observable<List<Photo>> getPhotos();
}
