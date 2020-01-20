package com.project.rapidline.HomeScreens.AddActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.project.rapidline.Models.RecyclerViewItemsCount;
import com.project.rapidline.R;

import java.util.ArrayList;

public class ListActivities extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<RecyclerViewItemsCount> recyclerViewItemsCounts;
    private com.project.rapidline.HomeScreens.Adapter.ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.List_recycler);
        setListModelArrayList();
        mAdapter = new com.project.rapidline.HomeScreens.Adapter.ListAdapter(getApplicationContext(),
                recyclerViewItemsCounts);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    private void setListModelArrayList(){
        recyclerViewItemsCounts = new ArrayList<>();
        recyclerViewItemsCounts.add(new RecyclerViewItemsCount(recyclerViewItemsCounts.size()+1+"",
                "Luke Cage"));
        recyclerViewItemsCounts.add(new RecyclerViewItemsCount(recyclerViewItemsCounts.size()+1+"",
                "Jessica Jones"));
        recyclerViewItemsCounts.add(new RecyclerViewItemsCount(recyclerViewItemsCounts.size()+1+"",
                "DareDevil"));
        recyclerViewItemsCounts.add(new RecyclerViewItemsCount(recyclerViewItemsCounts.size()+1+"",
                "Iron Fist"));
        recyclerViewItemsCounts.add(new RecyclerViewItemsCount(recyclerViewItemsCounts.size()+1+"",
                "Agent of Shield"));
        recyclerViewItemsCounts.add(new RecyclerViewItemsCount(recyclerViewItemsCounts.size()+1+"",
                "Marvel"));

    }
}
