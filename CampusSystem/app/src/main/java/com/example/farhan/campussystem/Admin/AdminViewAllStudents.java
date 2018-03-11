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

import com.example.farhan.campussystem.Adapters.ViewAllStudentsAdapter;
import com.example.farhan.campussystem.Models.Student;
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
public class AdminViewAllStudents extends Fragment {

    private DatabaseReference reference;
    private DatabaseReference stdDeleteReference;
    private ProgressBar progressBar;
    private ListView mListView;
    ViewAllStudentsAdapter mAdapter;
    private ArrayList<Student> studentArrayList;

    public AdminViewAllStudents() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_admin_view_all_students, container, false);

        progressBar = mainView.findViewById(R.id.progressBar_AdminViewAllStudent);
        mListView = mainView.findViewById(R.id.adminViewAll_Student_List);
        studentArrayList = new ArrayList<>();

        mAdapter = new ViewAllStudentsAdapter(getContext(), studentArrayList);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(mainView.findViewById(R.id.empty_View_AdminViewAll_Student_ListView));


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Student student = studentArrayList.get(position);
                showDeleteDialog(student);
            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child("Students");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    Student student = dataSnapshot.getValue(Student.class);
                    if (student != null) {
                        studentArrayList.add(0, student);
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
                if (dataSnapshot != null) {
                    Student student = dataSnapshot.getValue(Student.class);
                for (int i = 0; i < studentArrayList.size(); i++) {
                    // Find the item to remove and then remove it by index
                    if (studentArrayList.get(i).getStudentUid().equals(student.getStudentUid())) {
                        studentArrayList.remove(i);
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

    private void showDeleteDialog(final Student student) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you want to delete this student");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        stdDeleteReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(student.getStudentUid());
                        stdDeleteReference.removeValue();

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
