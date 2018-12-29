package com.example.farhan.parkingbookingsystem.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.farhan.parkingbookingsystem.Models.User;
import com.example.farhan.parkingbookingsystem.R;

import java.util.ArrayList;

/**
 * Created by Farhan on 3/21/2018.
 */

public class AdminUsersAdapter extends ArrayAdapter<User> {

    private ArrayList<User> userArrayList;

    public AdminUsersAdapter(@NonNull Context context, ArrayList<User> userArrayList) {
        super(context, 0, userArrayList);
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.admin_users_item_view, parent, false);
        }

        TextView txtUName = convertView.findViewById(R.id.txt_Admin_User_Name);
        TextView txtUEmail = convertView.findViewById(R.id.txt_Admin_User_Email);
        TextView txtUCnincNumber = convertView.findViewById(R.id.txt_Admin_User_Cnic_Number);
        TextView txtUCarName = convertView.findViewById(R.id.txt_Admin_User_Car_Name);
        TextView txtUCarLicenseNumber = convertView.findViewById(R.id.txt_Admin_User_Car_License_Number);

        User user = userArrayList.get(position);
        txtUName.setText(user.getUserName());
        txtUEmail.setText(user.getUserEmail());
        txtUCnincNumber.setText(user.getUserCnicNumber());
        txtUCarName.setText(user.getUserCarName());
        txtUCarLicenseNumber.setText(user.getUserCarLicensePlateNumber());


        return convertView;
    }
}
