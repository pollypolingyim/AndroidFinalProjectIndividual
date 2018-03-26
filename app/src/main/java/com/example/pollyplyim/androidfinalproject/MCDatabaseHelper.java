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
    static final String KEY_CHOICE = "ChoiceCol";
    static final String KEY_USER_ANS = "UserAnsCol";
    static final String KEY_CORR_ANS = "CorrAnsCol";
    static final String TB_NAME = "MessageTb"; //table name

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
                + KEY_USER_ANS + " TEXT,"
                + KEY_CORR_ANS + " TEXT,"
                + KEY_QUESTION + " TEXT);";
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
