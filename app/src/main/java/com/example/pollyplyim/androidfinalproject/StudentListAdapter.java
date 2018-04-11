package com.example.pollyplyim.androidfinalproject;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StudentListAdapter extends ArrayAdapter<Student> {

    private Context context;
    List<Student> students;


    public StudentListAdapter(Context context, List<Student> students) {
        super(context, R.layout.list_item, students);
        this.context = context;
        this.students = students;
    }

    private class ViewHolder {
        TextView studentIdTxt;
        TextView studentNameTxt;
        TextView studentGpaTxt;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Student getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();

            holder.studentIdTxt = (TextView) convertView
                    .findViewById(R.id.txt_student_id);
            holder.studentNameTxt = (TextView) convertView
                    .findViewById(R.id.txt_student_name);
            holder.studentGpaTxt = (TextView) convertView
                    .findViewById(R.id.txt_student_gpa);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Student student = (Student) getItem(position);
        holder.studentIdTxt.setText(student.getId() + "");
        holder.studentNameTxt.setText(student.getName());
        holder.studentGpaTxt.setText(student.getGpa() + "");

        return convertView;
    }

    @Override
    public void add(Student student) {
        students.add(student);
        notifyDataSetChanged();
        super.add(student);
    }

    @Override
    public void remove(Student student) {
        students.remove(student);
        notifyDataSetChanged();
        super.remove(student);
    }
}