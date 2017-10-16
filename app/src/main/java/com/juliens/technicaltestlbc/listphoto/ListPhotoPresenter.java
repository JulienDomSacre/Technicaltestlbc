package com.juliens.technicaltestlbc.listphoto;

import android.support.annotation.NonNull;

import com.juliens.technicaltestlbc.data.Photo;
import com.juliens.technicaltestlbc.data.local.PhotoLocalDataSource;
import com.juliens.technicaltestlbc.data.network.Service;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by juliens on 12/10/2017.
 */

/**
 *
 */
public class ListPhotoPresenter implements ListPhotoContract.Presenter {
    @NonNull
    private final ListPhotoContract.View mListPhotoView; //add cache?
    private boolean mFirstLoad = true;
    private List<Photo> listPhoto = new ArrayList<>();

    public ListPhotoPresenter(@NonNull ListPhotoContract.View listPhotoView){
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

    private void loadPhotos(final boolean isFirstLoad, final boolean showProgress) {
        mListPhotoView.setLoading(true);
        if (isFirstLoad) {
            PhotoLocalDataSource.getInstance(mListPhotoView.getViewContext()).getPhotos().subscribe(photos -> listPhoto = photos);
            if (listPhoto.isEmpty())
                Service.getInstance().getListPhoto().subscribe(this::loadComplete, this::loadError);
            else {
                newDataReceive();
            }
        }
    }
    @Override
    public void loadPhotos() {
        loadPhotos(mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadError(Throwable error) {
        mListPhotoView.showErrorMessage(error.getMessage());
    }

    private void loadComplete(List<Photo> listPhoto) {
        this.listPhoto = listPhoto;
        newDataReceive();
        PhotoLocalDataSource.getInstance(mListPhotoView.getViewContext()).savePhoto(listPhoto);
    }

    private void newDataReceive(){
        mListPhotoView.newData();
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
