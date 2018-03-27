package com.example.pollyplyim.androidfinalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by pollyplyim on 2018-03-03.
 */

public class MCDatabaseHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "MessagesDb.db"; //database name
    static final int VERSION_NUM = 1; //database version
    static final String KEY_ID = "ID"; //column name, primary key
    static final String KEY_QUESTION = "QuestionCol"; //column name
    static final String KEY_CHOICE_A = "ChoiceACol";
    static final String KEY_CHOICE_B = "ChoiceBCol";
    static final String KEY_CHOICE_C = "ChoiceCCol";
    static final String KEY_CHOICE_D = "ChoiceDCol";
    static final String KEY_CORRECT_CHOICE = "CorrChoiceCol";
    static final String KEY_CORRECT_TF = "CorrTFCol";
    static final String KEY_USER_TEXT_INPUT = "UserTextInputCol";
    static final String KEY_CORRECT_ANS_TEXT = "CorrTextCol";
    static final String KEY_USER_NUM_INPUT = "UserNumInputCol";
    static final String KEY_CORRECT_ANS_NUM = "CorrNumCol";
    static final String TB_NAME = "MCTable"; //table name

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
                + KEY_CORRECT_TF + " TEXT, "
                + KEY_USER_TEXT_INPUT + " TEXT,"
                + KEY_CORRECT_ANS_TEXT + "TEXT,"
                + KEY_USER_NUM_INPUT + "REAL,"
                + KEY_CORRECT_ANS_NUM + " REAL);";
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
