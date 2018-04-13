package com.example.pollyplyim.androidfinalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class StudentDAO extends StudentDBDAO {

    private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
            + " =?";

    public StudentDAO(Context context) {
        super(context);
    }


    public long save(Student student) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.NAME_COLUMN, student.getName());
        values.put(DataBaseHelper.STUDENT_GPA, student.getGpa());

        return database.insert(DataBaseHelper.STUDENT_TABLE, null, values);
    }

    public long update(Student student) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.NAME_COLUMN, student.getName());
        values.put(DataBaseHelper.STUDENT_GPA, student.getGpa());

        long result = database.update(DataBaseHelper.STUDENT_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(student.getId()) });
        return result;

    }

    public int delete(Student student) {
        return database.delete(DataBaseHelper.STUDENT_TABLE, WHERE_ID_EQUALS,
                new String[] { student.getId() + "" });
    }

    public double avgGpa(){
        String sql = "SELECT AVG (" + DataBaseHelper.STUDENT_GPA +") as AVG FROM "+DataBaseHelper.STUDENT_TABLE;
        double result = 0;
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            result = cursor.getDouble(0);
        }
        DecimalFormat df=new DecimalFormat("0.##");
        df.format(result);
        cursor.close();
        return result;
    }

    public int studentCount(){
        String sql = "SELECT SUM(*) FROM "+DataBaseHelper.STUDENT_TABLE;
        Cursor cursor = database.rawQuery(sql, null);
        int result = 0;
        if(cursor.moveToFirst()){
            result = cursor.getInt(0);
        }
        cursor.close();
        return result;
    }

    public String getHighestGpaStudent(){
        String sql = "SELECT "+DataBaseHelper.NAME_COLUMN + " FROM "+ DataBaseHelper.STUDENT_TABLE
                + " WHERE " + DataBaseHelper.STUDENT_GPA+ " = (SELECT MAX(" + DataBaseHelper.STUDENT_GPA
                + ") FROM " + DataBaseHelper.STUDENT_TABLE;
        Cursor cursor = database.rawQuery(sql, null);
        String result = "";
        if(cursor.moveToFirst()){
            result = cursor.getString(0);
        }
        cursor.close();
        return result;
    }

    public double getHighestGpa(){
        String sql = "SELECT MAX(*) FROM "+DataBaseHelper.STUDENT_TABLE;
        Cursor cursor = database.rawQuery(sql, null);
        double result = 0;
        if(cursor.moveToFirst()){
            result = cursor.getDouble(0);
        }
        cursor.close();
        return result;
    }

    public String getLowestGpaStudent(){
        String sql = "SELECT "+DataBaseHelper.NAME_COLUMN + " FROM "+ DataBaseHelper.STUDENT_TABLE
                + " WHERE " + DataBaseHelper.STUDENT_GPA+ " = (SELECT MIN(" + DataBaseHelper.STUDENT_GPA
                + ") FROM " + DataBaseHelper.STUDENT_TABLE;
        Cursor cursor = database.rawQuery(sql, null);
        String result = "";
        if(cursor.moveToFirst()){
            result = cursor.getString(0);
        }
        cursor.close();
        return result;
    }

    public double getLowestGpa(){
        String sql = "SELECT MIN(*) FROM "+DataBaseHelper.STUDENT_TABLE;
        Cursor cursor = database.rawQuery(sql, null);
        double result = 0;
        if(cursor.moveToFirst()){
            result = cursor.getDouble(0);
        }
        cursor.close();
        return result;
    }

    public ArrayList<Student> getStudents() {
        ArrayList<Student> students = new ArrayList<Student>();

        Cursor cursor = database.query(DataBaseHelper.STUDENT_TABLE,
                new String[] { DataBaseHelper.ID_COLUMN,
                        DataBaseHelper.NAME_COLUMN,
                        DataBaseHelper.STUDENT_GPA }, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            Student student = new Student();
            student.setId(cursor.getInt(0));
            student.setName(cursor.getString(1));
            student.setGpa(cursor.getDouble(2));

            students.add(student);
        }
        return students;

    }

    public Student getStudent(long id) {
        Student student = null;

        String sql = "SELECT * FROM " + DataBaseHelper.STUDENT_TABLE
                + " WHERE " + DataBaseHelper.ID_COLUMN + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            student = new Student();
            student.setId(cursor.getInt(0));
            student.setName(cursor.getString(1));
            student.setGpa(cursor.getDouble(2));
        }
        return student;
    }


}

