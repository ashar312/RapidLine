package com.project.rapidline.Activities.RapidLine.ViewData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.project.rapidline.Activities.Common.PrintOutActivity;
import com.project.rapidline.Activities.RapidLine.Forms.AddBiltyForm;
import com.project.rapidline.Activities.SaeedSons.ViewData.ViewBailsActivity;
import com.project.rapidline.Adapters.BailsAdapter;
import com.project.rapidline.Adapters.BiltyAdapter;
import com.project.rapidline.Adapters.OnItemClickListener;
import com.project.rapidline.Models.RapidLine.Bilty;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityViewBiltyBinding;
import com.project.rapidline.utils.StaticClasses;
import com.project.rapidline.viewmodel.RapidLine.RapidLineViewModel;
import com.project.rapidline.viewmodel.SaeedSons.SaeedSonsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewBiltyActivity extends AppCompatActivity implements BiltyAdapter.OnNoteListener {

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

        activityViewBiltyBinding.backBtn.setOnClickListener(view -> {
            finish();
        });

    }

    private void setupRecyclerView() {
        biltyAdapter = new BiltyAdapter(this, new ArrayList<>(), this);
        activityViewBiltyBinding.biltyRv.setLayoutManager(new LinearLayoutManager(this));
        activityViewBiltyBinding.biltyRv.setAdapter(biltyAdapter);
        activityViewBiltyBinding.biltyRv.setItemAnimator(new DefaultItemAnimator());


        rapidLineViewModel.getAllBilty().observe(this, bilties -> {
            ArrayList<Bilty> combinedList = new ArrayList<>(bilties.get(0));
            combinedList.addAll(bilties.get(1));
            biltyAdapter.setBiltyArrayList(combinedList);
        });

        //On Right swipe listener
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                Bilty bilty = biltyAdapter.getBiltyArrayList().get(viewHolder.getAdapterPosition());
                if (bilty.getSupplierName().toLowerCase().contains("rapid line")) {
                    return true;
                } else
                    return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //TODO delete on swipe

                Bilty bilty = biltyAdapter.getBiltyArrayList().get(viewHolder.getAdapterPosition());
                if (!bilty.getSupplierName().toLowerCase().contains("rapid line")) {
                    rapidLineViewModel.deleteBilty(bilty.getBiltyNo());
                } else {
                    Toast.makeText(ViewBiltyActivity.this, "Bails cannot be deleted from here", Toast.LENGTH_SHORT).show();
                }
            }
        }).attachToRecyclerView(activityViewBiltyBinding.biltyRv);


    }


    @Override
    public void onBiltyClick(Bilty bilty) {
        //Check whether bail or bilty
        Intent intent = new Intent(ViewBiltyActivity.this, AddBiltyForm.class);
        intent.putExtra("action", "edit");
        intent.putExtra("itemId", bilty.getBiltyNo());

        if (bilty.getSupplierName().toLowerCase().contains("rapid line")) {
            intent.putExtra("itemType", "Bail");
        } else
            intent.putExtra("itemType", "Bilty");

//        if (bilty.getSupplierName().toLowerCase().contains("rapid line")) {
//            intent.putExtra("itemType", "Bilty");
//        } else
//            intent.putExtra("itemType", "Bail");
        startActivity(intent);
    }

}