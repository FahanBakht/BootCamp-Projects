package com.example.farhan.parkingbookingsystem.Users;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.farhan.parkingbookingsystem.Adapters.BookingAdapter;
import com.example.farhan.parkingbookingsystem.Login_and_SignUp.LoginActivity;
import com.example.farhan.parkingbookingsystem.Models.Booking;
import com.example.farhan.parkingbookingsystem.Models.User;
import com.example.farhan.parkingbookingsystem.Parking_Malls.MallsActivity;
import com.example.farhan.parkingbookingsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private DatabaseReference mReferenceUser;
    private ArrayList<Booking> bookingArrayList;
    private ListView mListView;
    private BookingAdapter mAdapter;
    private DatabaseReference mReferenceBooking;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        progressDialog = new ProgressDialog(UserActivity.this);
        progressDialog.setMessage("Loading please wait");
        progressDialog.show();

        mListView = findViewById(R.id.root_ListView_User);
        bookingArrayList = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mReferenceUser = FirebaseDatabase.getInstance().getReference().child("BookingSystem").child("RegisteredAuth").child("Users");
        mReferenceUser.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        setTitle(user.getUserName());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAdapter = new BookingAdapter(UserActivity.this, bookingArrayList);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(findViewById(R.id.root_ListView_User_EmptyError));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(UserActivity.this, BookingDetailActivity.class);
                Booking bookingObj = bookingArrayList.get(position);
                intent.putExtra("obj", bookingObj);
                startActivity(intent);
            }
        });

        mReferenceBooking = FirebaseDatabase.getInstance().getReference().child("BookingSystem").child("Malls").child("Booking");
        mReferenceBooking.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    if (dataSnapshot.child("userUid").getValue().equals(mAuth.getCurrentUser().getUid())) {
                        Booking booking = dataSnapshot.getValue(Booking.class);
                        bookingArrayList.add(booking);
                        mAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    Booking booking = dataSnapshot.getValue(Booking.class);
                    for (int i = 0; i < bookingArrayList.size(); i++) {
                        // Find the item to remove and then remove it by index
                        if (bookingArrayList.get(i).getBookingKey().equals(booking.getBookingKey())) {
                            bookingArrayList.remove(i);
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

        Button btn_BookNow = findViewById(R.id.btn_User_BookNow);
        btn_BookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, MallsActivity.class);
                startActivity(intent);
            }
        });
        progressDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.user_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.user_Activity_Menu_FeedBack:
                Intent intent = new Intent(UserActivity.this, UserFeedBackActivity.class);
                intent.putExtra("userName", user.getUserName());
                startActivity(intent);
                return true;

            case R.id.user_Activity_Menu_LogOut:
                mAuth.signOut();
                startActivity(new Intent(UserActivity.this, LoginActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
