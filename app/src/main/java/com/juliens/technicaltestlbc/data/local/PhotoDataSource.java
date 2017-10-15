package com.juliens.technicaltestlbc.data.local;

import android.support.annotation.NonNull;

import com.juliens.technicaltestlbc.data.Photo;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by juliens on 15/10/2017.
 */

public interface PhotoDataSource {
    void savePhoto(@NonNull List<Photo> photos);

    Flowable<List<Photo>> getPhotos();
}
