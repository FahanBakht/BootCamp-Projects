package com.example.farhan.campussystem.Company;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.farhan.campussystem.Adapters.CompanyPagerAdapter;
import com.example.farhan.campussystem.LoginActivity;
import com.example.farhan.campussystem.Models.CompanyProfile;
import com.example.farhan.campussystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CompanyActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        if (isNetworkAvailable()) {

            mAuth = FirebaseAuth.getInstance();
            reference = FirebaseDatabase.getInstance().getReference().child("Company").child("CompanyProfile").child(mAuth.getCurrentUser().getUid());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        CompanyProfile companyProfile = dataSnapshot.getValue(CompanyProfile.class);
                        if (companyProfile != null) {
                            setTitle(companyProfile.getCompanyName());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            viewPager = findViewById(R.id.mCompanyViewPager);
            ArrayList<Fragment> fragments = new ArrayList<>();

            fragments.add(new CompanyMainView());
            fragments.add(new CompanyViewJobs());

            CompanyPagerAdapter mAdapter = new CompanyPagerAdapter(getSupportFragmentManager(), fragments);
            viewPager.setAdapter(mAdapter);
        } else {
            viewPager.setVisibility(View.GONE);
            TextView networkError = findViewById(R.id.company_Network_Error);
            networkError.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_company, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.setUp_Company_Profile:
                startActivity(new Intent(CompanyActivity.this, CreateCompanyProfile.class));
                return true;

            case R.id.view_Student_List:
                startActivity(new Intent(CompanyActivity.this, CompanyViewAllStudents.class));
                return true;

            case R.id.LogOut:
                mAuth.signOut();
                startActivity(new Intent(CompanyActivity.this, LoginActivity.class));
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
