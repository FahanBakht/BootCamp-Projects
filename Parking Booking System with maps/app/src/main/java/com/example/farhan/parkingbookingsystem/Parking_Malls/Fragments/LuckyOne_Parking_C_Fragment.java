package com.example.farhan.parkingbookingsystem.Parking_Malls.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.farhan.parkingbookingsystem.Adapters.ParkingAreaAdapter;
import com.example.farhan.parkingbookingsystem.Models.CheckAvailability;
import com.example.farhan.parkingbookingsystem.Models.Slot;
import com.example.farhan.parkingbookingsystem.R;
import com.example.farhan.parkingbookingsystem.Users.BookingActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class LuckyOne_Parking_C_Fragment extends Fragment {
    private ArrayList<Slot> slotArrayList;
    private DatabaseReference mReference;
    private ParkingAreaAdapter mAdapter;

    private static final String TAG = "TAG";
    static TextView showTime;
    static TextView showDate;
    static Spinner mSpinner;
    private static int endTimeInt;
    static String endTime;
    private ArrayList<CheckAvailability> checkAvailabilityArrayList;
    static long currentUserPickTimeCheck;

    public LuckyOne_Parking_C_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_lucky_one__parking__c_, container, false);

        mReference = FirebaseDatabase.getInstance().getReference().child("BookingSystem").child("Malls").child("MallsParking").child("LuckyOneParkingAreas").child("ParkingAreaC").child("Slots");
        final RecyclerView recyclerView = rootView.findViewById(R.id.rcView_LuckyOne_Parking_C);
        slotArrayList = new ArrayList<>();
        checkAvailabilityArrayList = new ArrayList<>();

        DatabaseReference checkSlotReference = FirebaseDatabase.getInstance().getReference().child("BookingSystem").child("Malls").child("MallsParking").child("LuckyOneParkingAreas").child("ParkingAreaC").child("CheckSlot");
        checkSlotReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    CheckAvailability checkAvailabilityObj = dataSnapshot.getValue(CheckAvailability.class);
                    checkAvailabilityArrayList.add(checkAvailabilityObj);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        showTime = rootView.findViewById(R.id.txt_showTime_Frag_LOP_C);
        ImageButton timePicker = rootView.findViewById(R.id.btn_Pick_Time_Frag_LOP_C);
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!showDate.getText().toString().isEmpty()) {
                    showDate.setError(null);
                    DialogFragment newFragment = new TimePickerFragment();
                    newFragment.show(getActivity().getFragmentManager(), "timePicker");
                } else {
                    showDate.requestFocus();
                    showDate.setText(null);
                    showDate.setError("Please Select Date First");
                }
            }
        });

        mSpinner = rootView.findViewById(R.id.spinner_Duration_Time_Frag_LOP_C);
        Integer[] items = new Integer[]{1, 2, 3, 4, 5};
        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        mSpinner.setAdapter(spinnerAdapter);

        showDate = rootView.findViewById(R.id.txt_showDate_Frag_LOP_C);
        ImageButton datePicker = rootView.findViewById(R.id.btn_Pick_Date_Frag_LOP_C);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate.setError(null);
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getFragmentManager(), "datePicker");
            }
        });

        Button btnShowSlots = rootView.findViewById(R.id.btn_Show_Slots_Frag_LOP_C);
        btnShowSlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String userPickTime = showTime.getText().toString();
                final String userPickDate = showDate.getText().toString();
                slotArrayList.clear();

                if (!userPickDate.isEmpty()) {
                    if (!userPickTime.isEmpty()) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, 23);
                        c.set(Calendar.MINUTE, 59);
                        c.set(Calendar.SECOND, 59);
                        long todayInMillis = c.getTimeInMillis();
                        if (currentUserPickTimeCheck < todayInMillis) {

                            showDate.setError(null);
                            mAdapter = new ParkingAreaAdapter(getContext(), slotArrayList, checkAvailabilityArrayList, userPickTime, userPickDate, endTime, new ParkingAreaAdapter.customButtonListener() {
                                @Override
                                public void onButtonClickListener(int position) {
                                    Intent intent = new Intent(getContext(), BookingActivity.class);
                                    Slot slotPosition = slotArrayList.get(position);
                                    intent.putExtra("obj", slotPosition);
                                    intent.putExtra("userPickTime", userPickTime);
                                    intent.putExtra("userPickDate", userPickDate);
                                    intent.putExtra("userPickedHours", mSpinner.getSelectedItem().toString());
                                    intent.putExtra("endDuration", endTime);
                                    startActivity(intent);
                                }
                            });
                            RecyclerView.LayoutManager recyclerViewLayoutManager = new GridLayoutManager(getContext(), 2);
                            recyclerView.setLayoutManager(recyclerViewLayoutManager);
                            recyclerView.setAdapter(mAdapter);

                            mReference.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    if (dataSnapshot != null) {
                                        Slot slotObj = dataSnapshot.getValue(Slot.class);
                                        slotArrayList.add(slotObj);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        } else {
                            showDate.requestFocus();
                            showDate.setError("Hour's cant be selected for tomorrow date");
                        }

                    } else {
                        showDate.requestFocus();
                        showDate.setError("Please select Time");
                    }
                } else {
                    showDate.requestFocus();
                    showDate.setText(null);
                    showDate.setError("Please select Date");
                }
            }
        });


        return rootView;
    }

    private static void endTime(final int hourOfDay, final int minute) {

        endTimeInt = Integer.parseInt(mSpinner.getSelectedItem().toString());

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                showDate.setError(null);
                endTimeInt = Integer.parseInt(mSpinner.getSelectedItem().toString());
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm a");
                Calendar getEndTime = Calendar.getInstance();
                getEndTime.set(Calendar.HOUR_OF_DAY, hourOfDay + endTimeInt);
                getEndTime.set(Calendar.MINUTE, minute);
                currentUserPickTimeCheck = getEndTime.getTimeInMillis();
                endTime = timeFormat.format(getEndTime.getTime());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm a");
        Calendar getEndTime = Calendar.getInstance();
        getEndTime.set(Calendar.HOUR_OF_DAY, hourOfDay + endTimeInt);
        getEndTime.set(Calendar.MINUTE, minute);
        currentUserPickTimeCheck = getEndTime.getTimeInMillis();
        endTime = timeFormat.format(getEndTime.getTime());

    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user

            Calendar getUserTime = Calendar.getInstance();
            getUserTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            getUserTime.set(Calendar.MINUTE, minute);
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm a");

            String userCurrentPickDate = showDate.getText().toString();
            String userCurrentPickTime = timeFormat.format(getUserTime.getTime());
            String userPickTimeAndDate = userCurrentPickDate + " " + userCurrentPickTime;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm aaa");

            try {
                Date mDateTimeAndDateCurrent = sdf.parse(userPickTimeAndDate);
                long userCurrentTimeAndDate = mDateTimeAndDateCurrent.getTime();

                if (userCurrentTimeAndDate > Calendar.getInstance().getTimeInMillis()) {
                    endTime(hourOfDay, minute);
                    showTime.setText(timeFormat.format(getUserTime.getTime()));
                } else {
                    showDate.requestFocus();
                    showDate.setError("Please select future date for the giving time");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            //pickerDialog.getDatePicker().setMaxDate((System.currentTimeMillis() - 1000)+(1000*60*60*24*2));
            pickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return pickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            String dateYouChoose = day + "-" + (month + 1) + "-" + year;
            showDate.setText(dateYouChoose);

        }
    }

}
