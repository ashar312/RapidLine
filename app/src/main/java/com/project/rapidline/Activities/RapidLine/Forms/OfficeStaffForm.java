package com.project.rapidline.Activities.RapidLine.Forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.project.rapidline.Models.RapidLine.OfficeStaff;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityOfficeStaffFormBinding;
import com.project.rapidline.utils.Responses;
import com.project.rapidline.viewmodel.RapidLine.RapidLineViewModel;
import com.project.rapidline.viewmodel.SaeedSons.SaeedSonsViewModel;

public class OfficeStaffForm extends AppCompatActivity {

    private ActivityOfficeStaffFormBinding officeStaffFormBinding;
    private RapidLineViewModel rapidLineViewModel;
    private String action;
    private OfficeStaff mOfficeStaff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        officeStaffFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_office_staff_form);
        rapidLineViewModel = ViewModelProviders.of(this).get(RapidLineViewModel.class);

        Bundle bundle = getIntent().getExtras();
        action = bundle.get("action").toString();


        //initialize UI
        if (action.equals("edit")) {

            officeStaffFormBinding.staffName.setEnabled(false);
            //Load the data
            String id = bundle.getString("itemId");
            rapidLineViewModel.getOfficeStaffById(id).observe(this, staff -> {
                mOfficeStaff = staff;
                loadData();
            });
        }

        officeStaffFormBinding.addStaffBtn.setOnClickListener(view -> {
            if (isDataFieldsEmpty()) {
                Toast.makeText(this, "Please fill all field to continue",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (action.equals("edit")) {
                mOfficeStaff.setStaffName(officeStaffFormBinding.staffName.getText().toString());
                mOfficeStaff.setStaffNo(officeStaffFormBinding.staffNo.getText().toString());
                mOfficeStaff.setStaffNic(officeStaffFormBinding.staffNic.getText().toString());
                mOfficeStaff.setStaffEmail(officeStaffFormBinding.staffEmail.getText().toString());

                rapidLineViewModel.updateOffice(mOfficeStaff);

                Toast.makeText(this, "Staff updated sucessfully", Toast.LENGTH_SHORT).show();
                finish();

            } else {
                mOfficeStaff=new OfficeStaff();
                mOfficeStaff.setStaffName(officeStaffFormBinding.staffName.getText().toString());
                mOfficeStaff.setStaffNo(officeStaffFormBinding.staffNo.getText().toString());
                mOfficeStaff.setStaffNic(officeStaffFormBinding.staffNic.getText().toString());
                mOfficeStaff.setStaffEmail(officeStaffFormBinding.staffEmail.getText().toString());

                rapidLineViewModel.addOffice(mOfficeStaff).observe(this,response -> {
                    Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                    if (response.equals(Responses.STAFF_ADDED))
                        finish();
                });

            }

        });

        officeStaffFormBinding.backBtn.setOnClickListener(view -> {
            finish();
        });

    }

    private void loadData() {
        officeStaffFormBinding.staffName.setText(mOfficeStaff.getStaffName());
        officeStaffFormBinding.staffNo.setText(mOfficeStaff.getStaffNo());
        officeStaffFormBinding.staffNic.setText(mOfficeStaff.getStaffNic());
        officeStaffFormBinding.staffEmail.setText(mOfficeStaff.getStaffEmail());

    }

    private Boolean isDataFieldsEmpty() {
        if (TextUtils.isEmpty(officeStaffFormBinding.staffName.getText())
                || TextUtils.isEmpty(officeStaffFormBinding.staffNo.getText())
                || TextUtils.isEmpty(officeStaffFormBinding.staffEmail.getText())) {
            return true;
        }
        return false;
    }

}
