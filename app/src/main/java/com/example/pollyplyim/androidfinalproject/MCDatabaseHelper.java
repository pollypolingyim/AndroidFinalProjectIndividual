package com.example.pollyplyim.androidfinalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by pollyplyim on 2018-03-03.
 */

public class MCDatabaseHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "Quiz.db"; //database name
    static final int VERSION_NUM = 1; //database version
    static final String KEY_ID = "ID"; //column name, primary key
    static final String KEY_QUESTION = "QuestionCol"; //column name
    static final String KEY_CHOICE_A = "ChoiceACol";
    static final String KEY_CHOICE_B = "ChoiceBCol";
    static final String KEY_CHOICE_C = "ChoiceCCol";
    static final String KEY_CHOICE_D = "ChoiceDCol";
    static final String KEY_CORRECT_CHOICE = "CorrChoiceCol";
    static final String KEY_ANSWER_TF = "CorrAnsTFCol";
    static final String KEY_ANSWER_NUMERIC = "CorrAnsNumCol";
    static final String KEY_ACCURACY = "AccuracyCol";
    static final String TB_NAME = "QuizTable"; //table name

    public MCDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    //create the table
    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        String CREATE_TABLE = "CREATE TABLE " + TB_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_QUESTION + " TEXT,"
                + KEY_CHOICE_A + " TEXT, "
                + KEY_CHOICE_B + " TEXT,"
                + KEY_CHOICE_C + " TEXT,"
                + KEY_CHOICE_D + " TEXT,"
                + KEY_CORRECT_CHOICE + " TEXT,"
                + KEY_ANSWER_TF + " TEXT, "
                + KEY_ANSWER_NUMERIC + " REAL,"
                + KEY_ACCURACY + " REAL);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, old Version=" +
                oldVersion + " newVersion="+newVersion );
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME); //drop old table
        onCreate(db);
    }
}
