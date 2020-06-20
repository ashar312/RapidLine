package com.project.rapidline.Activities.RapidLine.ViewData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.project.rapidline.Activities.SaeedSons.ViewData.ViewBailsActivity;
import com.project.rapidline.Adapters.BailsAdapter;
import com.project.rapidline.Adapters.BiltyAdapter;
import com.project.rapidline.Adapters.OnItemClickListener;
import com.project.rapidline.Models.RapidLine.Bilty;
import com.project.rapidline.Models.SaeedSons.Bails;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityViewBiltyBinding;
import com.project.rapidline.utils.StaticClasses;
import com.project.rapidline.viewmodel.RapidLine.RapidLineViewModel;
import com.project.rapidline.viewmodel.SaeedSons.SaeedSonsViewModel;

import java.util.ArrayList;

public class ViewBiltyActivity extends AppCompatActivity implements OnItemClickListener, BailsAdapter.OnNoteListener {

    private ActivityViewBiltyBinding activityViewBiltyBinding;
    private SaeedSonsViewModel saeedSonsViewModel;
    private RapidLineViewModel rapidLineViewModel;
    private BiltyAdapter biltyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityViewBiltyBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_bilty);
        saeedSonsViewModel = ViewModelProviders.of(this).get(SaeedSonsViewModel.class);
        rapidLineViewModel = ViewModelProviders.of(this).get(RapidLineViewModel.class);

        //Initialize sort spinner
        ArrayList<String> sortOrder = StaticClasses.sortOrder;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item,
                sortOrder);
        activityViewBiltyBinding.sortSpinner.setAdapter(arrayAdapter);
        setupRecyclerView();

        activityViewBiltyBinding.searchTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                biltyAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        activityViewBiltyBinding.sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void setupRecyclerView() {
        biltyAdapter = new BiltyAdapter(this, new ArrayList<>(), this::onItemClick, this::onBailClick);
        activityViewBiltyBinding.biltyRv.setLayoutManager(new LinearLayoutManager(this));
        activityViewBiltyBinding.biltyRv.setAdapter(biltyAdapter);
        activityViewBiltyBinding.biltyRv.setItemAnimator(new DefaultItemAnimator());


        rapidLineViewModel.getAllBilty().observe(this, bilties -> {

            ArrayList<Bilty> biltyArrayList=new ArrayList<>(bilties);

            //also get the data from saeed sons
            saeedSonsViewModel.getListAllBails().observe(this,bails -> {
                for(Bails currBail:bails){
                    if(currBail.getTransporterId().equals("")){

                    }
                }


            });

            biltyAdapter.setBiltyArrayList((ArrayList<Bilty>) bilties);
        });

        //On Right swipe listener
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


            }
        }).attachToRecyclerView(activityViewBiltyBinding.biltyRv);



    }


    @Override
    public void onBailClick(String itemId) {

    }

    //on Print btn click
    @Override
    public void onItemClick(String itemId, String action) {

    }
}