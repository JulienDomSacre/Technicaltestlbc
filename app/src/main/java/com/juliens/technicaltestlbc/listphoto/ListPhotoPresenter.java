package com.juliens.technicaltestlbc.listphoto;

import android.support.annotation.NonNull;

import com.juliens.technicaltestlbc.data.Photo;
import com.juliens.technicaltestlbc.data.local.PhotoLocalDataSource;
import com.juliens.technicaltestlbc.data.network.Service;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by juliens on 12/10/2017.
 *
 * The presenter of the MVP
 */
public class ListPhotoPresenter implements ListPhotoContract.Presenter {
    @NonNull
    private final ListPhotoContract.View mListPhotoView;
    private boolean mFirstLoad = true; // not really use for the moment, it's for the state change
    private List<Photo> listPhoto = new ArrayList<>(); //add cache?

    ListPhotoPresenter(@NonNull ListPhotoContract.View listPhotoView){
        mListPhotoView = checkNotNull(listPhotoView, "View cannot be null!");
        mListPhotoView.setPresenter(this);
    }


    @Override
    public void subscribe() {
        loadPhotos();
    }

    @Override
    public void unsubscribe() {
    }

    /**
     * Load the photo, select automaticaly the source of data (network or local)
     * @param isFirstLoad Pass in true if you want to load the data
     * @param showProgress Pass in true for show the progress in the UI
     */
    private void loadPhotos(final boolean isFirstLoad, final boolean showProgress) {
        if(showProgress)
            mListPhotoView.setLoading(true);
        if (isFirstLoad) {
            PhotoLocalDataSource.getInstance(mListPhotoView.getViewContext()).getPhotos()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(photos -> {
                listPhoto = photos;
                if (listPhoto.isEmpty())
                    Service.getInstance().getListPhoto().subscribe(this::loadComplete, this::loadError);
                else {
                    newDataReceive();
                }
            });
        }
    }


    @Override
    public void loadPhotos() {
        loadPhotos(mFirstLoad, true);
        mFirstLoad = false;
    }

    /**
     * Show the error message if the loading of network photo data have a problem
     * @param error The error information
     */
    private void loadError(Throwable error) {
        mListPhotoView.showErrorMessage(error.getMessage());
    }

    /**
     *  Receive the data loaded by the network, warn the ui and backups in the DB
     * @param listPhoto list of the Photo load from the network
     */
    private void loadComplete(List<Photo> listPhoto) {
        this.listPhoto = listPhoto;
        newDataReceive();
        PhotoLocalDataSource.getInstance(mListPhotoView.getViewContext()).savePhotos(listPhoto);
    }

    /**
     * warns the UI that new data is available
     */
    private void newDataReceive(){
        mListPhotoView.dataChange();
        mListPhotoView.setLoading(false);
    }

    @Override
    public int getPhotoItemCount() {
        return listPhoto.size();
    }

    @Override
    public void onBindPhotoItemViewAtPosition(int position, ListPhotoContract.PhotoItemView itemView) {
        Photo photo = listPhoto.get(position);
        itemView.setIdAlbum(photo.getAlbumId());
        itemView.setId(photo.getId());
        itemView.setImage(mListPhotoView.getViewContext(),photo.getThumbnailUrl());
    }
}
