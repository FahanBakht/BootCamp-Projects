package com.example.farhan.parkingbookingsystem.Admin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.farhan.parkingbookingsystem.Adapters.AdminUsersAdapter;
import com.example.farhan.parkingbookingsystem.Models.User;
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
public class AdminUsersFragment extends android.app.Fragment {

    private DatabaseReference reference;
    private ProgressBar progressBar;
    private ListView mListView;
    private AdminUsersAdapter mAdapter;
    private ArrayList<User> userArrayList;

    public AdminUsersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_admin_users, container, false);

        progressBar = rootView.findViewById(R.id.admin_Users_ProgressBar);
        mListView = rootView.findViewById(R.id.admin_Users_ListView);
        userArrayList = new ArrayList<>();

        mAdapter = new AdminUsersAdapter(getActivity(), userArrayList);
        mListView.setAdapter(mAdapter);

        reference = FirebaseDatabase.getInstance().getReference().child("BookingSystem").child("RegisteredAuth").child("Users");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        userArrayList.add(0, user);
                        mAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
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

        if (mListView.getAdapter().getCount() == 0) {
            progressBar.setVisibility(View.GONE);
            mListView.setEmptyView(rootView.findViewById(R.id.empty_View_Admin_Users_ListView));
        }

        return rootView;
    }

}
