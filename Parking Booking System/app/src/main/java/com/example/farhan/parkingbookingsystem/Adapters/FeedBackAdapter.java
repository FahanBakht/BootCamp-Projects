package com.example.farhan.parkingbookingsystem.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.farhan.parkingbookingsystem.Models.FeedBack;
import com.example.farhan.parkingbookingsystem.R;

import java.util.ArrayList;

/**
 * Created by Farhan on 3/21/2018.
 */

public class FeedBackAdapter extends ArrayAdapter<FeedBack> {

    private ArrayList<FeedBack> feedBackArrayList;

    public FeedBackAdapter(@NonNull Context context, ArrayList<FeedBack> feedBackArrayList) {
        super(context, 0, feedBackArrayList);
        this.feedBackArrayList = feedBackArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.feedback_item_view, parent, false);
        }

        TextView userName = convertView.findViewById(R.id.txtUserName);
        TextView txtFeedBack = convertView.findViewById(R.id.txtFeedBack);
        TextView txtShowDate = convertView.findViewById(R.id.txtShowDate);
        LinearLayout rooLinearLayout = convertView.findViewById(R.id.admin_FeedBack_Reply_Root);
        TextView adminFeedBackTxt = convertView.findViewById(R.id.admin_FeedBack_Reply_Txt);

        FeedBack feedBack = feedBackArrayList.get(position);

        if (feedBack.getAdminReply().equals("noReply")) {
            rooLinearLayout.setVisibility(View.GONE);
        } else {
            rooLinearLayout.setVisibility(View.VISIBLE);
            adminFeedBackTxt.setText(feedBack.getAdminReply());
        }

        userName.setText(feedBack.getUserName());
        txtFeedBack.setText(feedBack.getFeedBack());
        txtShowDate.setText(feedBack.getDate());

        return convertView;
    }
}