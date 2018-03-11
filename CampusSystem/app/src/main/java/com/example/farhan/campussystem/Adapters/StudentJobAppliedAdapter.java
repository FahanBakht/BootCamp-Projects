package com.example.farhan.campussystem.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.farhan.campussystem.Models.Student;
import com.example.farhan.campussystem.R;

import java.util.ArrayList;

/**
 * Created by Farhan on 3/9/2018.
 */

public class StudentJobAppliedAdapter extends ArrayAdapter<Student> {

    private ArrayList<Student> studentArrayList;

    public StudentJobAppliedAdapter(@NonNull Context context, ArrayList<Student> studentArrayList) {
        super(context, 0, studentArrayList);
        this.studentArrayList = studentArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.company_job_detail_student_applied, parent, false);
        }

        TextView txtStdName = convertView.findViewById(R.id.txt_Student_Applied_Job_Name);
        TextView txtStdEmail = convertView.findViewById(R.id.txt_Student_Applied_Job_Email);
        TextView txtStdAge = convertView.findViewById(R.id.txt_Student_Applied_Job_Qualification);
        TextView txtStdQualification = convertView.findViewById(R.id.txt_Student_Applied_Job_Age);

        Student student = studentArrayList.get(position);
        txtStdName.setText(student.getStudentName());
        txtStdEmail.setText(student.getStudentEmail());
        txtStdAge.setText(student.getStudentAge());
        txtStdQualification.setText(student.getStudentQualification());

        return convertView;
    }
}
