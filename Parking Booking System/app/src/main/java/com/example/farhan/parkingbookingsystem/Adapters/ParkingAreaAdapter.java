package com.example.farhan.parkingbookingsystem.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.farhan.parkingbookingsystem.Models.CheckAvailability;
import com.example.farhan.parkingbookingsystem.Models.Slot;
import com.example.farhan.parkingbookingsystem.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ParkingAreaAdapter extends RecyclerView.Adapter<ParkingAreaAdapter.RvCustomViewHolder> {

    private static final String TAG = "TAG";
    private ArrayList<Slot> slotArrayList;
    private Context context;
    customButtonListener listener;

    private ArrayList<CheckAvailability> checkAvailabilityArrayList;
    private String userPickTime;
    private String userPickDate;
    private String endTime;


    public class RvCustomViewHolder extends RecyclerView.ViewHolder {
        Button btn_Slot;

        public RvCustomViewHolder(View itemView) {
            super(itemView);
            btn_Slot = itemView.findViewById(R.id.txt_slot);
        }
    }

    // To Cr8 Custom OnClickListener on RCView
    public interface customButtonListener {
        void onButtonClickListener(int position);
    }

    public ParkingAreaAdapter(Context context, ArrayList<Slot> slotArrayList, ArrayList<CheckAvailability> checkAvailabilityArrayList, String userPickTime, String userPickDate, String endTime, customButtonListener listener) {
        this.context = context;
        this.slotArrayList = slotArrayList;
        this.checkAvailabilityArrayList = checkAvailabilityArrayList;
        this.userPickTime = userPickTime;
        this.userPickDate = userPickDate;
        this.endTime = endTime;
        this.listener = listener;
    }

    @Override
    public RvCustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_rc_view_item_view, parent, false);
        return new RvCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RvCustomViewHolder holder, final int position) {
        Slot slotObj = slotArrayList.get(position);
        holder.btn_Slot.setText(slotObj.getSlotName());

        int colorGreen = Color.parseColor("#ccffcc");
        int colorRed = Color.parseColor("#ff7f7f");

        Log.e(TAG, "onBindViewHolder: endTime--->" + endTime);
        Log.e("TAG", "onBindViewHolder: checkAvailabilityArrayList.size() -->" + checkAvailabilityArrayList.size());
        Log.e("TAG", "onBindViewHolder: slotArrayList.size() -->" + slotArrayList.size());
        Log.e(TAG, "onBindViewHolder: position ---> " + position);

        if (checkAvailabilityArrayList.size() != 0) {
            for (int i = 0; i < checkAvailabilityArrayList.size(); i++) {
                Log.e(TAG, "onBindViewHolder: slotOBJ.getId()---> " + slotObj.getId());
                Log.e(TAG, "onBindViewHolder: checkAvailOBJ.getSlotKey()---> " + checkAvailabilityArrayList.get(i).getSlotKey());

                if (slotObj.getId().equals(checkAvailabilityArrayList.get(i).getSlotKey())) {
                    if (checkDateAndTime(userPickDate, userPickTime, endTime, checkAvailabilityArrayList.get(i).getBookedDate(), checkAvailabilityArrayList.get(i).getBookedStartTime(), checkAvailabilityArrayList.get(i).getBookedDndTime())) {
                        holder.btn_Slot.setBackgroundColor(colorGreen);
                        holder.btn_Slot.setEnabled(true);
                    } else {
                        holder.btn_Slot.setBackgroundColor(colorRed);
                        holder.btn_Slot.setEnabled(false);
                        holder.btn_Slot.setClickable(false);
                        break;
                    }
                } else {
                    // no slot to check SlotAvailable = True
                    holder.btn_Slot.setBackgroundColor(colorGreen);
                    holder.btn_Slot.setEnabled(true);
                }
            }

        } else {
            holder.btn_Slot.setBackgroundColor(colorGreen);
            holder.btn_Slot.setEnabled(true);

        }


        holder.btn_Slot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onButtonClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return slotArrayList.size();
    }

    private boolean checkDateAndTime(String dateCurrentUser, String startTimeCurrentUser, String endTimeCurrentEndUser, String checkDate, String checkStartTime, String checkEndTime) {

        boolean check = false;

        // Current USER Select
        String givenDateStringStartCurrentUser = dateCurrentUser + " " + startTimeCurrentUser;
        String givenDateStringEndCurrentUser = dateCurrentUser + " " + endTimeCurrentEndUser;

        // To Check
        String toCheckStartTimeAndDate = checkDate + " " + checkStartTime;
        String toCheckEndTimeAndDate = checkDate + " " + checkEndTime;

        // RootFormat
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm aaa");

        try {
            // Convert giving Current Time and Date String into RootFormat
            Date mDateStartCurrent = sdf.parse(givenDateStringStartCurrentUser);
            Log.e("TAG", "checkDateAndTime: mDateStartCurrent ----> " + mDateStartCurrent);
            Date mDateEndCurrent = sdf.parse(givenDateStringEndCurrentUser);
            Log.e("TAG", "checkDateAndTime: mDateEndCurrent ----> " + mDateEndCurrent);

            // Convert giving ToCheck Time and Date String into RootFormat
            Date mDateStartToCheck = sdf.parse(toCheckStartTimeAndDate);
            Log.e("TAG", "checkDateAndTime: mDateStartToCheck ----> " + mDateStartToCheck);
            Date mDateEndToCheck = sdf.parse(toCheckEndTimeAndDate);
            Log.e("TAG", "checkDateAndTime: mDateEndToCheck ----> " + mDateEndToCheck);

            // Convert Current Start Time and End Time into Long
            long userCurrentStartTimeStamp = mDateStartCurrent.getTime();
            long userCurrentEndTimeStamp = mDateEndCurrent.getTime();

            // Convert toCheck Start Time and End Time into Long
            long toCheckTimeStampStart = mDateStartToCheck.getTime();
            long toCheckTimeStampEnd = mDateEndToCheck.getTime();


            // after bad
            // before phaylay

            if (!(userCurrentStartTimeStamp < toCheckTimeStampStart && userCurrentEndTimeStamp > toCheckTimeStampStart || userCurrentStartTimeStamp < toCheckTimeStampEnd && userCurrentEndTimeStamp > toCheckTimeStampEnd)) {
                Log.e("TAG", "checkDateAndTime: True OMG ");
                check = true;
            } else {
                Log.e("TAG", "checkDateAndTime: False DAMN ");
                check = false;
            }

        } catch (ParseException e) {
            Log.e("TAG", "checkDateAndTime: Exception " + e.getMessage());
        }

        return check;
    }

}
