package com.project.rapidline.Activities.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityAdminRightsBinding;
import com.project.rapidline.viewmodel.SaeedSons.AdminViewModel;
import com.suke.widget.SwitchButton;

public class AdminRightsActivity extends AppCompatActivity {

    private ActivityAdminRightsBinding adminRightsBinding;
    private AdminViewModel adminViewModel;
    private String adminId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminRightsBinding= DataBindingUtil.setContentView(this, R.layout.activity_admin_rights);
        adminViewModel = ViewModelProviders.of(this).get(AdminViewModel.class);

        Bundle bundle = getIntent().getExtras();
        adminId=bundle.get("adminId").toString();

        adminViewModel.getAdminPermissionsById(adminId).observe(this,permissions -> {
            adminRightsBinding.addBailsSwitch.setChecked(permissions.isAddBail());
            adminRightsBinding.editBailsSwitch.setChecked(permissions.isUpdateBail());
            adminRightsBinding.deleteBailsSwitch.setChecked(permissions.isDeleteBail());
        });


        adminRightsBinding.addBailsSwitch.setOnCheckedChangeListener((view, isChecked) -> {
            adminViewModel.updateAdminPerm(adminId,"addBail",isChecked);
        });

        adminRightsBinding.editBailsSwitch.setOnCheckedChangeListener((view, isChecked) -> {
            adminViewModel.updateAdminPerm(adminId,"updateBail",isChecked);
        });

        adminRightsBinding.deleteBailsSwitch.setOnCheckedChangeListener((view, isChecked) -> {
            adminViewModel.updateAdminPerm(adminId,"deleteBail",isChecked);
        });

//        boolean add_bail_state=this.getSharedPreferences("MyPref",Context.MODE_PRIVATE).getBoolean("add_bail_perm",true);
//        adminRightsBinding.addBailsSwitch.setChecked(add_bail_state);
//        adminRightsBinding.addBailsSwitch.setOnCheckedChangeListener((view, isChecked) -> {
//            addCurrentStateToPref(isChecked,"add_bail_perm");
//        });
//
//        boolean edit_bail_state=this.getSharedPreferences("MyPref",Context.MODE_PRIVATE).getBoolean("edit_bail_perm",false);
//        adminRightsBinding.editBailsSwitch.setChecked(edit_bail_state);
//        adminRightsBinding.editBailsSwitch.setOnCheckedChangeListener((view, isChecked) -> {
//            addCurrentStateToPref(isChecked,"edit_bail_perm");
//        });
//
//        boolean delete_bail_state=this.getSharedPreferences("MyPref",Context.MODE_PRIVATE).getBoolean("delete_bail_perm",false);
//        adminRightsBinding.deleteBailsSwitch.setChecked(delete_bail_state);
//        adminRightsBinding.deleteBailsSwitch.setOnCheckedChangeListener((view, isChecked) -> {
//            addCurrentStateToPref(isChecked,"delete_bail_perm");
//        });

    }

//    public void addCurrentStateToPref(boolean state,String permision) {
//        SharedPreferences.Editor editor = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE).edit();
//        editor.putBoolean(permision, state);
//        editor.apply(); //apply writes the data in background process
//    }

}
