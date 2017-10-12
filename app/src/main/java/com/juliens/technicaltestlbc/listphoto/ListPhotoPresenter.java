package com.juliens.technicaltestlbc.listphoto;

import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by juliens on 12/10/2017.
 */

/**
 *
 */
public class ListPhotoPresenter implements ListPhotoContract.Presenter {
    @NonNull
    private CompositeDisposable mCompositeDisposable;
    @NonNull
    private final ListPhotoContract.View mListPhotoView;

    public ListPhotoPresenter(@NonNull ListPhotoContract.View listPhotoView){
        mCompositeDisposable = new CompositeDisposable();
        mListPhotoView = checkNotNull(listPhotoView, "tasksView cannot be null!");
        mListPhotoView.setPresenter(this);
    }
    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }
}
