package com.example.farhan.parkingbookingsystem.Parking_Malls;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.farhan.parkingbookingsystem.Adapters.MallsAdapter;
import com.example.farhan.parkingbookingsystem.Models.Malls;
import com.example.farhan.parkingbookingsystem.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MallsActivity extends AppCompatActivity {
    ArrayList<Malls> mallsArrayList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_malls);

        progressDialog = new ProgressDialog(MallsActivity.this);
        progressDialog.setMessage("Loading please wait");
        progressDialog.show();

        ListView mListView = findViewById(R.id.root_ListView_Malls);
        mallsArrayList = new ArrayList<>();
        final MallsAdapter mAdapter = new MallsAdapter(this, mallsArrayList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(MallsActivity.this, HyperStarMallActivity.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(MallsActivity.this, LuckyOneMallActivity.class);
                    startActivity(intent);
                }else if(position == 2) {
                    Intent intent = new Intent(MallsActivity.this, OceanMallActivity.class);
                    startActivity(intent);
                }
            }
        });

        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference().child("BookingSystem").child("Malls").child("MallsData");
        mReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    Malls malls = dataSnapshot.getValue(Malls.class);
                    mallsArrayList.add(malls);
                    mAdapter.notifyDataSetChanged();
                    progressDialog.cancel();
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
    }
}
