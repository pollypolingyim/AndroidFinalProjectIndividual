package com.example.pollyplyim.androidfinalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements
        CustomStudentDialogFragment.StudentDialogFragmentListener {

    private Fragment contentFragment;
    private StudentListFragment studentListFragment;
    private StudentAddFragment studentAddFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        FragmentManager fragmentManager = getSupportFragmentManager();

        /*
         * This is called when orientation is changed.
         */
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("content")) {
                String content = savedInstanceState.getString("content");
                if (content.equals(StudentAddFragment.ARG_ITEM_ID)) {
                    if (fragmentManager
                            .findFragmentByTag(StudentAddFragment.ARG_ITEM_ID) != null) {
                        setFragmentTitle(R.string.add_student);
                        contentFragment = fragmentManager
                                .findFragmentByTag(StudentAddFragment.ARG_ITEM_ID);
                    }
                }
            }
            if (fragmentManager.findFragmentByTag(StudentListFragment.ARG_ITEM_ID) != null) {
                studentListFragment = (StudentListFragment) fragmentManager
                        .findFragmentByTag(StudentListFragment.ARG_ITEM_ID);
                contentFragment = studentListFragment;
            }
        } else {
            studentListFragment = new StudentListFragment();
            setFragmentTitle(R.string.app_name);
            switchContent(studentListFragment, StudentListFragment.ARG_ITEM_ID);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (contentFragment instanceof StudentAddFragment) {
            outState.putString("content", StudentAddFragment.ARG_ITEM_ID);
        } else {
            outState.putString("content", StudentListFragment.ARG_ITEM_ID);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                setFragmentTitle(R.string.add_student);
                studentAddFragment = new StudentAddFragment();
                switchContent(studentAddFragment, StudentAddFragment.ARG_ITEM_ID);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.popBackStackImmediate())
            ;

        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.replace(R.id.content_frame, fragment, tag);
            if (!(fragment instanceof StudentListFragment)) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            contentFragment = fragment;
        }
    }

    protected void setFragmentTitle(int resourseId) {
        setTitle(resourseId);
//       getActionBar().setTitle(resourseId);

    }



    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else if (contentFragment instanceof StudentListFragment
                || fm.getBackStackEntryCount() == 0) {
            onShowQuitDialog();
        }
    }

    public void onShowQuitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        builder.setMessage(getResources().getString(R.string.quit_question));
        builder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        builder.setNegativeButton(android.R.string.no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    @Override
    public void onFinishDialog() {
        if (studentListFragment != null) {
            studentListFragment.updateView();
        }
    }
}