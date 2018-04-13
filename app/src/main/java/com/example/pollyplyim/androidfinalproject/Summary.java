package com.example.pollyplyim.androidfinalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Summary extends AppCompatActivity {
    StudentDAO studentDao;
    TextView numStudents;
    TextView avgGpa;
    TextView highestGpaStudentName;
    TextView highestGpa;
    TextView lowestGpaStudentName;
    TextView lowestGpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);
        studentDao = new StudentDAO(this);

        numStudents =(TextView)findViewById(R.id.num_students_actual);
        numStudents.setText(studentDao.studentCount()+"");

        avgGpa = (TextView)findViewById(R.id.avg_gpa_actual);
        avgGpa.setText(studentDao.avgGpa());

        highestGpaStudentName = (TextView)findViewById(R.id.highest_student_name_actual);
        highestGpaStudentName.setText(studentDao.getHighestGpaStudent());

        highestGpa = (TextView)findViewById(R.id.highest_gpa_actual);
        highestGpa.setText(studentDao.getHighestGpa());

        lowestGpaStudentName = (TextView)findViewById(R.id.lowest_student_name_actual);
        lowestGpaStudentName.setText(studentDao.getLowestGpaStudent());

        lowestGpa = (TextView)findViewById(R.id.lowest_gpa_actual);
        lowestGpa.setText(studentDao.getLowestGpa());

    }
}
