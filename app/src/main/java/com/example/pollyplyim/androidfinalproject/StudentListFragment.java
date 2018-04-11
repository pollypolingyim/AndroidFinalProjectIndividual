package com.example.pollyplyim.androidfinalproject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;



public class StudentListFragment extends Fragment implements OnItemClickListener,
        OnItemLongClickListener {

    public static final String ARG_ITEM_ID = "student_list";

    Activity activity;
    ListView studentListView;
    ArrayList<Student> students;

    StudentListAdapter studentListAdapter;
    StudentDAO studentDAO;

    private GetStudentTask task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        studentDAO = new StudentDAO(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_list, container,
                false);
        findViewsById(view);

        task = new GetStudentTask(activity);
        task.execute((Void) null);

        studentListView.setOnItemClickListener(this);
        studentListView.setOnItemLongClickListener(this);

        return view;
    }

    private void findViewsById(View view) {
        studentListView = (ListView) view.findViewById(R.id.list_student);
    }

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.app_name);
//        getActivity().getActionBar().setTitle(R.string.app_name);
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> list, View arg1, int position,
                            long arg3) {
        Student student = (Student) list.getItemAtPosition(position);

        if (student != null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable("selectedStudent", student);
            CustomStudentDialogFragment customStudentDialogFragment = new CustomStudentDialogFragment();
            customStudentDialogFragment.setArguments(arguments);
            customStudentDialogFragment.show(getFragmentManager(),
                    CustomStudentDialogFragment.ARG_ITEM_ID);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                   int position, long arg3) {
        Student student = (Student) parent.getItemAtPosition(position);

        // Use AsyncTask to delete from database
        studentDAO.delete(student);
        studentListAdapter.remove(student);
        return true;
    }

    public class GetStudentTask extends AsyncTask<Void, Void, ArrayList<Student>> {

        private final WeakReference<Activity> activityWeakRef;

        public GetStudentTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected ArrayList<Student> doInBackground(Void... arg0){
            return studentDAO.getStudents();
        }

        @Override
        protected void onPostExecute(ArrayList<Student> studentList) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                students = studentList;
                if (studentList != null) {
                    if (studentList.size() != 0) {
                        studentListAdapter = new StudentListAdapter(activity,
                                studentList);
                        studentListView.setAdapter(studentListAdapter);
                    } else {
                        Toast.makeText(activity, "No Student Records",
                                Toast.LENGTH_LONG).show();
                    }
                }

            }
        }
    }


    public void updateView() {
        task = new GetStudentTask(activity);
        task.execute((Void) null);
    }
}