package com.example.farhan.campussystem.Company;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.farhan.campussystem.Models.AddJob;
import com.example.farhan.campussystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CompanyAddJob extends AppCompatActivity {
    private EditText addJobRole;
    private EditText addJobType;
    private EditText addJobExperience;
    private EditText addJobSalary;
    private EditText addJobCompanySize;
    private EditText addJobCompanyType;
    private EditText addJobIndustry;
    private EditText addJobDescription;

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jobs);

        progressDialog = new ProgressDialog(CompanyAddJob.this);
        progressDialog.setMessage("Loading please wait");

        addJobRole = findViewById(R.id.et_AddJob_Role);
        addJobType = findViewById(R.id.et_AddJob_Type);
        addJobExperience = findViewById(R.id.et_AddJob_Experience_Level);
        addJobSalary = findViewById(R.id.et_AddJob_Salary);
        addJobCompanySize = findViewById(R.id.et_AddJob_Company_Size);
        addJobCompanyType = findViewById(R.id.et_AddJob_Company_Type);
        addJobIndustry = findViewById(R.id.et_AddJob_Industry);
        addJobDescription = findViewById(R.id.et_AddJob_Job_Description);
        Button btnAddJobPost = findViewById(R.id.btn_AddJob_Post_Job);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Company").child("CompanyJobs").child(mAuth.getCurrentUser().getUid());

        btnAddJobPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postJob();
            }
        });
    }

    private void postJob() {
        String jobRole = addJobRole.getText().toString();
        String jobType = addJobType.getText().toString();
        String jobExp = addJobExperience.getText().toString();
        String jobSalary = addJobSalary.getText().toString();
        String jobCompSize = addJobCompanySize.getText().toString();
        String jobCompType = addJobCompanyType.getText().toString();
        String jobIndustry = addJobIndustry.getText().toString();
        String jobDec = addJobDescription.getText().toString();

        if (!jobRole.isEmpty()) {
            if (!jobType.isEmpty()) {
                if (!jobExp.isEmpty()) {
                    if (!jobSalary.isEmpty()) {
                        if (!jobCompSize.isEmpty()) {
                            if (!jobCompType.isEmpty()) {
                                if (!jobIndustry.isEmpty()) {
                                    if (!jobDec.isEmpty()) {

                                        progressDialog.show();
                                        String key = reference.push().getKey();
                                        AddJob addJob = new AddJob(jobRole, jobType, jobExp, jobSalary, jobCompSize, jobCompType, jobIndustry, jobDec, mAuth.getCurrentUser().getUid(), key);
                                        reference.child(key).setValue(addJob);

                                        DatabaseReference adminReference = FirebaseDatabase.getInstance().getReference().child("Admin").child("Jobs");
                                        adminReference.child(key).setValue(addJob);

                                        addJobRole.setText("");
                                        addJobType.setText("");
                                        addJobExperience.setText("");
                                        addJobSalary.setText("");
                                        addJobCompanySize.setText("");
                                        addJobCompanyType.setText("");
                                        addJobIndustry.setText("");
                                        addJobDescription.setText("");
                                        progressDialog.dismiss();

                                    } else {
                                        addJobDescription.setError("Please insert job description");
                                    }
                                } else {
                                    addJobIndustry.setError("Please insert industry");
                                }
                            } else {
                                addJobCompanyType.setError("Please insert company type");
                            }
                        } else {
                            addJobCompanySize.setError("Please insert company size");
                        }
                    } else {
                        addJobSalary.setError("Please insert salary");
                    }
                } else {
                    addJobExperience.setError("Please insert experience required");
                }
            } else {
                addJobType.setError("Please insert job type");
            }
        } else {
            addJobRole.setError("Please insert job role");
        }
    }
}
