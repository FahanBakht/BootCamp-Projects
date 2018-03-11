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

import com.example.farhan.campussystem.Adapters.StudentViewCompaniesAdapter;
import com.example.farhan.campussystem.Models.CompanyProfile;
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
public class AdminViewAllCompanies extends Fragment {
    private ProgressBar progressBar;
    private ListView mListView;
    private StudentViewCompaniesAdapter mAdapter;
    private ArrayList<CompanyProfile> companyProfileArrayList;
    private DatabaseReference reference;


    public AdminViewAllCompanies() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_admin_view_all_compaies, container, false);

        progressBar = mainView.findViewById(R.id.progressBar_Admin_View_All_Companies);
        mListView = mainView.findViewById(R.id.list_Admin_View_All_Companies);
        reference = FirebaseDatabase.getInstance().getReference().child("Company").child("CompanyProfile");

        companyProfileArrayList = new ArrayList<>();
        mAdapter = new StudentViewCompaniesAdapter(getContext(), companyProfileArrayList);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(mainView.findViewById(R.id.list_Admin_View_All_Companies_EmptyView));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                CompanyProfile companyProfile = companyProfileArrayList.get(position);
                showDeleteDialog(companyProfile);
            }
        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    CompanyProfile companyProfile = dataSnapshot.getValue(CompanyProfile.class);
                    companyProfileArrayList.add(companyProfile);
                    mAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    CompanyProfile companyProfile = dataSnapshot.getValue(CompanyProfile.class);
                    for (int i = 0; i < companyProfileArrayList.size(); i++) {
                        // Find the item to remove and then remove it by index
                        if (companyProfileArrayList.get(i).getCompanyProfileUID().equals(companyProfile.getCompanyProfileUID())) {
                            companyProfileArrayList.remove(i);
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

    private void showDeleteDialog(final CompanyProfile companyProfile) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you want to delete this company");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        progressBar.setVisibility(View.VISIBLE);
                        DatabaseReference companyProfileDeleteReference = FirebaseDatabase.getInstance().getReference().child("Company").child("CompanyProfile").child(companyProfile.getCompanyProfileUID());
                        companyProfileDeleteReference.removeValue();

                        DatabaseReference companyJobsDeleteReference = FirebaseDatabase.getInstance().getReference().child("Company").child("CompanyJobs").child(companyProfile.getCompanyProfileUID());
                        companyJobsDeleteReference.removeValue();

                        DatabaseReference companyStudentAppliedJobsDeleteReference = FirebaseDatabase.getInstance().getReference().child("Company").child("StudentAppliedForJob").child(companyProfile.getCompanyProfileUID());
                        companyStudentAppliedJobsDeleteReference.removeValue();

                        DatabaseReference companySignUpDeleteReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Companies").child(companyProfile.getCompanyProfileUID());
                        companySignUpDeleteReference.removeValue();
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
