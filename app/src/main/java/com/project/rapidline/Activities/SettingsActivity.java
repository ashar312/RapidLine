package com.project.rapidline.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.rapidline.Activities.Forms.AdminForm;
import com.project.rapidline.Activities.ViewData.RapidLine.StaffRegistationActivity;
import com.project.rapidline.Adapters.ListAdapter;
import com.project.rapidline.Adapters.OnItemClickListener;
import com.project.rapidline.Models.Admins;
import com.project.rapidline.Models.SaeedSons.Cities;
import com.project.rapidline.Models.SaeedSons.KindOfItem;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivitySettingsBinding;
import com.project.rapidline.utils.Responses;
import com.project.rapidline.viewmodel.SaeedSons.SaeedSonsViewModel;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity implements OnItemClickListener {

    private enum SettingsTabs {
        Cities,
        Kinds,
        Admins
    }

    private SaeedSonsViewModel saeedSonsViewModel;
    private ActivitySettingsBinding settingsBinding;
    private ListAdapter listAdapter;
    private SettingsTabs selectedTab = SettingsTabs.Cities;
    private boolean firstTimeLoaded = true;

    //Lists of data
    private ArrayList<String> itemList = new ArrayList<>();
    private ArrayList<String> citiesList = new ArrayList<>();
    private ArrayList<String> adminsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        saeedSonsViewModel = ViewModelProviders.of(this).get(SaeedSonsViewModel.class);

        setupRecyclerView();
        setupObservers();

        //Initialize click listener
        settingsBinding.citiesBtn.setOnClickListener(view -> {
            resetOldTabDesign();
            //then set new tab design
            settingsBinding.citiesBtn.setTypeface(settingsBinding.citiesBtn.getTypeface(), Typeface.BOLD);
            settingsBinding.citiesLine.setVisibility(View.VISIBLE);
            selectedTab = SettingsTabs.Cities;

            //then set data
            listAdapter.setListItems(citiesList);

        });

        settingsBinding.kindsBtn.setOnClickListener(view -> {

            resetOldTabDesign();
            //then set new tab design
            settingsBinding.kindsBtn.setTypeface(settingsBinding.kindsBtn.getTypeface(), Typeface.BOLD);
            settingsBinding.kindsLine.setVisibility(View.VISIBLE);
            selectedTab = SettingsTabs.Kinds;

            //then set data
            listAdapter.setListItems(itemList);

        });

        settingsBinding.adminsBtn.setOnClickListener(view -> {

            resetOldTabDesign();
            //then set new tab design
            settingsBinding.adminsBtn.setTypeface(settingsBinding.adminsBtn.getTypeface(), Typeface.BOLD);
            settingsBinding.adminsLine.setVisibility(View.VISIBLE);
            selectedTab = SettingsTabs.Admins;


            //then set data
            listAdapter.setListItems(adminsList);

        });

        settingsBinding.addCommonBtn.setOnClickListener(view -> {
            if (selectedTab == SettingsTabs.Admins) {
                startActivity(new Intent(this, AdminForm.class));
            } else {
                //open popup
                showAddPopup();
            }

        });
    }

    private void setupRecyclerView() {
        listAdapter = new ListAdapter(this, new ArrayList<>(), this);
        settingsBinding.settingsRv.setLayoutManager(new LinearLayoutManager(this));
        settingsBinding.settingsRv.setAdapter(listAdapter);
        settingsBinding.settingsRv.setItemAnimator(new DefaultItemAnimator());
    }

    private void setupObservers() {
        saeedSonsViewModel.getListAllCities(this).observe(this, cities -> {
            citiesList.clear();
            for (Cities cities1 : cities) {
                citiesList.add(cities1.getName());
            }

            if (firstTimeLoaded) {
                firstTimeLoaded = false;
                listAdapter.setListItems(citiesList);
            }

        });
        saeedSonsViewModel.getListAllItems(this).observe(this, kindOfItems -> {
            itemList.clear();
            for (KindOfItem item : kindOfItems) {
                itemList.add(item.getName());
            }

        });
        saeedSonsViewModel.getListAllAdmins(this).observe(this, admins -> {
            adminsList.clear();
            for (Admins admins1 : admins) {
                adminsList.add(admins1.getUsername());
            }

        });
    }

    private void resetOldTabDesign() {
        if (selectedTab == SettingsTabs.Cities) {

            settingsBinding.citiesBtn.setTypeface(Typeface.DEFAULT);
            settingsBinding.citiesLine.setVisibility(View.GONE);
        } else if (selectedTab == SettingsTabs.Kinds) {
            settingsBinding.kindsBtn.setTypeface(Typeface.DEFAULT);
            settingsBinding.kindsLine.setVisibility(View.GONE);
        } else {
            settingsBinding.adminsBtn.setTypeface(Typeface.DEFAULT);
            settingsBinding.adminsLine.setVisibility(View.GONE);
        }

    }


    @Override
    public void onItemClick(String itemId, String action) {
        if (selectedTab == SettingsTabs.Cities) {
            if (action.equals("delete")) {
                saeedSonsViewModel.deleteCity(itemId);
            } else {
                //update
                showUpdatePopup(itemId);
            }

        } else if (selectedTab == SettingsTabs.Kinds) {
            if (action.equals("delete")) {
                saeedSonsViewModel.deleteItem(itemId);
            } else {
                //update
                showUpdatePopup(itemId);
            }
        } else {
            //open admins for edit or delete

        }
    }


    private void showUpdatePopup(String itemId) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        View view = layoutInflaterAndroid.inflate(R.layout.popup_settings, null);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setView(view);

        TextView heading = view.findViewById(R.id.popup_heading);
        EditText text = view.findViewById(R.id.popup_edit_text);
        Button updateBtn = view.findViewById(R.id.popup_btn);


        //Initialize fields
        if (selectedTab == SettingsTabs.Cities) {
            heading.setText("Update City");
            text.setText(itemId);
        } else {
            //items
            heading.setText("Update items");
            text.setText(itemId);
        }

        //Initialize buttons
        alertBuilder.setCancelable(true);

        AlertDialog dialog = alertBuilder.create();
        dialog.show();

        updateBtn.setOnClickListener(view1 -> {
            //Check for empty edit text
            if (TextUtils.isEmpty(text.getText())) {
                Toast.makeText(this, "Field cannot be left empty", Toast.LENGTH_SHORT).show();
            } else {

                //update city or item
                if (selectedTab == SettingsTabs.Cities) {
                    //update city
                    saeedSonsViewModel.updateCity(itemId,text.getText().toString());
                    dialog.dismiss();

                } else {
                    //add item
                    saeedSonsViewModel.updateCity(itemId,text.getText().toString());
                    dialog.dismiss();

                }
            }

        });
    }

    private void showAddPopup() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        View view = layoutInflaterAndroid.inflate(R.layout.popup_settings, null);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setView(view);

        TextView heading = view.findViewById(R.id.popup_heading);
        EditText text = view.findViewById(R.id.popup_edit_text);
        Button addBtn = view.findViewById(R.id.popup_btn);

        //Initialize fields
        if (selectedTab == SettingsTabs.Cities) {
            heading.setText("Add City");
            text.setHint("Enter a city");
        } else {
            //items
            heading.setText("Add items");
            text.setHint("Enter a item");
        }


        //Initialize buttons
        alertBuilder.setCancelable(true);

        AlertDialog dialog = alertBuilder.create();
        dialog.show();

        addBtn.setOnClickListener(view1 -> {
            //Check for empty edit text
            if (TextUtils.isEmpty(text.getText())) {
                Toast.makeText(this, text.getHint(), Toast.LENGTH_SHORT).show();
            } else {

                //add city or item
                if (selectedTab == SettingsTabs.Cities) {
                    //add city
                    saeedSonsViewModel.addCity(new Cities(text.getText().toString())).observe(this, response -> {
                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                        if (response.equals(Responses.CITY_ADDED))
                            dialog.dismiss();
                    });

                } else {
                    //add item
                    saeedSonsViewModel.addItem(new KindOfItem(text.getText().toString())).observe(this,response -> {
                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                        if(response.equals(Responses.ITEM_ADDED)){
                            dialog.dismiss();
                        }
                    });

                }
            }

        });

    }


}
