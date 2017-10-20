package com.juliens.technicaltestlbc.listphoto;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.juliens.technicaltestlbc.R;
import com.juliens.technicaltestlbc.util.ActivityUtils;

/**
 * This is the main activity of the application
 */
public class ListPhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photo);

        //set the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        //TODO add the navigation drawer

        //set the fragment
        ListPhotoFragment listPhotoFragment =
                (ListPhotoFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (listPhotoFragment == null) {
            // Create the fragment
            listPhotoFragment = ListPhotoFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), listPhotoFragment, R.id.contentFrame);
        }

        //create the presenter
       new ListPhotoPresenter(listPhotoFragment);
    }
}