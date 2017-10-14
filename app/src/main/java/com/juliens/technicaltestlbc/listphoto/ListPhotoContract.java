package com.juliens.technicaltestlbc.listphoto;

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

    }

    interface PhotoItemView {
        void setImage();//TODO format
        void setIdAlbum(int idAlbum);
    }
}
