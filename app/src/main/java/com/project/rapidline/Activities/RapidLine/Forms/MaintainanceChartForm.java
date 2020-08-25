package com.project.rapidline.Activities.RapidLine.Forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.project.rapidline.Models.RapidLine.MaintainanceChart;
import com.project.rapidline.Models.RapidLine.Vechile;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityMaintainanceChartFormBinding;
import com.project.rapidline.viewmodel.RapidLine.RapidLineViewModel;

import java.util.ArrayList;
import java.util.List;

public class MaintainanceChartForm extends AppCompatActivity {

    private ActivityMaintainanceChartFormBinding maintainanceChartFormBinding;
    private RapidLineViewModel rapidLineViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        maintainanceChartFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_maintainance_chart_form);
        rapidLineViewModel = ViewModelProviders.of(this).get(RapidLineViewModel.class);

        initializeDataFields();


        maintainanceChartFormBinding.categChipTxt.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addToCategoryChip(maintainanceChartFormBinding.categChipTxt.getText().toString());
                maintainanceChartFormBinding.categChipTxt.setText("");
                return true;
            }

            return false;
        });

//        maintainanceChartFormBinding.categChipTxt.setOnFocusChangeListener((view, hasFocus) -> {
//            maintainanceChartFormBinding.categChipTxt.showDropDown();
//        });
//
//        maintainanceChartFormBinding.categChipTxt.setOnClickListener(view -> {
//            maintainanceChartFormBinding.categChipTxt.showDropDown();
//        });

        maintainanceChartFormBinding.categoryDropdownBtn.setOnClickListener(view -> {
            maintainanceChartFormBinding.categChipTxt.showDropDown();
        });

        maintainanceChartFormBinding.categChipTxt.setOnItemClickListener((adapterView, view, pos, l) -> {
            String selectedItem = (String) adapterView.getItemAtPosition(pos);
            closeKeyBoard();
            addToCategoryChip(selectedItem);
            maintainanceChartFormBinding.categChipTxt.setText("");
//                Toast.makeText(MaintainanceChartForm.this,selectedItem,Toast.LENGTH_SHORT).show();
        });


        maintainanceChartFormBinding.specificChipTxt.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addSpecificsChip(maintainanceChartFormBinding.specificChipTxt.getText().toString());
                maintainanceChartFormBinding.specificChipTxt.setText("");
                return true;
            }

            return false;
        });


        maintainanceChartFormBinding.specificChipTxt.setOnItemClickListener((adapterView, view, pos, l) -> {
            String selectedItem = (String) adapterView.getItemAtPosition(pos);
            closeKeyBoard();
            addSpecificsChip(selectedItem);
            maintainanceChartFormBinding.specificChipTxt.setText("");
        });


        maintainanceChartFormBinding.specificDropdownBtn.setOnClickListener(view -> {
            maintainanceChartFormBinding.specificChipTxt.showDropDown();
        });

        maintainanceChartFormBinding.saveChartBtn.setOnClickListener(view -> {
            if (isFieldEmpty()) {
                Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
                return;
            }

            //Now do the work for database

            MaintainanceChart maintainanceChartdata=new MaintainanceChart();
            maintainanceChartdata.setRegNo(maintainanceChartFormBinding.vechileSpinner.getSelectedItem().toString());
            maintainanceChartdata.setVechileCurrentKm(maintainanceChartFormBinding.currentKmTxt.getText().toString());
            maintainanceChartdata.setNotes(maintainanceChartFormBinding.commentsTxt.getText().toString());

            //Now get the data of chips
            List<String> categoryChipsList=new ArrayList<>();


            for (int i=0;i<maintainanceChartFormBinding.categChipGroup.getChildCount();i++){
                Chip chip= (Chip) maintainanceChartFormBinding.categChipGroup.getChildAt(i);

                categoryChipsList.add(chip.getText().toString());
            }


            List<String> specificsChipsList=new ArrayList<>();

            for (int i=0;i<maintainanceChartFormBinding.specificsChipGroup.getChildCount();i++){
                Chip chip= (Chip) maintainanceChartFormBinding.specificsChipGroup.getChildAt(i);

                specificsChipsList.add(chip.getText().toString());
            }

            maintainanceChartdata.setMaintainanceCategory(categoryChipsList);
            maintainanceChartdata.setMaintainanceSpecifics(specificsChipsList);

            rapidLineViewModel.addMaintainanceChartRecord(maintainanceChartdata);
        });

        maintainanceChartFormBinding.backBtn.setOnClickListener(view -> {
            finish();
        });

    }

    private void addToCategoryChip(String chipText) {
        LayoutInflater inflater = LayoutInflater.from(MaintainanceChartForm.this);
        Chip categoryChip = (Chip) inflater.inflate(R.layout.chip_item, null, false);

        categoryChip.setText(chipText);

        categoryChip.setOnCloseIconClickListener(view -> {
            maintainanceChartFormBinding.categChipGroup.removeView(view);
        });

        maintainanceChartFormBinding.categChipGroup.addView(categoryChip);
        closeKeyBoard();
    }

    private void addSpecificsChip(String chipText) {
        LayoutInflater inflater = LayoutInflater.from(MaintainanceChartForm.this);
        Chip specificChip = (Chip) inflater.inflate(R.layout.chip_item, null, false);

        specificChip.setText(chipText);

        specificChip.setOnCloseIconClickListener(view -> {
            maintainanceChartFormBinding.specificsChipGroup.removeView(view);
        });

        maintainanceChartFormBinding.specificsChipGroup.addView(specificChip);
        closeKeyBoard();
    }

    private void closeKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void initializeDataFields() {

        //Vechiles spinner
        rapidLineViewModel.getAllVechiles().observe(this, vechiles -> {
            List<Vechile> vechileList = new ArrayList<>(vechiles);
            vechileList.add(0, new Vechile("Select a vechile"));
            ArrayAdapter<Vechile> arrayAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item, vechileList);
            maintainanceChartFormBinding.vechileSpinner.setAdapter(arrayAdapter);
        });

        //Category Autocomplete text
        rapidLineViewModel.getAllMaintainanceData().observe(this, maintainanceData -> {
            List<String> maintainanceCategory = new ArrayList<>(maintainanceData.getMaintainanceCategory());
            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item, maintainanceCategory);
            maintainanceChartFormBinding.categChipTxt.setAdapter(categoryAdapter);


            List<String> specificCategory = new ArrayList<>(maintainanceData.getMaintainanceSpecifics());
            ArrayAdapter<String> specificAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item, specificCategory);
            maintainanceChartFormBinding.specificChipTxt.setAdapter(specificAdapter);

        });


    }

    private boolean isFieldEmpty() {
        if (maintainanceChartFormBinding.vechileSpinner.getSelectedItem().toString().equals("Select a vechile")
                || TextUtils.isEmpty(maintainanceChartFormBinding.currentKmTxt.getText())
                || TextUtils.isEmpty(maintainanceChartFormBinding.commentsTxt.getText()) ||
                isChipsEmpty()) {
            return true;
        }
        return false;
    }

    private boolean isChipsEmpty() {
        if (maintainanceChartFormBinding.categChipGroup.getChildCount() == 0
                || maintainanceChartFormBinding.specificsChipGroup.getChildCount() == 0) {
            return true;
        }
        return false;
    }

}
