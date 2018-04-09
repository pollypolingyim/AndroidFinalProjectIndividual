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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import android.os.AsyncTask;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;

public class QuizListActivity extends Activity {
    protected static final String ACTIVITY_NAME = "QuizListActivity";
    private ListView listView;
    private EditText newQuestion;
    private Button addBut;
    private ArrayList<String> questions;
    private QuizAdapter quizAdapter;
    private MCDatabaseHelper mdh;
    private static SQLiteDatabase sld;
    private ContentValues cv;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        listView = (ListView) findViewById(R.id.questionList);
        newQuestion = (EditText) findViewById(R.id.newQuestion);
        addBut = (Button) findViewById(R.id.addBut);
        questions = new ArrayList<String>();

        mdh = new MCDatabaseHelper(getApplicationContext());
        sld = mdh.getWritableDatabase();
        cv = new ContentValues();

        String str = "select " + MCDatabaseHelper.KEY_QUESTION + ", " +
                 "from" + MCDatabaseHelper.TB_NAME;
        cursor = sld.rawQuery(str, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String question = cursor.getString(0);
            questions.add(question);
            cursor.moveToNext();
        }

        quizAdapter = new QuizAdapter(this);
        listView.setAdapter(quizAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    public void addQuestion(View view) {
        String question = newQuestion.getText().toString();

        cv.put(MCDatabaseHelper.KEY_QUESTION, question);
        sld.insert(MCDatabaseHelper.TB_NAME, "NullCol", cv);
        String query = "select "+MCDatabaseHelper.KEY_QUESTION + " from "
                + MCDatabaseHelper.TB_NAME;
        cursor = sld.rawQuery(query, null);

        questions.add(question);
        quizAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()

        newQuestion.setText("");
    }

    public void deleteQuestion() {

    }


    private class QuizAdapter extends ArrayAdapter<String> {
        public QuizAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return questions.size();
        }

        public String getItem(int position) {
            return questions.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = QuizListActivity.this.getLayoutInflater();
            final View result = inflater.inflate(R.layout.each_question_layout,null);
            if(getCount()>0) { //when the application was loaded before, the data will be retrieved from the database
                TextView question = (TextView) result.findViewById(R.id.question_text);
                question.setText(getItem(position)); // get the string at position
            }
            else{ //first time loading the application, it will parse the data from the XML.
                class QuizQuery extends AsyncTask<String, Integer, String>{
                    @Override
                    protected String doInBackground(String... params) {
                        try {
                            URL url = new URL(params[0]);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setReadTimeout(10000);
                            conn.setConnectTimeout(15000);
                            conn.setRequestMethod("GET");
                            conn.setDoInput(true);
                            conn.connect();

                            InputStream stream = conn.getInputStream();
                            XmlPullParser parser = Xml.newPullParser();
                            parser.setInput(stream, null);

                            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                                if (parser.getEventType() != XmlPullParser.START_TAG) {
                                    continue;
                                }
                                if (parser.getName().equals("MultipleChoiceQuestion")||
                                        parser.getName().equals("NumericQuestion")||
                                        parser.getName().equals("TrueFalseQuestion")) {
                                    String quest = parser.getAttributeValue(null, "value");
                                    TextView question = (TextView) result.findViewById(R.id.question_text);
                                    question.setText(quest);
                                }
                            }
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }
                QuizQuery quizQuery = new QuizQuery();
                String urlString = "http://torunski.ca/CST2335/QuizInstance.xml";
                quizQuery.execute(urlString);
            }
            return result;
        }



        @Override
        public long getItemId(int position) {
            cursor.moveToPosition(position);
            int colIndex = cursor.getColumnIndex(MCDatabaseHelper.KEY_ID);
            return cursor.getLong(colIndex);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        mdh.close();
        sld.close();
    }
}

