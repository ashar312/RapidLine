package com.project.rapidline.Activities.RapidLine.ViewData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.project.rapidline.Activities.RapidLine.Forms.DriverSideKickForm;
import com.project.rapidline.Activities.RapidLine.Forms.OfficeStaffForm;
import com.project.rapidline.Adapters.ListAdapter;
import com.project.rapidline.Adapters.OnItemClickListener;
import com.project.rapidline.Models.RapidLine.Drivers;
import com.project.rapidline.Models.RapidLine.OfficeStaff;
import com.project.rapidline.Models.RapidLine.SideKick;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityStaffRegistationBinding;
import com.project.rapidline.viewmodel.RapidLine.RapidLineViewModel;

import java.util.ArrayList;
import java.util.List;

public class StaffRegistationActivity extends AppCompatActivity implements OnItemClickListener {

    public enum StaffRegTabs {
        Driver,
        SideKick,
        OfficeStaff
    }

    private StaffRegTabs selectedTab = StaffRegTabs.Driver;

    private ActivityStaffRegistationBinding staffRegistationBinding;
    private RapidLineViewModel rapidLineViewModel;
    private ListAdapter listAdapter;

    //List of data
    private ArrayList<String> driverArrayList = new ArrayList<>();
    private ArrayList<String> sidekickArrayList = new ArrayList<>();
    private ArrayList<String> staffArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        staffRegistationBinding = DataBindingUtil.setContentView(this, R.layout.activity_staff_registation);
        rapidLineViewModel = ViewModelProviders.of(this).get(RapidLineViewModel.class);

        setupRecyclerView();
        setupObservers();

        //Initialize click listener
        staffRegistationBinding.driverBtn.setOnClickListener(view -> {
            if (selectedTab != StaffRegTabs.Driver) {
                //reset old tab
                resetOldTabDesign();

                //then set new tab design
                staffRegistationBinding.driverBtn.setTypeface(staffRegistationBinding.driverBtn.getTypeface(), Typeface.BOLD);
                staffRegistationBinding.driverLine.setVisibility(View.VISIBLE);
                selectedTab = StaffRegTabs.Driver;

                //then set data
                listAdapter.setListItems(driverArrayList);
            }
        });

        staffRegistationBinding.sidekickBtn.setOnClickListener(view -> {
            if (selectedTab != StaffRegTabs.SideKick) {
                resetOldTabDesign();

                //then set new tab design
                staffRegistationBinding.sidekickBtn.setTypeface(staffRegistationBinding.sidekickBtn.getTypeface(), Typeface.BOLD);
                staffRegistationBinding.sidekickLine.setVisibility(View.VISIBLE);
                selectedTab = StaffRegTabs.SideKick;

                //then set data
                listAdapter.setListItems(sidekickArrayList);
            }
        });

        staffRegistationBinding.officeStaffBtn.setOnClickListener(view -> {
            if (selectedTab != StaffRegTabs.OfficeStaff) {
                resetOldTabDesign();

                //then set new tab design
                staffRegistationBinding.officeStaffBtn.setTypeface(staffRegistationBinding.officeStaffBtn.getTypeface(), Typeface.BOLD);
                staffRegistationBinding.officeStaffLine.setVisibility(View.VISIBLE);
                selectedTab = StaffRegTabs.OfficeStaff;

                //then set data
                listAdapter.setListItems(staffArrayList);
            }
        });


        staffRegistationBinding.addCommonBtn.setOnClickListener(view -> {
            if (selectedTab == StaffRegTabs.Driver) {
                Intent intent = new Intent(StaffRegistationActivity.this, DriverSideKickForm.class);
                intent.putExtra("ActivityName", String.valueOf(StaffRegTabs.Driver));
                intent.putExtra("action", "add");
                startActivity(intent);

            } else if (selectedTab == StaffRegTabs.SideKick) {
                Intent intent = new Intent(StaffRegistationActivity.this, DriverSideKickForm.class);
                intent.putExtra("ActivityName", String.valueOf(StaffRegTabs.SideKick));
                intent.putExtra("action", "add");
                startActivity(intent);
            } else {
                //staff
                Intent intent = new Intent(StaffRegistationActivity.this, OfficeStaffForm.class);
                intent.putExtra("action", "add");
                startActivity(intent);
            }
        });


