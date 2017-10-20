package com.juliens.technicaltestlbc.listphoto;

import android.content.Context;

import com.juliens.technicaltestlbc.BasePresenter;
import com.juliens.technicaltestlbc.BaseView;

/**
 * Created by juliens on 12/10/2017.
 * <p>
 * This specifies the contract between the view and the presenter.
 */
interface ListPhotoContract {
    interface Presenter extends BasePresenter {
        void loadPhotos();

        int getPhotoItemCount();

        void onBindPhotoItemViewAtPosition(int position, PhotoItemView itemView);
    }

    interface View extends BaseView<Presenter> {
        void dataChange();

        Context getViewContext();

        void setLoading(boolean isInProgress);

        void showErrorMessage(String errorMessage);
    }

    /**
     * The view for the recycler view holder
     */
    interface PhotoItemView {
        void setImage(Context context, String url);

        void setIdAlbum(int idAlbum);

        void setId(int id);// TODO delete later
    }
}
