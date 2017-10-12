package com.juliens.technicaltestlbc.listphoto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.juliens.technicaltestlbc.R;
import com.juliens.technicaltestlbc.util.ActivityUtils;

/**
 * This is the view in the MVP pattern
 */
public class ListPhotoActivity extends AppCompatActivity {
    private ListPhotoPresenter mListPhotoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photo);

        //TODO set the toolbar

        //TODO set the fragment
        ListPhotoFragment listPhotoFragment =
                (ListPhotoFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (listPhotoFragment == null) {
            // Create the fragment
            listPhotoFragment = ListPhotoFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), listPhotoFragment, R.id.contentFrame);
        }

        //TODO create the presenter
        // Create the presenter
        mListPhotoPresenter = new ListPhotoPresenter(listPhotoFragment);
    }
}