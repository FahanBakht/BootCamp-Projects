package com.example.farhan.campussystem.Student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farhan.campussystem.Models.AddJob;
import com.example.farhan.campussystem.Models.Student;
import com.example.farhan.campussystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentJobApply extends AppCompatActivity {

    private DatabaseReference reference;
    private DatabaseReference getStdReference;
    private FirebaseAuth mAuth;

    private String companyUid;
    private String jobUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_job_apply);

        TextView student_Job_Apply_Role = findViewById(R.id.student_Job_Apply_Role);
        TextView student_Job_Apply_Type = findViewById(R.id.student_Job_Apply_Type);
        TextView student_Job_Apply_Salary = findViewById(R.id.student_Job_Apply_Salary);
        TextView student_Job_Apply_Experience = findViewById(R.id.student_Job_Apply_Experience);
        TextView student_Job_Apply_CompanyType = findViewById(R.id.student_Job_Apply_CompanyType);
        TextView student_Job_Apply_CompanySize = findViewById(R.id.student_Job_Apply_CompanySize);
        TextView student_Job_Apply_Industry = findViewById(R.id.student_Job_Apply_Industry);
        TextView student_Job_Apply_JobDescription = findViewById(R.id.student_Job_Apply_JobDescription);
        Button btnJobApply = findViewById(R.id.btn_student_Job_Apply);

        Intent intent = getIntent();
        AddJob addJob = intent.getParcelableExtra("obj");

        if (addJob != null) {
            setTitle(addJob.getAddJobRole());
            companyUid = addJob.getCompanyUid();
            jobUid = addJob.getJobUid();
            student_Job_Apply_Role.setText(addJob.getAddJobRole());
            student_Job_Apply_Type.setText(addJob.getAddJobType());
            student_Job_Apply_Salary.setText(addJob.getAddJobSalary()+"$");
            student_Job_Apply_Experience.setText(addJob.getAddJobExperience());
            student_Job_Apply_CompanyType.setText(addJob.getAddJobCompanyType());
            student_Job_Apply_CompanySize.setText(addJob.getAddJobCompanySize());
            student_Job_Apply_Industry.setText(addJob.getAddJobIndustry());
            student_Job_Apply_JobDescription.setText(addJob.getAddJobDescription());
        }

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Company").child("StudentAppliedForJob").child(companyUid).child(jobUid);

        getStdReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(mAuth.getCurrentUser().getUid());

        btnJobApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStudentData();
                Toast.makeText(StudentJobApply.this, "Applied to job successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getStudentData(){

        getStdReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null){
                    Student student = dataSnapshot.getValue(Student.class);

                    reference.child(mAuth.getCurrentUser().getUid()).setValue(student);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
