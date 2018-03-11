package com.example.farhan.campussystem.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.farhan.campussystem.Models.CompanyProfile;
import com.example.farhan.campussystem.R;

import java.util.ArrayList;

/**
 * Created by Farhan on 3/8/2018.
 */

public class StudentViewCompaniesAdapter extends ArrayAdapter<CompanyProfile> {

    private ArrayList<CompanyProfile> companyProfileArrayList;

    public StudentViewCompaniesAdapter(@NonNull Context context, ArrayList<CompanyProfile> companyProfileArrayList) {
        super(context, 0, companyProfileArrayList);
        this.companyProfileArrayList = companyProfileArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_all_companies_item_view, parent, false);
        }

        ImageView companyImage = convertView.findViewById(R.id.img_View_All_Companies_Image);
        TextView companyName = convertView.findViewById(R.id.txt_View_All_Companies_Name);
        TextView companyEmail = convertView.findViewById(R.id.txt_View_All_Companies_Email);
        TextView companyAboutUs = convertView.findViewById(R.id.txt_View_All_Companies_AboutUs);

        CompanyProfile companyProfile = companyProfileArrayList.get(position);

        Glide.with(getContext()).load(companyProfile.getCompanyProfileImage()).into(companyImage);
        companyName.setText(companyProfile.getCompanyName());
        companyEmail.setText(companyProfile.getCompanyEmail());
        companyAboutUs.setText(companyProfile.getCompanyAboutUs());

        return convertView;
    }
}
