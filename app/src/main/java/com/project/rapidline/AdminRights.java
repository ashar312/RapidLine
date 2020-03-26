package com.project.rapidline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.project.rapidline.databinding.ActivityAdminRightsBinding;
import com.suke.widget.SwitchButton;

public class AdminRights extends AppCompatActivity {

    private ActivityAdminRightsBinding adminRightsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminRightsBinding= DataBindingUtil.setContentView(this,R.layout.activity_admin_rights);

        boolean add_bail_state=getApplicationContext().getSharedPreferences("MyPref",0).getBoolean("add_bail_perm",false);
        adminRightsBinding.addBailsSwitch.setChecked(add_bail_state);
        adminRightsBinding.addBailsSwitch.setOnCheckedChangeListener((view, isChecked) -> {
            addCurrentStateToPref(isChecked,"add_bail_perm");
        });

        boolean edit_bail_state=getApplicationContext().getSharedPreferences("MyPref",0).getBoolean("edit_bail_perm",false);
        adminRightsBinding.editBailsSwitch.setChecked(edit_bail_state);
        adminRightsBinding.editBailsSwitch.setOnCheckedChangeListener((view, isChecked) -> {
            addCurrentStateToPref(isChecked,"edit_bail_perm");
        });

        boolean delete_bail_state=getApplicationContext().getSharedPreferences("MyPref",0).getBoolean("delete_bail_perm",false);
        adminRightsBinding.deleteBailsSwitch.setChecked(delete_bail_state);
        adminRightsBinding.deleteBailsSwitch.setOnCheckedChangeListener((view, isChecked) -> {
            addCurrentStateToPref(isChecked,"delete_bail_perm");
        });

    }

    public void addCurrentStateToPref(boolean state,String permision) {
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("MyPref", 0).edit();
        editor.putBoolean(permision, state);
        editor.apply(); //apply writes the data in background process
    }
}
