package com.project.rapidline.HomeScreens.AddActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.rapidline.Form.AgentForm;
import com.project.rapidline.Form.LabourForm;
import com.project.rapidline.Form.PatriForm;
import com.project.rapidline.Form.SenderRecieverForm;
import com.project.rapidline.Form.TransportersForm;
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

        //Set title
        final String ActivityValue = getIntent().getExtras().get("ListItem").toString();
        TextView textView = findViewById(R.id.ListItemText);
        textView.setText(ActivityValue);

        Button button = findViewById(R.id.add_common_Btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenWhichActivity(ActivityValue);
            }
        });

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

    private void OpenWhichActivity(String listitem){
        Intent intent;
        switch (listitem){
            case "SenderReceiver":
                intent = new Intent(ListActivities.this, SenderRecieverForm.class);
                intent.putExtra("ListItem",listitem);
                startActivity(intent);
                break;
            case "Agents":
                intent = new Intent(ListActivities.this, AgentForm.class);
                intent.putExtra("ListItem",listitem);
                startActivity(intent);
                break;
            case "Labour":
                intent = new Intent(ListActivities.this, LabourForm.class);
                intent.putExtra("ListItem",listitem);
                startActivity(intent);
                break;
            case "Transporters":
                intent = new Intent(ListActivities.this, TransportersForm.class);
                intent.putExtra("ListItem",listitem);
                startActivity(intent);
                break;
            case "Patri":
                intent = new Intent(ListActivities.this, PatriForm.class);
                intent.putExtra("ListItem",listitem);
                startActivity(intent);
            default:
                break;

        }



    }
}
