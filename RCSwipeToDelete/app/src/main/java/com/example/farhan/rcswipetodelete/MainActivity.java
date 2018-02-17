package com.example.farhan.rcswipetodelete;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<dataModel> modelArrayList;
    private RVCustomAdapter mAdapter;
    private LinearLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.mRecyclerView);
        modelArrayList = new ArrayList<>();
        loadData();
        mAdapter = new RVCustomAdapter(modelArrayList);
        rootLayout = findViewById(R.id.rootLayout);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (viewHolder instanceof RVCustomAdapter.RvCustomViewHolder) {
                    // get the removed item name to display it in snack bar
                    String name = modelArrayList.get(viewHolder.getAdapterPosition()).getFruitsName();

                    // backup of removed item for undo purpose
                    final dataModel deletedItem = modelArrayList.get(viewHolder.getAdapterPosition());
                    final int deletedIndex = viewHolder.getAdapterPosition();

                    // remove the item from recycler view
                    mAdapter.removeItem(viewHolder.getAdapterPosition());

                    // showing snack bar with Undo option
                    Snackbar snackbar = Snackbar.make(rootLayout, name + " removed from the List!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // undo is selected, restore the deleted item
                            mAdapter.restoreItem(deletedItem, deletedIndex);
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }
            }

        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void loadData() {
        modelArrayList.add(new dataModel("Apple"));
        modelArrayList.add(new dataModel("Banana"));
        modelArrayList.add(new dataModel("Guava"));
        modelArrayList.add(new dataModel("Graphs"));
        modelArrayList.add(new dataModel("Pineapple"));

    }
}
