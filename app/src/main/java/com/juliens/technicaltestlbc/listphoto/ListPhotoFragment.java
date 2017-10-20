package com.juliens.technicaltestlbc.listphoto;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.juliens.technicaltestlbc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by juliens on 12/10/2017.
 *
 * The view of the MVP. Display a grid of Photos.
 */
public class ListPhotoFragment extends Fragment implements ListPhotoContract.View{
    private ListPhotoContract.Presenter mPresenter;
    private ListPhotoAdapter adapter;

    @BindView(R.id.ll_progressbar)
    LinearLayout llProgressBar;
    @BindView(R.id.rv_list_photos)
    RecyclerView rvListPhoto;
    @BindView(R.id.pb_load_data)
    ProgressBar pbLoadData;
    @BindView(R.id.tv_errorMessage)
    TextView tvErrorMessage;

    public ListPhotoFragment() {
        // Requires empty public constructor
    }

    public static ListPhotoFragment newInstance() {
        return new ListPhotoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO saveInstanceState of data or presenter?
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_photo, container, false);
        ButterKnife.bind(this,root);

        //TODO add scroll update if has a network error

        rvListPhoto.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter = new ListPhotoAdapter(mPresenter);
        rvListPhoto.setAdapter(adapter);
        return root;
    }

    /**
     * link the presenter to the view
     * @param presenter
     */
    @Override
    public void setPresenter(ListPhotoContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void dataChange() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void setLoading(boolean isInProgress) {
        if(isInProgress){
            llProgressBar.setVisibility(View.VISIBLE);
            pbLoadData.setVisibility(View.VISIBLE);
            tvErrorMessage.setVisibility(View.GONE);
        }else
            llProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        llProgressBar.setVisibility(View.VISIBLE);
        pbLoadData.setVisibility(View.GONE);
        tvErrorMessage.setVisibility(View.VISIBLE);
        tvErrorMessage.setText(R.string.error_message_load);
        Toast.makeText(getViewContext(),"error: "+errorMessage,Toast.LENGTH_LONG).show();
    }
}
