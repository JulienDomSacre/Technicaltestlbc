package com.juliens.technicaltestlbc.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by juliens on 12/10/2017.
 */

public class ActivityUtils {
    /**
     * From the blueprint android
     * @param fragmentManager
     * @param fragment Fragment who you want to add in the container
     * @param frameId Id of the container where you add the fragment
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
