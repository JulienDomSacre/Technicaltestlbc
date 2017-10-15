package com.juliens.technicaltestlbc.listphoto;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juliens.technicaltestlbc.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by juliens on 12/10/2017.
 */

public class ListPhotoFragment extends Fragment implements ListPhotoContract.View{
    private ListPhotoContract.Presenter mPresenter;
    private ListPhotoAdapter adapter;

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
        RecyclerView recyclerView = root.findViewById(R.id.rc_photos_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        adapter = new ListPhotoAdapter(mPresenter);
        recyclerView.setAdapter(adapter);
        setHasOptionsMenu(true);
        return root;
    }

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
    public void newData() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

}
