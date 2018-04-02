package com.example.farhan.parkingbookingsystem.Users;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farhan.parkingbookingsystem.Models.Booking;
import com.example.farhan.parkingbookingsystem.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookingDetailActivity extends AppCompatActivity {

    private DatabaseReference mReferenceBooking;
    private DatabaseReference mrReferenceSlot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);

        TextView txt_Booking_Detailed_UserName = findViewById(R.id.txt_Booking_Detailed_UserName);
        TextView txt_Booking_Detailed_UserEmail = findViewById(R.id.txt_Booking_Detailed_UserEmail);
        TextView txt_Booking_Detailed_UserCnicNumber = findViewById(R.id.txt_Booking_Detailed_UserCnicNumber);
        TextView txt_Booking_Detailed_UserCarName = findViewById(R.id.txt_Booking_Detailed_UserCarName);
        TextView txt_Booking_Detailed_UserCarLicenseNumber = findViewById(R.id.txt_Booking_Detailed_UserCarLicenseNumber);
        TextView txt_Booking_Detailed_UserParkingMall = findViewById(R.id.txt_Booking_Detailed_UserParkingMall);
        TextView txt_Booking_Detailed_UserParkingArea = findViewById(R.id.txt_Booking_Detailed_UserParkingArea);
        TextView txt_Booking_Detailed_UserParkingSlot = findViewById(R.id.txt_Booking_Detailed_UserParkingSlot);
        TextView txt_Booking_Detailed_UserParkingTime = findViewById(R.id.txt_Booking_Detailed_UserParkingTime);
        TextView txt_Booking_Detailed_UserSelectedHours = findViewById(R.id.txt_Booking_Detailed_UserSelectedHours);
        TextView txt_Booking_Detailed_UserParkingDate = findViewById(R.id.txt_Booking_Detailed_UserParkingDate);

        Button btn_Booking_Detail_CancelBooking = findViewById(R.id.btn_Booking_Detail_CancelBooking);

        Intent intent = getIntent();
        final Booking bookingObj = intent.getParcelableExtra("obj");
        if (bookingObj != null) {
            txt_Booking_Detailed_UserName.setText(bookingObj.getUserName());
            txt_Booking_Detailed_UserEmail.setText(bookingObj.getUserEmail());
            txt_Booking_Detailed_UserCnicNumber.setText(bookingObj.getUserCnicNumber());
            txt_Booking_Detailed_UserCarName.setText(bookingObj.getUserCarName());
            txt_Booking_Detailed_UserCarLicenseNumber.setText(bookingObj.getUserCarLicenseNumber());
            txt_Booking_Detailed_UserParkingMall.setText(bookingObj.getMallKey());
            txt_Booking_Detailed_UserParkingArea.setText(bookingObj.getParkingAreaKey());
            txt_Booking_Detailed_UserParkingSlot.setText(bookingObj.getSlotName());
            txt_Booking_Detailed_UserParkingTime.setText(bookingObj.getUserPickTime());
            txt_Booking_Detailed_UserSelectedHours.setText(bookingObj.getSelectedHours());
            txt_Booking_Detailed_UserParkingDate.setText(bookingObj.getUserPickDate());
        }

        btn_Booking_Detail_CancelBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mReferenceBooking = FirebaseDatabase.getInstance().getReference().child("BookingSystem").child("Malls").child("Booking").child(bookingObj.getBookingKey());
                mReferenceBooking.removeValue();

                mrReferenceSlot = FirebaseDatabase.getInstance().getReference().child("BookingSystem").child("Malls").child("MallsParking").child(bookingObj.getMallKey()).child(bookingObj.getParkingAreaKey()).child(bookingObj.getSlotKey());
                mrReferenceSlot.child("checkAvailability").setValue(true);
                Intent i = new Intent(BookingDetailActivity.this, UserActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast.makeText(BookingDetailActivity.this, "Parking Booking Canceled Successfully", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });

    }
}
