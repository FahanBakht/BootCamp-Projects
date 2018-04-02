package com.example.farhan.parkingbookingsystem.Adapters;

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
import com.example.farhan.parkingbookingsystem.Models.Malls;
import com.example.farhan.parkingbookingsystem.R;

import java.util.ArrayList;

/**
 * Created by Farhan on 3/20/2018.
 */

public class MallsAdapter extends ArrayAdapter<Malls> {
    private ArrayList<Malls> mallsArrayList;

    public MallsAdapter(@NonNull Context context, ArrayList<Malls> mallsArrayList) {
        super(context, 0, mallsArrayList);
        this.mallsArrayList = mallsArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.malls_item_view, parent, false);
        }

        ImageView imageViewMalls = convertView.findViewById(R.id.img_Malls);
        TextView textViewMalls = convertView.findViewById(R.id.txt_Malls);

        Malls mallsObj = mallsArrayList.get(position);
        Glide.with(parent.getContext()).load(mallsObj.getMallImage()).into(imageViewMalls);
        textViewMalls.setText(mallsObj.getMallName());
        return convertView;
    }
}
