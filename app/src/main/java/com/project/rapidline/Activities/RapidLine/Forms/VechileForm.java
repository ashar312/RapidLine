package com.project.rapidline.Activities.RapidLine.Forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.rapidline.Models.RapidLine.Vechile;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityVechileFormBinding;
import com.project.rapidline.utils.Responses;
import com.project.rapidline.viewmodel.RapidLine.RapidLineViewModel;

import java.util.ArrayList;
import java.util.List;

public class VechileForm extends AppCompatActivity {

    private ActivityVechileFormBinding vechilesBinding;
    private RapidLineViewModel rapidLineViewModel;
    private String action;
    private Vechile vechileEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vechile_form);
        vechilesBinding = DataBindingUtil.setContentView(this, R.layout.activity_vechile_form);
        rapidLineViewModel = ViewModelProviders.of(this).get(RapidLineViewModel.class);

        Bundle bundle = getIntent().getExtras();
        action = bundle.get("action").toString();




        //initialize UI
        if (action.equals("edit")) {

            vechilesBinding.regNoTxt.setEnabled(false);
            //Load data
            String id = bundle.getString("itemId");
            rapidLineViewModel.getVechilesById(id).observe(this, vechile -> {
                vechileEdit = vechile;
                loadData();
            });
        }

        initializeSpinners();


        vechilesBinding.saveVechBtn.setOnClickListener(view -> {
            if (isFieldEmpty()) {
                Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (action.equals("edit")) {
                vechileEdit.setRegNo(vechilesBinding.regNoTxt.getText().toString());
                vechileEdit.setChasisNo(vechilesBinding.chasisNoTxt.getText().toString());
                vechileEdit.setVechileCompany(vechilesBinding.vechCompanyTxt.getText().toString());
                vechileEdit.setCapacity(Integer.parseInt(vechilesBinding.vechCapacityTxt.getText().toString()));

                vechileEdit.setVechileCategory(vechilesBinding.vechCategorySpinner.getSelectedItem().toString());
                vechileEdit.setModel(vechilesBinding.modelSpinner.getSelectedItem().toString());

                rapidLineViewModel.updateVechile(vechileEdit);

                Toast.makeText(this, "Vechile updated sucessfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {

                vechileEdit = new Vechile();
                vechileEdit.setRegNo(vechilesBinding.regNoTxt.getText().toString());
                vechileEdit.setChasisNo(vechilesBinding.chasisNoTxt.getText().toString());
                vechileEdit.setVechileCompany(vechilesBinding.vechCompanyTxt.getText().toString());
                vechileEdit.setCapacity(Integer.parseInt(vechilesBinding.vechCapacityTxt.getText().toString()));

                vechileEdit.setVechileCategory(vechilesBinding.vechCategorySpinner.getSelectedItem().toString());
                vechileEdit.setModel(vechilesBinding.modelSpinner.getSelectedItem().toString());

                rapidLineViewModel.addVechile(vechileEdit).observe(this, response -> {
                    Toast.makeText(this, response, Toast.LENGTH_SHORT).show();

                    if (response.equals(Responses.VECHILE_ADDED)) {
                        finish();
                    }
                });

            }

        });


    }

    private void initializeSpinners() {

        //Load vechile category
        rapidLineViewModel.getAllVechileCategory().observe(this, strings -> {
            List<String> categoryList = new ArrayList<>(strings);
            categoryList.add(0, "Select a category");
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item, categoryList);
            vechilesBinding.vechCategorySpinner.setAdapter(arrayAdapter);
        });

        //Load models
        rapidLineViewModel.getAllVechileModels().observe(this, strings -> {
            List<String> modelList = new ArrayList<>(strings);
            modelList.add(0, "Select a model");
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item, modelList);
            vechilesBinding.modelSpinner.setAdapter(arrayAdapter);

        });
    }


    private boolean isFieldEmpty() {
        if (TextUtils.isEmpty(vechilesBinding.regNoTxt.getText())
                || TextUtils.isEmpty(vechilesBinding.chasisNoTxt.getText())
                || TextUtils.isEmpty(vechilesBinding.vechCompanyTxt.getText())
                || TextUtils.isEmpty(vechilesBinding.vechCapacityTxt.getText())
                || vechilesBinding.modelSpinner.getSelectedItem().toString().equals("Select a model")
                || vechilesBinding.vechCategorySpinner.getSelectedItem().toString().equals("Select a category")) {
            return true;
        }

        return false;
    }

    private void loadData() {
        vechilesBinding.regNoTxt.setText(vechileEdit.getRegNo());
        vechilesBinding.chasisNoTxt.setText(vechileEdit.getChasisNo());
        vechilesBinding.vechCompanyTxt.setText(vechileEdit.getVechileCompany());
        vechilesBinding.vechCapacityTxt.setText(String.valueOf(vechileEdit.getCapacity()));

        //Load spinner data
        int modelPos = getIndex(vechilesBinding.modelSpinner, vechileEdit.getModel());
        vechilesBinding.modelSpinner.setSelection(modelPos);

        int categoryPos = getIndex(vechilesBinding.vechCategorySpinner, vechileEdit.getVechileCategory());
        vechilesBinding.vechCategorySpinner.setSelection(categoryPos);

    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return 0;
    }

}
