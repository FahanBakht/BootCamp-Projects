package com.example.farhan.parkingbookingsystem.Admin;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.farhan.parkingbookingsystem.Login_and_SignUp.LoginActivity;
import com.example.farhan.parkingbookingsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class AdminActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Drawer result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mAuth = FirebaseAuth.getInstance();

        Toolbar mToolbar = findViewById(R.id.admin_ToolBar);
        mToolbar.setBackground(getResources().getDrawable(R.color.toolBarBg));
        setSupportActionBar(mToolbar);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(getResources().getDrawable(R.color.toolBarBg)).withProfileImagesClickable(false)
                .withSelectionListEnabledForSingleProfile(false)
                .addProfiles(
                        new ProfileDrawerItem().withName("Parking Booking").withEmail("book your parking before your arrival").withIcon(getResources().getDrawable(R.drawable.icon_parking))
                )
                .withTranslucentStatusBar(true)
                .build();

        //if you want to update the items at a later time it is recommended to keep it in a variable
        final PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withIcon(R.drawable.ic_bookpark).withName("Booking");
        final PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withIcon(R.drawable.ic_person).withName("Users");
        final PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withIcon(R.drawable.ic_feedback).withName("Feed Back");
        final SecondaryDrawerItem item4 = new SecondaryDrawerItem().withIdentifier(5).withIcon(R.drawable.ic_logout).withName("Logout");


        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item1,
                        item2,
                        item3,
                        new DividerDrawerItem(),
                        item4
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        selectItem(position);
                        return true;
                    }
                })
                .build();
        result.setSelection(1, true);

    }

    private void selectItem(int position) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        switch (position) {
            case 1: {
                setTitle("Parking Bookings");
                Fragment bookingFragment = new AdminBookingFragment();
                transaction.replace(R.id.fragment_Root_Container, bookingFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                result.closeDrawer();
                break;
            }
            case 2: {
                setTitle("Users");
                Fragment usersFragment = new AdminUsersFragment();
                transaction.replace(R.id.fragment_Root_Container, usersFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                result.closeDrawer();
                break;
            }
            case 3: {
                setTitle("Feed Backs");
                Fragment feedBackFragment = new AdminFeedBackFragment();
                transaction.replace(R.id.fragment_Root_Container, feedBackFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                result.closeDrawer();
                break;
            }
            case 5: {
                mAuth.signOut();
                result.closeDrawer();
                startActivity(new Intent(AdminActivity.this, LoginActivity.class));
                finish();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
