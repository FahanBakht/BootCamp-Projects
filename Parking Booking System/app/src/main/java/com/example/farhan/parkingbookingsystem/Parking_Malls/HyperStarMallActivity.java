package com.example.farhan.parkingbookingsystem.Parking_Malls;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.farhan.parkingbookingsystem.Adapters.ParkingAreaPagerAdapter;
import com.example.farhan.parkingbookingsystem.Parking_Malls.Fragments.HyperStar_Parking_A_Fragment;
import com.example.farhan.parkingbookingsystem.Parking_Malls.Fragments.HyperStar_Parking_B_Fragment;
import com.example.farhan.parkingbookingsystem.Parking_Malls.Fragments.HyperStar_Parking_C_Fragment;
import com.example.farhan.parkingbookingsystem.R;

import java.util.ArrayList;

public class HyperStarMallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hyper_star_mall);

        ViewPager viewPager = findViewById(R.id.mViewPager_HyperStar);
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(new HyperStar_Parking_A_Fragment());
        fragments.add(new HyperStar_Parking_B_Fragment());
        fragments.add(new HyperStar_Parking_C_Fragment());

        ParkingAreaPagerAdapter mAdapter = new ParkingAreaPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(mAdapter);
    }
}
