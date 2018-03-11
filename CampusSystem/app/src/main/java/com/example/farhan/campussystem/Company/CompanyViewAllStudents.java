package com.example.farhan.campussystem.Company;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class CompanyViewAllStudents extends AppCompatActivity {

    private DatabaseReference reference;
    private ProgressBar progressBar;
    private ListView mListView;
    ViewAllStudentsAdapter mAdapter;
    private ArrayList<Student> studentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_students);

        progressBar = findViewById(R.id.progressBar_AllStudents);
        mListView = findViewById(R.id.view_All_Student_List);
        studentArrayList = new ArrayList<>();

        mAdapter = new ViewAllStudentsAdapter(CompanyViewAllStudents.this, studentArrayList);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(findViewById(R.id.empty_View_For_Student_ListView));

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
    }
}
