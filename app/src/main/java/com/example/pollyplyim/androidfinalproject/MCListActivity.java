package com.example.pollyplyim.androidfinalproject;

import android.support.v4.app.Fragment;

/**
 * Created by pollyplyim on 2018-03-04.
 */

public class MCListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new MCListFragment();
    }
}
