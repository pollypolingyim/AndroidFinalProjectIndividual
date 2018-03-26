package com.example.pollyplyim.androidfinalproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class MultipleChoiceActivity extends SingleFragmentActivity {
    protected static final String ACTIVITY_NAME = "ChatWindowActivity";
    private ListView listView;
    private EditText editT;
    private Button sendBut;
    private ArrayList<String> chatMsg;
    //private ChatAdapter messageAdapter;
    private MCDatabaseHelper mdh;
    private static SQLiteDatabase sld;
    private ContentValues cv;
    private Cursor cursor;

    @Override
    protected Fragment createFragment(){
        return new MCListFragment();
    }






    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something when a list item is clicked
    }
}
