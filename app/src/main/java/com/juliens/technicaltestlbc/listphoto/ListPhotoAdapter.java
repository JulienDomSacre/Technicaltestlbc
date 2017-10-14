package com.juliens.technicaltestlbc.listphoto;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juliens.technicaltestlbc.R;

/**
 * Created by juliens on 14/10/2017.
 */

public class ListPhotoAdapter extends RecyclerView.Adapter<ListPhotoAdapter.ListPhotoHolder> {
    private final ListPhotoPresenter presenter;

    public ListPhotoAdapter(ListPhotoPresenter listPhotoPresenter) {
        this.presenter = listPhotoPresenter;
    }

    @Override
    public ListPhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListPhotoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo,parent,false));
    }

    @Override
    public void onBindViewHolder(ListPhotoHolder holder, int position) {
        presenter.onBindPhotoItemViewAtPosition(position, holder);
    }


    @Override
    public int getItemCount() {
        return presenter.getPhotoItemCount();
    }

    public static class ListPhotoHolder extends RecyclerView.ViewHolder implements ListPhotoContract.PhotoItemView {
        //TODO bind widget layout
        public ListPhotoHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setImage() {

        }

        @Override
        public void setIdAlbum(int idAlbum) {

        }
    }
}
