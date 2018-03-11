package com.example.farhan.campussystem.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.farhan.campussystem.Models.AddJob;
import com.example.farhan.campussystem.R;

import java.util.ArrayList;

/**
 * Created by Farhan on 3/8/2018.
 */

public class JobAdapter extends ArrayAdapter<AddJob> {

    private ArrayList<AddJob> addJobArrayList;

    public JobAdapter(@NonNull Context context, ArrayList<AddJob> addJobArrayList) {
        super(context, 0, addJobArrayList);
        this.addJobArrayList = addJobArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.jobs_item_view, parent, false);
        }

        TextView txtItemJobName = convertView.findViewById(R.id.txt_Item_Job_Name);
        TextView txtItemJobExperience = convertView.findViewById(R.id.txt_Item_Job_Experience);
        TextView txtItemJobSalary = convertView.findViewById(R.id.txt_Item_Job_Salary);
        TextView txtItemJobType = convertView.findViewById(R.id.txt_Item_Job_Type);

        AddJob addJob = addJobArrayList.get(position);
        txtItemJobName.setText(addJob.getAddJobRole());
        txtItemJobExperience.setText(addJob.getAddJobExperience());
        txtItemJobSalary.setText(addJob.getAddJobSalary()+"$");
        txtItemJobType.setText(addJob.getAddJobType());

        return convertView;
    }
}
