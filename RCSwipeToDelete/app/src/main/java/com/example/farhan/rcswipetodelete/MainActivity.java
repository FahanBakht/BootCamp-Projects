package com.example.farhan.rcswipetodelete;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<dataModel> modelArrayList;
    private RVCustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.mRecyclerView);
        modelArrayList = new ArrayList<>();
        loadData();
        mAdapter = new RVCustomAdapter(modelArrayList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mAdapter.removeItem(viewHolder.getAdapterPosition());
            }

        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void loadData(){
        modelArrayList.add(new dataModel("Apple"));
        modelArrayList.add(new dataModel("Banana"));
        modelArrayList.add(new dataModel("Guava"));
        modelArrayList.add(new dataModel("Graphs"));
        modelArrayList.add(new dataModel("Pineapple"));

    }
}
