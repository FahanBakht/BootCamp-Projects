package com.example.farhan.campussystem.Company;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.farhan.campussystem.Adapters.StudentJobAppliedAdapter;
import com.example.farhan.campussystem.Models.AddJob;
import com.example.farhan.campussystem.Models.Student;
import com.example.farhan.campussystem.Others.NonScrollListView;
import com.example.farhan.campussystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CompanyJobDetail extends AppCompatActivity {

    private NonScrollListView mListViewStudentApplied;
    private StudentJobAppliedAdapter mAdapter;
    private ArrayList<Student> studentArrayList;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private String jobUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_job_detail);

        TextView company_JobDetail_Role = findViewById(R.id.company_JobDetail_Role);
        TextView company_JobDetail_Type = findViewById(R.id.company_JobDetail_Type);
        TextView company_JobDetail_Salary = findViewById(R.id.company_JobDetail_Salary);
        TextView company_JobDetail_Experience = findViewById(R.id.company_JobDetail_Experience);
        TextView company_JobDetail_CompanyType = findViewById(R.id.company_JobDetail_CompanyType);
        TextView company_JobDetail_CompanySize = findViewById(R.id.company_JobDetail_CompanySize);
        TextView company_JobDetail_Industry = findViewById(R.id.company_JobDetail_Industry);
        TextView company_JobDetail_JobDescription = findViewById(R.id.company_JobDetail_JobDescription);
        mListViewStudentApplied = findViewById(R.id.mListViewStudentApplied);

        Intent intent = getIntent();
        AddJob addJob = intent.getParcelableExtra("obj");

        if (addJob != null) {
            jobUid = addJob.getJobUid();
            company_JobDetail_Role.setText(addJob.getAddJobRole());
            company_JobDetail_Type.setText(addJob.getAddJobType());
            company_JobDetail_Salary.setText(addJob.getAddJobSalary());
            company_JobDetail_Experience.setText(addJob.getAddJobExperience());
            company_JobDetail_CompanyType.setText(addJob.getAddJobCompanyType());
            company_JobDetail_CompanySize.setText(addJob.getAddJobCompanySize());
            company_JobDetail_Industry.setText(addJob.getAddJobIndustry());
            company_JobDetail_JobDescription.setText(addJob.getAddJobDescription());
        }

        studentArrayList = new ArrayList<>();
        mAdapter = new StudentJobAppliedAdapter(CompanyJobDetail.this, studentArrayList);
        mListViewStudentApplied.setAdapter(mAdapter);
        mListViewStudentApplied.setEmptyView(findViewById(R.id.student_Applied_Empty_View));

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Company").child("StudentAppliedForJob").child(mAuth.getCurrentUser().getUid()).child(jobUid);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    Student student = dataSnapshot.getValue(Student.class);
                    studentArrayList.add(0, student);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
