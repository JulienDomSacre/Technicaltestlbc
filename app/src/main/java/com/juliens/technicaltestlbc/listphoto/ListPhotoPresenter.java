package com.juliens.technicaltestlbc.listphoto;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Bundle;

import com.juliens.technicaltestlbc.BasePresenter;
import com.juliens.technicaltestlbc.data.Photo;
import com.juliens.technicaltestlbc.data.local.PhotoLocalDataSource;
import com.juliens.technicaltestlbc.data.network.Service;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by juliens on 12/10/2017.
 */

/**
 *
 */
public class ListPhotoPresenter extends BasePresenter<ListPhotoContract.View> implements ListPhotoContract.Presenter {
    private static final String PROGRESS_BAR_STATE_KEY = "progress_bar_state_key";
    private static final String ERROR_STATE_KEY = "error_state_key";
    private static final String PHOTOS_STATE_KEY = "photos_state_key";
    private static final String IS_FIRST_LOAD_STATE_KEY = "is_first_load_state_key";
    private boolean mFirstLoad = true;
    private List<Photo> listPhoto = new ArrayList<>();
    private Bundle viewStateBundle = getStateBundle();


    public ListPhotoPresenter(){
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_CREATE)
    protected void onCreate() {
        listPhoto = viewStateBundle.getParcelableArrayList(PHOTOS_STATE_KEY);
        mFirstLoad = viewStateBundle.getBoolean(IS_FIRST_LOAD_STATE_KEY);
        loadPhotos();
        if (viewStateBundle.getBoolean(PROGRESS_BAR_STATE_KEY)) {
            if (isViewAttached())
                getView().setLoading(true);
        }
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_DESTROY)
    protected void onDestroy() {
        if (isViewAttached()){
            viewStateBundle.putParcelableArrayList(PHOTOS_STATE_KEY,(ArrayList)listPhoto);
            viewStateBundle.putBoolean(IS_FIRST_LOAD_STATE_KEY,mFirstLoad);
            getView().setLoading(false);
        }

    }

    @Override
    public void onPresenterDestroy() {
        super.onPresenterDestroy();
    }

    /*@Override
    public void subscribe() {
        loadPhotos();
    }

    @Override
    public void unsubscribe() {
    }*/

    private void loadPhotos(final boolean isFirstLoad, final boolean showProgress) {
        getView().setLoading(true);
        if (isFirstLoad) {
            PhotoLocalDataSource.getInstance(getView().getViewContext()).getPhotos()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(photos -> {
                listPhoto = photos;
                if (listPhoto.isEmpty())
                    Service.getInstance().getListPhoto().subscribe(this::loadComplete, this::loadError);
                else {
                    newDataReceive();
                }
            });
        }else
            newDataReceive();
    }
    @Override
    public void loadPhotos() {
        loadPhotos(mFirstLoad, true);
    }

    private void loadError(Throwable error) {
        getView().showErrorMessage(error.getMessage());
    }

    private void loadComplete(List<Photo> listPhoto) {
        this.listPhoto = listPhoto;
        newDataReceive();
        PhotoLocalDataSource.getInstance(getView().getViewContext()).savePhoto(listPhoto);
    }

    private void newDataReceive(){
        mFirstLoad = false;
        getView().newData();
        getView().setLoading(false);
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
        itemView.setImage(getView().getViewContext(),photo.getThumbnailUrl());
    }
}
