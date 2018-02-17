package com.example.farhan.rcswipetodelete;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Farhan on 2/16/2018.
 */

public class RVCustomAdapter extends RecyclerView.Adapter<RVCustomAdapter.RvCustomViewHolder> {


    class RvCustomViewHolder extends RecyclerView.ViewHolder {
        TextView mFruitsName;

        public RvCustomViewHolder(View itemView) {
            super(itemView);

            mFruitsName = itemView.findViewById(R.id.tv_rvFruits);
        }
    }


    private ArrayList<dataModel> modelArrayList;


    public RVCustomAdapter(ArrayList<dataModel> modelArrayList) {
        this.modelArrayList = modelArrayList;
    }

    @Override
    public RvCustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_listitem, parent, false);
        return new RvCustomViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RvCustomViewHolder holder, int position) {
        dataModel dataModel = modelArrayList.get(position);
        holder.mFruitsName.setText(dataModel.getFruitsName());
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public void removeItem(int position) {
        modelArrayList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()

        notifyItemRemoved(position);
    }

    public void restoreItem(dataModel item, int position) {
        modelArrayList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

}
