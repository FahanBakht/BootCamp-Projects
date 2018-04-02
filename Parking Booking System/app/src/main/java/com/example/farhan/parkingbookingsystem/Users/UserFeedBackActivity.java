package com.example.farhan.parkingbookingsystem.Users;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.farhan.parkingbookingsystem.Adapters.FeedBackAdapter;
import com.example.farhan.parkingbookingsystem.Models.FeedBack;
import com.example.farhan.parkingbookingsystem.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UserFeedBackActivity extends AppCompatActivity {

    private ArrayList<FeedBack> feedBackArrayList;
    private FeedBackAdapter mAdapter;
    private DatabaseReference mReference;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed_back);

        Intent intent = getIntent();
        name = intent.getStringExtra("userName");
        mReference = FirebaseDatabase.getInstance().getReference().child("BookingSystem").child("FeedBack");

        final EditText etFeedBackTxt = findViewById(R.id.etFeedBackTxt);
        ImageButton btnSendFeedBack = findViewById(R.id.btnSendFeedBack);
        btnSendFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get Current Date
                long date = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd");
                String dateString = sdf.format(date);

                String commentText = etFeedBackTxt.getText().toString();

                // Send Comment Object to FireBase Database
                if (!commentText.isEmpty()) {
                    String feedBackKey = mReference.push().getKey();
                    FeedBack feedBack = new FeedBack(commentText, feedBackKey, name, dateString, "noReply");
                    mReference.child(feedBackKey).setValue(feedBack);
                    etFeedBackTxt.setText("");
                } else {
                    if (commentText.isEmpty()) {
                        etFeedBackTxt.setError("Please Type Some feedback");
                    }
                }
            }
        });

        feedBackArrayList = new ArrayList<>();
        ListView mListView = findViewById(R.id.feedBackListView);
        mAdapter = new FeedBackAdapter(UserFeedBackActivity.this, feedBackArrayList);
        mListView.setAdapter(mAdapter);

        mReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    FeedBack feedBack = dataSnapshot.getValue(FeedBack.class);
                    feedBackArrayList.add(0, feedBack);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    FeedBack feedBack = dataSnapshot.getValue(FeedBack.class);
                    for (int i = 0; i < feedBackArrayList.size(); i++) {
                        // Find the item to remove and then remove it by index
                        if (feedBackArrayList.get(i).getKey().equals(feedBack.getKey())) {
                            feedBackArrayList.set(i,feedBack);
                            break;
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
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
