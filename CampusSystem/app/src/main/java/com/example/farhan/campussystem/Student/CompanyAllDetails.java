package com.example.farhan.campussystem.Student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.farhan.campussystem.Adapters.JobAdapter;
import com.example.farhan.campussystem.Models.AddJob;
import com.example.farhan.campussystem.Models.CompanyProfile;
import com.example.farhan.campussystem.Others.NonScrollListView;
import com.example.farhan.campussystem.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CompanyAllDetails extends AppCompatActivity {

    private ProgressBar progressBar;
    private NonScrollListView mListView;
    private JobAdapter mAdapter;
    private ArrayList<AddJob> addJobArrayList;

    private DatabaseReference reference;

    private String companyJobKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);

        ImageView company_Detail_Image = findViewById(R.id.company_Detail_Image);
        TextView company_Detail_AboutUs = findViewById(R.id.company_Detail_AboutUs);
        TextView company_Detail_Contact = findViewById(R.id.company_Detail_Contact);
        TextView company_Detail_Email = findViewById(R.id.company_Detail_Email);
        TextView company_Detail_location = findViewById(R.id.company_Detail_location);

        progressBar = findViewById(R.id.progressBar_Company_Detail);
        mListView = findViewById(R.id.mListViewJobAvailable_CompanyDetails);

        Intent intent = getIntent();
        final CompanyProfile companyProfile = intent.getParcelableExtra("obj");
        if (companyProfile != null) {
            setTitle(companyProfile.getCompanyName());
            Glide.with(CompanyAllDetails.this).load(companyProfile.getCompanyProfileImage()).into(company_Detail_Image);
            company_Detail_AboutUs.setText(companyProfile.getCompanyAboutUs());
            company_Detail_Contact.setText(companyProfile.getCompanyContact());
            company_Detail_Email.setText(companyProfile.getCompanyEmail());
            company_Detail_location.setText(companyProfile.getCompanyLocation());
            companyJobKey = companyProfile.getCompanyProfileUID();
            progressBar.setVisibility(View.GONE);
        }

        addJobArrayList = new ArrayList<>();
        mAdapter = new JobAdapter(CompanyAllDetails.this, addJobArrayList);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(findViewById(R.id.job_Available_Empty_View_CompanyDetails));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(CompanyAllDetails.this, StudentJobApply.class);
                AddJob addJob = addJobArrayList.get(position);
                intent.putExtra("obj", addJob);
                startActivity(intent);

            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("Company").child("CompanyJobs").child(companyJobKey);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    AddJob addJob = dataSnapshot.getValue(AddJob.class);
                    if (addJob != null) {
                        addJobArrayList.add(addJob);
                        mAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
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
