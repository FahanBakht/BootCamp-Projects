package com.example.farhan.parkingbookingsystem.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.farhan.parkingbookingsystem.Models.Booking;
import com.example.farhan.parkingbookingsystem.R;

import java.util.ArrayList;

/**
 * Created by Farhan on 3/21/2018.
 */

public class BookingAdapter extends ArrayAdapter<Booking> {

    private ArrayList<Booking> bookingArrayList;

    public BookingAdapter(@NonNull Context context, ArrayList<Booking> bookingArrayList) {
        super(context, 0, bookingArrayList);
        this.bookingArrayList = bookingArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_booking_item_view, parent, false);
        }

        TextView txt_Booking_Detail_UserName = convertView.findViewById(R.id.txt_Booking_Detail_UserName);
        TextView txt_Booking_Detail_MallName = convertView.findViewById(R.id.txt_Booking_Detail_MallName);
        TextView txt_Booking_Detail_ParkingArea = convertView.findViewById(R.id.txt_Booking_Detail_ParkingArea);
        TextView txt_Booking_Detail_SlotName = convertView.findViewById(R.id.txt_Booking_Detail_SlotName);
        TextView txt_Booking_Detail_CarName = convertView.findViewById(R.id.txt_Booking_Detail_CarName);
        TextView txt_Booking_Detail_CarLicenseNumber = convertView.findViewById(R.id.txt_Booking_Detail_CarLicenseNumber);

        Booking booking = bookingArrayList.get(position);
        txt_Booking_Detail_UserName.setText(booking.getUserName());
        txt_Booking_Detail_MallName.setText(booking.getMallKey());
        txt_Booking_Detail_ParkingArea.setText(booking.getParkingAreaKey());
        txt_Booking_Detail_SlotName.setText(booking.getSlotName());
        txt_Booking_Detail_CarName.setText(booking.getUserCarName());
        txt_Booking_Detail_CarLicenseNumber.setText(booking.getUserCarLicenseNumber());

        return convertView;
    }
}
