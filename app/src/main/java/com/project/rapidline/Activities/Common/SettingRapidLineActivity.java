package com.project.rapidline.Activities.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.rapidline.Adapters.ListAdapter;
import com.project.rapidline.Adapters.OnItemClickListener;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivitySettingRapidLineBinding;
import com.project.rapidline.viewmodel.RapidLine.RapidLineViewModel;

import java.util.ArrayList;
import java.util.List;

public class SettingRapidLineActivity extends AppCompatActivity implements OnItemClickListener {

    private ActivitySettingRapidLineBinding binding;
    private RapidLineViewModel viewModel;
    private ListAdapter listAdapter;
    private List<String> vechileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingRapidLineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupViews();
        setupObservers();
    }

    private void setupViews() {

        viewModel = ViewModelProviders.of(this).get(RapidLineViewModel.class);

        listAdapter = new ListAdapter(this, new ArrayList<>(), this);
        binding.vechCategoryRv.setLayoutManager(new LinearLayoutManager(this));
        binding.vechCategoryRv.setAdapter(listAdapter);
        binding.vechCategoryRv.setItemAnimator(new DefaultItemAnimator());

        binding.addCommonBtn.setOnClickListener(v -> {
            showVechilePopup();
        });


    }

    private void setupObservers() {
        viewModel.getAllVechileCategory().observe(this, strings -> {
            vechileList = strings;
            listAdapter.setListItems((ArrayList<String>) strings);
        });

    }


    @Override
    public void onItemClick(String itemId, String action) {
        if (action.equals("delete")) {
            viewModel.removeVechileCategory(itemId);
        }
    }

    private boolean checkVechList(String mVech) {
        for (String vechile : vechileList) {
            if (vechile.equals(mVech))
                return true;
        }
        return false;
    }

    private void showVechilePopup() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        View view = layoutInflaterAndroid.inflate(R.layout.popup_settings, null);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setView(view);

        TextView heading = view.findViewById(R.id.popup_heading);
        EditText text = view.findViewById(R.id.popup_edit_text);
        Button addBtn = view.findViewById(R.id.popup_btn);

        heading.setText("Add Vechile Category");
        text.setHint("Enter category");
        addBtn.setText("Add");



        alertBuilder.setCancelable(true);

        AlertDialog dialog = alertBuilder.create();
        dialog.show();

        addBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(text.getText())) {
                Toast.makeText(this, "Field is empty", Toast.LENGTH_SHORT).show();
            } else if (checkVechList(text.getText().toString())) {
                Toast.makeText(this, "Vechile already present", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.addVechileCategory(text.getText().toString());
                Toast.makeText(this, "Sucessfull", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }

}