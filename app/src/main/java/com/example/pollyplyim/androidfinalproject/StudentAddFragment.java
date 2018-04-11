package com.example.pollyplyim.androidfinalproject;

import java.lang.ref.WeakReference;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StudentAddFragment extends Fragment implements OnClickListener {
    private EditText studentNameEtxt;
    private EditText studentGpaEtxt;
    private Button addButton;
    private Button resetButton;

    Student student = null;
    private StudentDAO studentDAO;
    private AddStudentTask task;

    public static final String ARG_ITEM_ID = "student_add_fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studentDAO = new StudentDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_student, container,
                false);

        findViewsById(rootView);
        setListeners();
        return rootView;
    }

    private void setListeners() {
        addButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
    }

    protected void resetAllFields() {
        studentNameEtxt.setText("");
        studentGpaEtxt.setText("");
    }

    private void setStudent() {
        student = new Student();
        student.setName(studentNameEtxt.getText().toString());
        student.setGpa(Double.parseDouble(studentGpaEtxt.getText()
                .toString()));
    }

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.add_student);
        getActivity().getActionBar().setTitle(R.string.add_student);
        super.onResume();
    }



    private void findViewsById(View rootView) {
        studentNameEtxt = (EditText) rootView.findViewById(R.id.etxt_name);
        studentGpaEtxt = (EditText) rootView.findViewById(R.id.etxt_gpa);
        addButton = (Button) rootView.findViewById(R.id.button_add);
        resetButton = (Button) rootView.findViewById(R.id.button_reset);
    }

    @Override
    public void onClick(View view) {
        if (view == addButton) {
            setStudent();

            task = new AddStudentTask(getActivity());
            task.execute((Void) null);
        } else if (view == resetButton) {
            resetAllFields();
        }
    }

    public class AddStudentTask extends AsyncTask<Void, Void, Long> {

        private final WeakReference<Activity> activityWeakRef;

        public AddStudentTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected Long doInBackground(Void... arg0) {
            long result = studentDAO.save(student);
            return result;
        }

        @Override
        protected void onPostExecute(Long result) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                if (result != -1)
                    Toast.makeText(activityWeakRef.get(), "Student Saved",
                            Toast.LENGTH_LONG).show();
            }
        }
    }
}