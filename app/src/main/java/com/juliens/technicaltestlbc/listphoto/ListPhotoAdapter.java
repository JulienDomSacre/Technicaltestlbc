package com.juliens.technicaltestlbc.listphoto;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.juliens.technicaltestlbc.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by juliens on 14/10/2017.
 *
 * Adapter of the recycler view, in the MVP it's the presenter to know wich data show
 */
public class ListPhotoAdapter extends RecyclerView.Adapter<ListPhotoAdapter.ListPhotoHolder> {
    private final ListPhotoContract.Presenter presenter;

    /**
     * Initiate the adapter with the presenter
     * @param presenter set the presenter for to access the data
     */
    public ListPhotoAdapter(ListPhotoContract.Presenter presenter) {
        this.presenter = presenter;
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

    /**
     * The view holder of the recycler view
     */
    public static class ListPhotoHolder extends RecyclerView.ViewHolder implements ListPhotoContract.PhotoItemView{
        @BindView(R.id.tv_id)
        TextView tvId;
        @BindView(R.id.tv_album_id)
        TextView tvAlbumId;
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;

        public ListPhotoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void setImage(Context context, String url) {
            Picasso.with(context).load(url).into(ivPhoto);
        }

        @Override
        public void setIdAlbum(int idAlbum) {
            tvAlbumId.setText(String.valueOf(idAlbum));
        }

        @Override
        public void setId(int id) {
            tvId.setText(String.valueOf(id));
        }
    }
}
