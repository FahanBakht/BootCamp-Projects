package com.example.farhan.parkingbookingsystem.Admin;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.farhan.parkingbookingsystem.Adapters.FeedBackAdapter;
import com.example.farhan.parkingbookingsystem.Models.FeedBack;
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
public class AdminFeedBackFragment extends android.app.Fragment {

    private ArrayList<FeedBack> feedBackArrayList;
    private ProgressBar progressBar;
    private FeedBackAdapter mAdapter;
    private DatabaseReference mReference;

    public AdminFeedBackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_admin_feed_back, container, false);

        progressBar = rootView.findViewById(R.id.admin_FeedBack_ProgressBar);
        feedBackArrayList = new ArrayList<>();
        ListView mListView = rootView.findViewById(R.id.admin_FeedBack_ListView);
        mAdapter = new FeedBackAdapter(getActivity(), feedBackArrayList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showFeedBackReplyDialog(feedBackArrayList.get(i));
            }
        });

        mReference = FirebaseDatabase.getInstance().getReference().child("BookingSystem").child("FeedBack");
        mReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    FeedBack feedBack = dataSnapshot.getValue(FeedBack.class);
                    feedBackArrayList.add(0, feedBack);
                    mAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
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

        if (mListView.getAdapter().getCount() == 0) {
            progressBar.setVisibility(View.GONE);
            mListView.setEmptyView(rootView.findViewById(R.id.empty_View_Admin_FeedBack_ListView));
        }

        return rootView;
    }

    public void showFeedBackReplyDialog(final FeedBack feedBack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View promptsView = inflater.inflate(R.layout.admin_feedback_reply_dialog, null);

        final EditText adminReplyTxt = promptsView.findViewById(R.id.et_Admin_FeedBack_Reply_Txt);
        ImageButton btnAdminSend = promptsView.findViewById(R.id.btn_Admin_FeedBack_Send_Reply);

        builder.setView(promptsView);
        builder.setTitle("FeedBack Reply");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
        btnAdminSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyText = adminReplyTxt.getText().toString();
                if (!replyText.isEmpty()) {
                    DatabaseReference referenceFeedBack = FirebaseDatabase.getInstance().getReference().child("BookingSystem").child("FeedBack");
                    referenceFeedBack.child(feedBack.getKey()).child("adminReply").setValue(replyText);
                    dialog.dismiss();
                } else {
                    adminReplyTxt.setError("Please write some feedback");
                }
            }
        });

    }

}
