package com.juliens.technicaltestlbc.listphoto;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.juliens.technicaltestlbc.BaseFragment;
import com.juliens.technicaltestlbc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by juliens on 12/10/2017.
 */

public class ListPhotoFragment extends BaseFragment<ListPhotoContract.View,ListPhotoContract.Presenter> implements ListPhotoContract.View{
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
        Bundle arguments = new Bundle();
        ListPhotoFragment fragment = new ListPhotoFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_photo, container, false);
        ButterKnife.bind(this,root);

        //TODO add scroll update

        rvListPhoto.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter = new ListPhotoAdapter(presenter);
        rvListPhoto.setAdapter(adapter);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void newData() {
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

    @Override
    protected ListPhotoContract.Presenter initPresenter() {
        return new ListPhotoPresenter();
    }
}
