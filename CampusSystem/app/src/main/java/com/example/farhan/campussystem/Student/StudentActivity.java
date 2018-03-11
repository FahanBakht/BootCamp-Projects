package com.example.farhan.campussystem.Student;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.farhan.campussystem.Adapters.StudentViewCompaniesAdapter;
import com.example.farhan.campussystem.LoginActivity;
import com.example.farhan.campussystem.Models.CompanyProfile;
import com.example.farhan.campussystem.Models.Student;
import com.example.farhan.campussystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ListView mListView;
    private StudentViewCompaniesAdapter mAdapter;
    private ArrayList<CompanyProfile> companyProfileArrayList;

    private DatabaseReference reference;
    private DatabaseReference referenceStdName;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity);

        if (isNetworkAvailable()) {

            progressBar = findViewById(R.id.progressBar_View_All_Companies);
            mAuth = FirebaseAuth.getInstance();
            reference = FirebaseDatabase.getInstance().getReference().child("Company").child("CompanyProfile");
            referenceStdName = FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(mAuth.getCurrentUser().getUid());

            companyProfileArrayList = new ArrayList<>();
            mListView = findViewById(R.id.list_View_All_Companies);
            mAdapter = new StudentViewCompaniesAdapter(StudentActivity.this, companyProfileArrayList);
            mListView.setAdapter(mAdapter);
            mListView.setEmptyView(findViewById(R.id.list_View_All_Companies_EmptyView));

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(StudentActivity.this, CompanyAllDetails.class);
                    CompanyProfile companyProfile = companyProfileArrayList.get(position);
                    intent.putExtra("obj", companyProfile);
                    startActivity(intent);
                }
            });

            referenceStdName.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        Student student = dataSnapshot.getValue(Student.class);
                        if (student != null) {
                            setTitle(student.getStudentName());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

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

        } else {
            TextView networkError = findViewById(R.id.student_Network_Error);
            networkError.setVisibility(View.VISIBLE);

            progressBar.setVisibility(View.GONE);
            mListView.setVisibility(View.GONE);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.student_LogOut:
                mAuth.signOut();
                startActivity(new Intent(StudentActivity.this, LoginActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
