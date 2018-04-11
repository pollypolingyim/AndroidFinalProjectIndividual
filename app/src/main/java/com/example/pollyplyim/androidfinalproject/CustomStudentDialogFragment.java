package com.example.pollyplyim.androidfinalproject;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CustomStudentDialogFragment extends DialogFragment {
    private EditText studentNameEtxt;
    private EditText studentGpaEtxt;
    private LinearLayout submitLayout;
    private Student student;

    StudentDAO studentDAO;

    public static final String ARG_ITEM_ID = "student_dialog_fragment";


    public interface StudentDialogFragmentListener {
        void onFinishDialog();
    }


    public CustomStudentDialogFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        studentDAO = new StudentDAO(getActivity());

        Bundle bundle = this.getArguments();
        student = bundle.getParcelable("selectedStudent");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View customDialogView = inflater.inflate(R.layout.fragment_add_student,
                null);
        builder.setView(customDialogView);

        studentNameEtxt = (EditText) customDialogView.findViewById(R.id.etxt_name);
        studentGpaEtxt = (EditText) customDialogView
                .findViewById(R.id.etxt_gpa);
        submitLayout = (LinearLayout) customDialogView
                .findViewById(R.id.layout_submit);
        submitLayout.setVisibility(View.GONE);

        setValue();

        builder.setTitle(R.string.update_student);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.update,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        student.setName(studentNameEtxt.getText().toString());
                        student.setGpa(Double.parseDouble(studentGpaEtxt
                                .getText().toString()));
                        long result = studentDAO.update(student);
                        if (result > 0) {
                            MainActivity activity = (MainActivity) getActivity();
                            activity.onFinishDialog();
                        } else {
                            Toast.makeText(getActivity(),
                                    "Unable to update student",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

    private void setValue() {
        if (student != null) {
            studentNameEtxt.setText(student.getName());
            studentGpaEtxt.setText(student.getGpa() + "");
        }
    }
}
