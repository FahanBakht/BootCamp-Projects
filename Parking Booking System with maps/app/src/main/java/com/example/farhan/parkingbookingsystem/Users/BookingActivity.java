package com.example.farhan.parkingbookingsystem.Users;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farhan.parkingbookingsystem.Models.Booking;
import com.example.farhan.parkingbookingsystem.Models.CheckAvailability;
import com.example.farhan.parkingbookingsystem.Models.Slot;
import com.example.farhan.parkingbookingsystem.Models.User;
import com.example.farhan.parkingbookingsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookingActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private FirebaseAuth mAuth;
    private DatabaseReference mReference;
    private ProgressDialog progressDialog;
    EditText parkingBookingUserName;
    EditText parkingBookingUserEmail;
    EditText parkingBookingUserCnicNumber;
    EditText parkingBookingUserCarName;
    EditText parkingBookingUserCarLicenseNumber;
    User userObj;
    Button btnBookParking;
    TextView showTime;
    TextView showDate;
    TextView showDuration;

    String userPickTime;
    String userPickDate;
    String userPickedHours;
    String endDuration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        progressDialog = new ProgressDialog(BookingActivity.this);
        progressDialog.setMessage("Logging please wait");
        progressDialog.show();
        parkingBookingUserName = findViewById(R.id.txt_Booking_UserName);
        parkingBookingUserEmail = findViewById(R.id.txt_Booking_Email);
        parkingBookingUserCnicNumber = findViewById(R.id.txt_Booking_CnicNumber);
        parkingBookingUserCarName = findViewById(R.id.txt_Booking_CarName);
        parkingBookingUserCarLicenseNumber = findViewById(R.id.txt_Booking_CarLicenseNumber);
        showTime = findViewById(R.id.txt_Booking_showTime);
        showDate = findViewById(R.id.txt_Booking_showDate);
        showDuration = findViewById(R.id.txt_Booking_Duration);

        Intent intent = getIntent();
        userPickTime = intent.getStringExtra("userPickTime");
        userPickDate = intent.getStringExtra("userPickDate");
        userPickedHours = intent.getStringExtra("userPickedHours");
        endDuration = intent.getStringExtra("endDuration");

        showTime.setText(userPickTime);
        showDate.setText(userPickDate);
        showDuration.setText(userPickedHours);

        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference().child("BookingSystem").child("RegisteredAuth").child("Users");

        mReference.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    userObj = dataSnapshot.getValue(User.class);
                    parkingBookingUserName.setText(userObj.getUserName());
                    parkingBookingUserEmail.setText(userObj.getUserEmail());
                    parkingBookingUserCnicNumber.setText(userObj.getUserCnicNumber());
                    parkingBookingUserCarName.setText(userObj.getUserCarName());
                    parkingBookingUserCarLicenseNumber.setText(userObj.getUserCarLicensePlateNumber());
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btnBookParking = findViewById(R.id.btn_Book_Parking);
        btnBookParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookParkingNow();
            }
        });
    }

    private void bookParkingNow() {
        Intent intent = getIntent();
        Slot slotObj = intent.getParcelableExtra("obj");
        if (slotObj != null && userObj != null) {

            String slotKey = slotObj.getId();
            String slotName = slotObj.getSlotName();
            String mallKey = slotObj.getMallKey();
            String parkingAreaKey = slotObj.getParkingAreaKey();

            String userName = userObj.getUserName();
            String userEmail = userObj.getUserEmail();
            String userCnicNumber = userObj.getUserCnicNumber();
            String userCarName = userObj.getUserCarName();
            String userCarLicenseNumber = userObj.getUserCarLicensePlateNumber();
            String userUid = userObj.getUserUid();

            String userPickTime = showTime.getText().toString();
            String userPickDate = showDate.getText().toString();

            if (!userPickTime.isEmpty()) {
                if (!userPickDate.isEmpty()) {

                    progressDialog.show();

                    DatabaseReference mReferenceBooking = FirebaseDatabase.getInstance().getReference().child("BookingSystem").child("Malls").child("Booking");
                    String bookingKey = mReferenceBooking.push().getKey();
                    Booking bookingObj = new Booking(slotKey, slotName, mallKey, parkingAreaKey, userName, userEmail, userCnicNumber, userCarName, userCarLicenseNumber, userUid, userPickTime, userPickDate, bookingKey, userPickedHours);
                    mReferenceBooking.child(bookingKey).setValue(bookingObj);

                    DatabaseReference mReferenceSlot = FirebaseDatabase.getInstance().getReference().child("BookingSystem").child("Malls").child("MallsParking").child(mallKey).child(parkingAreaKey).child("CheckSlot");
                    String checkAvailabilityKey = mReferenceBooking.push().getKey();
                    CheckAvailability checkAvailabilityObj = new CheckAvailability(userPickTime,endDuration,userPickDate,checkAvailabilityKey,slotKey);
                    mReferenceSlot.child(checkAvailabilityKey).setValue(checkAvailabilityObj);

                    btnBookParking.setEnabled(false);
                    Toast.makeText(this, "You have Booked " + slotName + " Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(BookingActivity.this, UserActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    progressDialog.dismiss();

                } else {
                    showDate.setTextColor(Color.RED);
                    showDate.setText("Please select Date");

                }
            } else {
                showTime.setTextColor(Color.RED);
                showTime.setText("Please select Time");

            }
        }
    }

}
