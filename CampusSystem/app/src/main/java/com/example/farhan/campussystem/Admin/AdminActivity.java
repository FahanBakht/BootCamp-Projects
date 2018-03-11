package com.example.farhan.campussystem.Admin;

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

import com.example.farhan.campussystem.Adapters.AdminPagerAdapter;
import com.example.farhan.campussystem.LoginActivity;
import com.example.farhan.campussystem.Models.Admin;
import com.example.farhan.campussystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        if (isNetworkAvailable()){

            mAuth = FirebaseAuth.getInstance();
            reference = FirebaseDatabase.getInstance().getReference().child("Users").child("Admins").child(mAuth.getCurrentUser().getUid());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        Admin admin = dataSnapshot.getValue(Admin.class);
                        if (admin != null) {
                            setTitle(admin.getAdminName());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            viewPager = findViewById(R.id.mAdminViewPager);
            ArrayList<Fragment> fragments = new ArrayList<>();

            fragments.add(new AdminViewAllStudents());
            fragments.add(new AdminViewAllCompanies());
            fragments.add(new AdminViewCompanyJobs());

            AdminPagerAdapter mAdapter = new AdminPagerAdapter(getSupportFragmentManager(), fragments);
            viewPager.setAdapter(mAdapter);

        }else {
            viewPager.setVisibility(View.GONE);
            TextView networkError = findViewById(R.id.admin_Network_Error);
            networkError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.admin_LogOut:
                mAuth.signOut();
                startActivity(new Intent(AdminActivity.this, LoginActivity.class));
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
