package com.juliens.technicaltestlbc.listphoto;

import android.content.Context;

import com.juliens.technicaltestlbc.BasePresenter;
import com.juliens.technicaltestlbc.BaseView;

/**
 * Created by juliens on 12/10/2017.
 */

public interface ListPhotoContract {
    interface Presenter extends BasePresenter {
        void loadPhotos();
        int getPhotoItemCount();
        void onBindPhotoItemViewAtPosition(int position, PhotoItemView itemView);
    }

    interface View extends BaseView<Presenter> {
        void newData();
        Context getViewContext();
    }

    interface PhotoItemView {
        void setImage(Context context,String url);//TODO format
        void setIdAlbum(int idAlbum);
        void setId(int id);// TODO delete later
    }
}
