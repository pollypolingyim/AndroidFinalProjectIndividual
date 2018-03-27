package com.example.pollyplyim.androidfinalproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class MultipleChoiceActivity extends Activity {
    protected static final String ACTIVITY_NAME = "MultipleChoiceActivity";
    private ListView listView;
    private MCDatabaseHelper mdh;
    private static SQLiteDatabase sld;
    private ArrayList<String> rows;
    private ContentValues cv;
    private Cursor cursor;
    private boolean isChoice;
    private boolean isTF;
    private boolean isInput;
    private  RowAdapter rowAdapter;
    private Button a;
    private Button b;
    private Button c;
    private Button d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        listView = (ListView)findViewById(R.id.main_page);
        mdh = new MCDatabaseHelper(getApplicationContext());
        sld = mdh.getWritableDatabase();
        cv = new ContentValues();
        a = (Button)findViewById(R.id.buttonA);
        b = (Button)findViewById(R.id.buttonB);
        c = (Button)findViewById(R.id.buttonC);
        d = (Button)findViewById(R.id.buttonD);


        String str = "select * from " + MCDatabaseHelper.TB_NAME;
        cursor = sld.rawQuery(str, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            String question = cursor.getString(1);
            rows.add(question);
            if(cursor.getString(2)!=null) isChoice=true;
            else if(cursor.getString(7)!=null)isTF=true;
            else if(cursor.getString(2)==null && cursor.getString(7)==null)
                isInput=true;
            cursor.moveToNext();
        }

        rowAdapter=new RowAdapter( this );
        listView.setAdapter (rowAdapter);
    }

    private class RowAdapter extends ArrayAdapter<String> {
        public RowAdapter(Context ctx) {
            super(ctx, 0);
        }
        public int getCount(){
            return rows.size();
        }

        public String getItem(int position){
            return rows.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = MultipleChoiceActivity.this.getLayoutInflater();
            View result = null ;
            if(isChoice) {
                result = inflater.inflate(R.layout.choice_layout, null);
                a.setText(getItem(position));
                b.setText(getItem(position));
                c.setText(getItem(position));
                d.setText(getItem(position));
            }
            else if(isTF)
                result = inflater.inflate(R.layout.true_false_layout, null);
            else if(isInput)
                result = inflater.inflate(R.layout.text_input_layout, null);

            return result;
        }

        public long getId(int position){
            return position;
        }

    }

    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        mdh.close();
        sld.close();
    }
}
