package com.project.rapidline.Activities.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.rapidline.Activities.Common.AdminForm;
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

public class SettingsActivity extends AppCompatActivity implements OnItemClickListener {

    private enum SettingsTabs {
        Cities,
        Kinds,
        Admins
    }

    private SaeedSonsViewModel saeedSonsViewModel;
    private ActivitySettingsBinding settingsBinding;
    private ListAdapter listAdapter;
    private SettingsTabs selectedTab = SettingsTabs.Kinds;

    //Lists of data
    private ArrayList<String> itemList = new ArrayList<>();
    private ArrayList<String> adminsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        saeedSonsViewModel = ViewModelProviders.of(this).get(SaeedSonsViewModel.class);

        setupRecyclerView();
        setupObservers();

        //Filtering data work
        settingsBinding.searchCommon.addTextChangedListener(new TextWatcher() {
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


        settingsBinding.kindsBtn.setOnClickListener(view -> {

            resetOldTabDesign();
            //then set new tab design
            settingsBinding.kindsBtn.setTypeface(settingsBinding.kindsBtn.getTypeface(), Typeface.BOLD);
            settingsBinding.kindsLine.setVisibility(View.VISIBLE);
            selectedTab = SettingsTabs.Kinds;

            //then set data
            listAdapter.setUpdateOptionVisible(false);
            listAdapter.setListItems(itemList);

        });

        settingsBinding.adminsBtn.setOnClickListener(view -> {

            resetOldTabDesign();
            //then set new tab design
            settingsBinding.adminsBtn.setTypeface(settingsBinding.adminsBtn.getTypeface(), Typeface.BOLD);
            settingsBinding.adminsLine.setVisibility(View.VISIBLE);
            selectedTab = SettingsTabs.Admins;


            //then set data
            listAdapter.setUpdateOptionVisible(true);
            listAdapter.setListItems(adminsList);

        });

        settingsBinding.addCommonBtn.setOnClickListener(view -> {
            if (selectedTab == SettingsTabs.Admins) {
//                Intent intent = new Intent(this, AdminForm.class);
//                intent.putExtra("action", "add");
//                startActivity(intent);
                Toast.makeText(this,"Admins cannot be added currently",Toast.LENGTH_SHORT).show();
            } else {
                //open popup
                showAddPopup();
            }

        });

        settingsBinding.backBtn.setOnClickListener(view -> {
            finish();
        });
    }

    private void setupRecyclerView() {
        listAdapter = new ListAdapter(this, new ArrayList<>(), this);
        settingsBinding.settingsRv.setLayoutManager(new LinearLayoutManager(this));
        settingsBinding.settingsRv.setAdapter(listAdapter);
        settingsBinding.settingsRv.setItemAnimator(new DefaultItemAnimator());
    }

    private void setupObservers() {
        saeedSonsViewModel.getListAllItems().observe(this, kindOfItems -> {
            itemList.clear();
            for (KindOfItem item : kindOfItems) {
                itemList.add(item.getName());
            }

            if (selectedTab == SettingsTabs.Kinds) {
                listAdapter.setUpdateOptionVisible(false);
                listAdapter.setListItems(itemList);
            }
        });

        saeedSonsViewModel.getListAllAdmins().observe(this, admins -> {
            adminsList.clear();
            for (Admins admins1 : admins) {
                adminsList.add(admins1.getUsername());
            }

            if (selectedTab == SettingsTabs.Admins) {
                listAdapter.setUpdateOptionVisible(true);
                listAdapter.setListItems(adminsList);
            }
        });
    }

    private void resetOldTabDesign() {
        if (selectedTab == SettingsTabs.Kinds) {
            settingsBinding.kindsBtn.setTypeface(Typeface.DEFAULT);
            settingsBinding.kindsLine.setVisibility(View.GONE);
        } else {
            settingsBinding.adminsBtn.setTypeface(Typeface.DEFAULT);
            settingsBinding.adminsLine.setVisibility(View.GONE);
        }

    }


    @Override
    public void onItemClick(String itemId, String action) {
        if (selectedTab == SettingsTabs.Kinds) {
            if (action.equals("delete")) {
                saeedSonsViewModel.deleteItem(itemId);
            }
//            else {
//                //update
//                showUpdatePopup(itemId);
//            }
        } else {
            if (action.equals("delete")) {
                saeedSonsViewModel.deleteAdminById(itemId);
            } else {
                //update
                Intent updateIntent = new Intent(this, AdminForm.class);
                updateIntent.putExtra("action", action);
                updateIntent.putExtra("itemId", itemId);
                startActivity(updateIntent);
            }

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

        updateBtn.setText("Update");
        //Initialize fields

        //items
        heading.setText("Update items");
        text.setText(itemId);


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

                //add item
                    saeedSonsViewModel.updateItem(itemId, text.getText().toString());
                    dialog.dismiss();

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

        addBtn.setText("Add");
        //Initialize fields

        //items
        heading.setText("Add items");
        text.setHint("Enter a item");


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

                //add item
                saeedSonsViewModel.addItem(new KindOfItem(text.getText().toString())).observe(this, response -> {
                    Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                    if (response.equals(Responses.ITEM_ADDED)) {
                        dialog.dismiss();
                    }
                });


            }

        });

    }


}
