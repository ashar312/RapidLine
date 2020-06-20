package com.project.rapidline.Activities.RapidLine.ViewData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.project.rapidline.Activities.RapidLine.Forms.VechileForm;
import com.project.rapidline.Adapters.ListAdapter;
import com.project.rapidline.Adapters.OnItemClickListener;
import com.project.rapidline.Models.RapidLine.Vechile;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityVechilesBinding;
import com.project.rapidline.viewmodel.RapidLine.RapidLineViewModel;

import java.util.ArrayList;
import java.util.List;

public class VechilesActivity extends AppCompatActivity implements OnItemClickListener {

    private ActivityVechilesBinding vechilesBinding;
    private RapidLineViewModel rapidLineViewModel;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vechilesBinding= DataBindingUtil.setContentView(this,R.layout.activity_vechiles);
        rapidLineViewModel = ViewModelProviders.of(this).get(RapidLineViewModel.class);


        setupRecyclerView();

        rapidLineViewModel.getAllVechiles().observe(this,vechiles -> {
            List<String> vechList=new ArrayList<>();
            for(Vechile vechile:vechiles){
                vechList.add(vechile.getRegNo());
            }
            listAdapter.setListItems((ArrayList<String>) vechList);
        });

        vechilesBinding.addCommonBtn.setOnClickListener(view -> {
            Intent intent=new Intent(VechilesActivity.this, VechileForm.class);
            intent.putExtra("action","add");
            startActivity(intent);

        });

        vechilesBinding.searchCommon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void setupRecyclerView() {
        listAdapter = new ListAdapter(this, new ArrayList<>(), this);
        vechilesBinding.vechileRecycler.setLayoutManager(new LinearLayoutManager(this));
        vechilesBinding.vechileRecycler.setAdapter(listAdapter);
        vechilesBinding.vechileRecycler.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    public void onItemClick(String itemId, String action) {

        if (action.equals("delete")) {
            rapidLineViewModel.deleteVechile(itemId);
        }
        else {
            //edit
            Intent intent=new Intent(VechilesActivity.this, VechileForm.class);
            intent.putExtra("action",action);
            intent.putExtra("itemId",itemId);
            startActivity(intent);
        }
    }
}
