package com.example.farhan.campussystem.Company;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.farhan.campussystem.Models.CompanyProfile;
import com.example.farhan.campussystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyMainView extends Fragment {
    private TextView errorCreateProfile;
    private ScrollView rootView;
    private ProgressBar progressBar;

    private ImageView companyImage;
    private TextView companyAboutUs;
    private TextView companyContact;
    private TextView companyEmail;
    private TextView companyLocation;

    private DatabaseReference reference;
    private FirebaseAuth mAuth;

    private boolean view = false;

    public CompanyMainView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_company_main_view, container, false);

        progressBar = mainView.findViewById(R.id.progress_Company_MainView);
        errorCreateProfile = mainView.findViewById(R.id.error_Create_Profile);
        rootView = mainView.findViewById(R.id.main_Root_ScrollView);

        companyImage = mainView.findViewById(R.id.company_Image);
        companyAboutUs = mainView.findViewById(R.id.company_AboutUs);
        companyContact = mainView.findViewById(R.id.company_Contact);
        companyEmail = mainView.findViewById(R.id.company_Email);
        companyLocation = mainView.findViewById(R.id.company_location);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Company").child("CompanyProfile").child(mAuth.getCurrentUser().getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    CompanyProfile companyProfile = dataSnapshot.getValue(CompanyProfile.class);
                    if (companyProfile != null) {
                        Glide.with(getActivity()).load(companyProfile.getCompanyProfileImage()).into(companyImage);
                        companyAboutUs.setText(companyProfile.getCompanyAboutUs());
                        companyContact.setText(companyProfile.getCompanyContact());
                        companyEmail.setText(companyProfile.getCompanyEmail());
                        companyLocation.setText(companyProfile.getCompanyLocation());
                        view = true;

                        if (view) {
                            errorCreateProfile.setVisibility(View.GONE);
                            rootView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        if (!view) {
            rootView.setVisibility(View.GONE);
            errorCreateProfile.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

        return mainView;
    }

}
