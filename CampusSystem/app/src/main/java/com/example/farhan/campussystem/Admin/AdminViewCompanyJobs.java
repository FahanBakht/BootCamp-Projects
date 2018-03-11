package com.example.farhan.campussystem.Admin;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.farhan.campussystem.Adapters.JobAdapter;
import com.example.farhan.campussystem.Models.AddJob;
import com.example.farhan.campussystem.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminViewCompanyJobs extends Fragment {

    private static final String TAG = "AdminViewCompanyJobsTag";
    private ListView mListView;
    private ProgressBar progressBar;
    private ArrayList<AddJob> addJobArrayList;
    private JobAdapter mAdapter;
    private DatabaseReference reference;

    public AdminViewCompanyJobs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_admin_view_company_jobs, container, false);
        progressBar = mainView.findViewById(R.id.progressBar_adminView_All_Companies_Jobs);

        mListView = mainView.findViewById(R.id.adminView_All_Companies_Jobs_ListView);
        addJobArrayList = new ArrayList<>();

        mAdapter = new JobAdapter(getContext(), addJobArrayList);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(mainView.findViewById(R.id.adminView_All_Companies_Jobs_ListView_EmptyView));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AddJob addJob = addJobArrayList.get(position);
                showDeleteDialog(addJob);
            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("Admin").child("Jobs");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    AddJob addJob = dataSnapshot.getValue(AddJob.class);
                    addJobArrayList.add(0, addJob);
                    progressBar.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    AddJob addJob = dataSnapshot.getValue(AddJob.class);
                    for (int i = 0; i < addJobArrayList.size(); i++) {
                        // Find the item to remove and then remove it by index
                        if (addJobArrayList.get(i).getJobUid().equals(addJob.getJobUid())) {
                            addJobArrayList.remove(i);
                            break;
                        }
                    }

                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (mAdapter != null) {
            progressBar.setVisibility(View.GONE);
        }

        return mainView;
    }

    private void showDeleteDialog(final AddJob addJob) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you want to delete this company job");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        progressBar.setVisibility(View.VISIBLE);
                        DatabaseReference adminCompanyJobDeleteReference = FirebaseDatabase.getInstance().getReference().child("Admin").child("Jobs").child(addJob.getJobUid());
                        adminCompanyJobDeleteReference.removeValue();

                        DatabaseReference companyJobDeleteReference = FirebaseDatabase.getInstance().getReference().child("Company").child("CompanyJobs").child(addJob.getCompanyUid()).child(addJob.getJobUid());
                        companyJobDeleteReference.removeValue();

                        DatabaseReference studentsAppliedJobDeleteReference = FirebaseDatabase.getInstance().getReference().child("Company").child("StudentAppliedForJob").child(addJob.getCompanyUid()).child(addJob.getJobUid());
                        studentsAppliedJobDeleteReference.removeValue();
                        progressBar.setVisibility(View.GONE);
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder.create();
        alert11.show();
    }
}
