package com.juliens.technicaltestlbc.listphoto;

import android.content.Context;

import com.juliens.technicaltestlbc.BaseContract;

/**
 * Created by juliens on 12/10/2017.
 */

public interface ListPhotoContract {


    interface View extends BaseContract.View {
        void newData();
        Context getViewContext();
        void setLoading(boolean isInProgress);
        void showErrorMessage(String errorMessage);
    }

    interface Presenter extends BaseContract.Presenter<ListPhotoContract.View> {
        void loadPhotos();
    int getPhotoItemCount();
    void onBindPhotoItemViewAtPosition(int position, PhotoItemView itemView);
}

    interface PhotoItemView {
        void setImage(Context context,String url);
        void setIdAlbum(int idAlbum);
        void setId(int id);// TODO delete later
    }
}
