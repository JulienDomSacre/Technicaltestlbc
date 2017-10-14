package com.juliens.technicaltestlbc.listphoto;

import android.support.annotation.NonNull;

import com.juliens.technicaltestlbc.data.ListPhoto;
import com.juliens.technicaltestlbc.data.Photo;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by juliens on 12/10/2017.
 */

/**
 *
 */
public class ListPhotoPresenter implements ListPhotoContract.Presenter {
    @NonNull
    private final ListPhotoContract.View mListPhotoView;

    private ListPhoto listPhoto;

    public ListPhotoPresenter(@NonNull ListPhotoContract.View listPhotoView){
        mListPhotoView = checkNotNull(listPhotoView, "tasksView cannot be null!");
        mListPhotoView.setPresenter(this);
    }
    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }


    @Override
    public void loadPhotos() {

    }

    @Override
    public int getPhotoItemCount() {
        return listPhoto.getPhotoList().size();
    }

    @Override
    public void onBindPhotoItemViewAtPosition(int position, ListPhotoContract.PhotoItemView itemView) {
        Photo photo = listPhoto.getPhotoList().get(position);
        //TODO set the view with the data
    }


}
