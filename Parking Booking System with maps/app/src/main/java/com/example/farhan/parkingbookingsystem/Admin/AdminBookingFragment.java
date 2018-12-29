package com.example.farhan.parkingbookingsystem.Admin;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farhan.parkingbookingsystem.Adapters.BookingAdapter;
import com.example.farhan.parkingbookingsystem.Models.Booking;
import com.example.farhan.parkingbookingsystem.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminBookingFragment extends android.app.Fragment {
    private DatabaseReference mReference;
    private ProgressBar progressBar;
    private ListView mListView;
    private BookingAdapter mAdapter;
    private ArrayList<Booking> bookingArrayList;

    public AdminBookingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_admin_booking, container, false);

        progressBar = rootView.findViewById(R.id.admin_Booking_ProgressBar);
        mListView = rootView.findViewById(R.id.admin_Booking_ListView);
        bookingArrayList = new ArrayList<>();

        mAdapter = new BookingAdapter(getActivity(), bookingArrayList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                showBookingDetailsDialog(bookingArrayList.get(i));
            }
        });

        mReference = FirebaseDatabase.getInstance().getReference().child("BookingSystem").child("Malls").child("Booking");
        mReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    Booking booking = dataSnapshot.getValue(Booking.class);
                    bookingArrayList.add(booking);
                    mAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
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
                Log.e("TAG", "onCancelled: Runs");
            }
        });

        if (mListView.getAdapter().getCount() == 0) {
            progressBar.setVisibility(View.GONE);
            mListView.setEmptyView(rootView.findViewById(R.id.empty_View_Admin_Booking_ListView));
        }

        return rootView;
    }

    public void showBookingDetailsDialog(final Booking bookingObj) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View promptsView = inflater.inflate(R.layout.admin_booking_detail_dialog, null);

        TextView txt_Admin_Booking_dialog_UserName = promptsView.findViewById(R.id.txt_Admin_Booking_dialog_UserName);
        TextView txt_Admin_Booking_dialog_UserEmail = promptsView.findViewById(R.id.txt_Admin_Booking_dialog_UserEmail);
        TextView txt_Admin_Booking_dialog_UserCnicNumber = promptsView.findViewById(R.id.txt_Admin_Booking_dialog_UserCnicNumber);
        TextView txt_Admin_Booking_dialog_UserCarName = promptsView.findViewById(R.id.txt_Admin_Booking_dialog_UserCarName);
        TextView txt_Admin_Booking_dialog_UserCarLicenseNumber = promptsView.findViewById(R.id.txt_Admin_Booking_dialog_UserCarLicenseNumber);
        TextView txt_Admin_Booking_dialog_UserParkingMall = promptsView.findViewById(R.id.txt_Admin_Booking_dialog_UserParkingMall);
        TextView txt_Admin_Booking_dialog_UserParkingArea = promptsView.findViewById(R.id.txt_Admin_Booking_dialog_UserParkingArea);
        TextView txt_Admin_Booking_dialog_UserParkingSlot = promptsView.findViewById(R.id.txt_Admin_Booking_dialog_UserParkingSlot);
        TextView txt_Admin_Booking_dialog_UserParkingTime = promptsView.findViewById(R.id.txt_Admin_Booking_dialog_UserParkingTime);
        TextView txt_Admin_Booking_dialog_UserSelectedHours = promptsView.findViewById(R.id.txt_Admin_Booking_dialog_UserSelectedHours);
        TextView txt_Admin_Booking_dialog_UserParkingDate = promptsView.findViewById(R.id.txt_Admin_Booking_dialog_UserParkingDate);
        Button btn_Admin_Booking_dialog_CancelBooking = promptsView.findViewById(R.id.btn_Admin_Booking_dialog_CancelBooking);

        if (bookingObj != null) {
            txt_Admin_Booking_dialog_UserName.setText(bookingObj.getUserName());
            txt_Admin_Booking_dialog_UserEmail.setText(bookingObj.getUserEmail());
            txt_Admin_Booking_dialog_UserCnicNumber.setText(bookingObj.getUserCnicNumber());
            txt_Admin_Booking_dialog_UserCarName.setText(bookingObj.getUserCarName());
            txt_Admin_Booking_dialog_UserCarLicenseNumber.setText(bookingObj.getUserCarLicenseNumber());
            txt_Admin_Booking_dialog_UserParkingMall.setText(bookingObj.getMallKey());
            txt_Admin_Booking_dialog_UserParkingArea.setText(bookingObj.getParkingAreaKey());
            txt_Admin_Booking_dialog_UserParkingSlot.setText(bookingObj.getSlotName());
            txt_Admin_Booking_dialog_UserParkingTime.setText(bookingObj.getUserPickTime());
            txt_Admin_Booking_dialog_UserSelectedHours.setText(bookingObj.getSelectedHours());
            txt_Admin_Booking_dialog_UserParkingDate.setText(bookingObj.getUserPickDate());
        }

        builder.setView(promptsView);
        builder.setTitle("Booking Details");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
        btn_Admin_Booking_dialog_CancelBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mReferenceBooking = FirebaseDatabase.getInstance().getReference().child("BookingSystem").child("Malls").child("Booking").child(bookingObj.getBookingKey());
                mReferenceBooking.removeValue();

                DatabaseReference mrReferenceSlot = FirebaseDatabase.getInstance().getReference().child("BookingSystem").child("Malls").child("MallsParking").child(bookingObj.getMallKey()).child(bookingObj.getParkingAreaKey()).child(bookingObj.getSlotKey());
                mrReferenceSlot.child("checkAvailability").setValue(true);
                Toast.makeText(getActivity(), "Parking Booking Canceled Successfully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

}
