package com.example.farhan.campussystem.SignUp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.farhan.campussystem.Adapters.SignUpPagerAdapter;
import com.example.farhan.campussystem.R;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ViewPager viewPager = findViewById(R.id.mViewPager);
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(new Student_SignUp_Fragment());
        fragments.add(new Company_SignUp_Fragment());
        fragments.add(new Admin_SignUp_Fragment());

        SignUpPagerAdapter mAdapter = new SignUpPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(mAdapter);

    }
}