        staffRegistationBinding.searchCommon.addTextChangedListener(new TextWatcher() {
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

        staffRegistationBinding.backBtn.setOnClickListener(view -> {
            finish();
        });
    }


    private void setupRecyclerView() {
        listAdapter = new ListAdapter(this, new ArrayList<>(), this);
        listAdapter.setUpdateOptionVisible(true);
        staffRegistationBinding.staffRecycler.setLayoutManager(new LinearLayoutManager(this));
        staffRegistationBinding.staffRecycler.setAdapter(listAdapter);
        staffRegistationBinding.staffRecycler.setItemAnimator(new DefaultItemAnimator());
    }

    private void setupObservers() {

        rapidLineViewModel.getAllDrivers().observe(this, drivers -> {
            driverArrayList.clear();
            for (Drivers driver : drivers) {
                driverArrayList.add(driver.getDriverName());
            }

            if (selectedTab == StaffRegTabs.Driver)
                listAdapter.setListItems(driverArrayList);
        });

        rapidLineViewModel.getAllSideKicks().observe(this, sideKicks -> {
            sidekickArrayList.clear();
            for (SideKick sideKick : sideKicks) {
                sidekickArrayList.add(sideKick.getSideKickName());
            }

            if (selectedTab == StaffRegTabs.SideKick)
                listAdapter.setListItems(sidekickArrayList);
        });

        rapidLineViewModel.getAllOfficeStaff().observe(this, officeStaffs -> {
            staffArrayList.clear();
            for (OfficeStaff staff : officeStaffs) {
                staffArrayList.add(staff.getStaffName());
            }

            if (selectedTab == StaffRegTabs.OfficeStaff)
                listAdapter.setListItems(staffArrayList);
        });

    }


    @Override
    public void onItemClick(String itemId, String action) {

        if (selectedTab == StaffRegTabs.Driver) {
            if (action.equals("delete")) {
                rapidLineViewModel.deleteDriver(itemId);
            } else {
                Intent intent = new Intent(StaffRegistationActivity.this, DriverSideKickForm.class);
                intent.putExtra("ActivityName", String.valueOf(StaffRegTabs.Driver));
                intent.putExtra("action", action);
                intent.putExtra("itemId", itemId);
                startActivity(intent);
            }
        } else if (selectedTab == StaffRegTabs.SideKick) {
            if (action.equals("delete")) {
                rapidLineViewModel.deleteSideKick(itemId);
            } else {
                Intent intent = new Intent(StaffRegistationActivity.this, DriverSideKickForm.class);
                intent.putExtra("ActivityName", String.valueOf(StaffRegTabs.SideKick));
                intent.putExtra("action", action);
                intent.putExtra("itemId", itemId);
                startActivity(intent);
            }
        } else {
            if (action.equals("delete")) {
                rapidLineViewModel.deleteOffice(itemId);
            } else {
                Intent intent = new Intent(StaffRegistationActivity.this, OfficeStaffForm.class);
                intent.putExtra("action", action);
                intent.putExtra("itemId", itemId);
                startActivity(intent);
            }
        }


    }


    private void resetOldTabDesign() {
        if (selectedTab == StaffRegTabs.Driver) {

            staffRegistationBinding.driverBtn.setTypeface(Typeface.DEFAULT);

//            staffRegistationBinding.driverBtn.setTextColor(getResources().getColor(R.color.black));
            staffRegistationBinding.driverLine.setVisibility(View.GONE);
        } else if (selectedTab == StaffRegTabs.OfficeStaff) {
            staffRegistationBinding.officeStaffBtn.setTypeface(Typeface.DEFAULT);
            staffRegistationBinding.officeStaffLine.setVisibility(View.GONE);
        } else {
            staffRegistationBinding.sidekickBtn.setTypeface(Typeface.DEFAULT);
            staffRegistationBinding.sidekickLine.setVisibility(View.GONE);
        }

    }


}
