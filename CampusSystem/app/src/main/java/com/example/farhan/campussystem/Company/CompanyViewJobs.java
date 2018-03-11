package com.example.farhan.campussystem.Company;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.farhan.campussystem.Adapters.JobAdapter;
import com.example.farhan.campussystem.Models.AddJob;
import com.example.farhan.campussystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyViewJobs extends Fragment {
    private JobAdapter mJobAdapter;
    private ArrayList<AddJob> addJobArrayList;
    private ListView mListView;
    private FloatingActionButton fab_Add_Job;
    private ProgressBar progressBar;

    private DatabaseReference reference;
    private FirebaseAuth mAuth;

    public CompanyViewJobs() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_company_jobs, container, false);

        progressBar = mainView.findViewById(R.id.progress_Company_JobPosts);
        mListView = mainView.findViewById(R.id.mListViewJobAvailable);
        fab_Add_Job = mainView.findViewById(R.id.fab_Add_Job);

        fab_Add_Job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CompanyAddJob.class));
            }
        });

        addJobArrayList = new ArrayList<>();
        mJobAdapter = new JobAdapter(getContext(), addJobArrayList);
        mListView.setAdapter(mJobAdapter);
        mListView.setEmptyView(mainView.findViewById(R.id.job_Available_Empty_View));



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getContext(), CompanyJobDetail.class);
                AddJob addJob = addJobArrayList.get(position);
                intent.putExtra("obj", addJob);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Company").child("CompanyJobs").child(mAuth.getCurrentUser().getUid());
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    AddJob addJob = dataSnapshot.getValue(AddJob.class);
                    if (addJob != null) {
                        addJobArrayList.add(0, addJob);
                        mJobAdapter.notifyDataSetChanged();
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
        progressBar.setVisibility(View.GONE);
        return mainView;
    }

}
