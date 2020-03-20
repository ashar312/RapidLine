package com.project.rapidline.Form;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.project.rapidline.Database.entity.Labours;
import com.project.rapidline.Database.entity.Patri;
import com.project.rapidline.Database.entity.Transporters;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityLabourPatriFormBinding;
import com.project.rapidline.viewmodel.SaeedSonsViewModel;

import java.util.Calendar;

public class LabourPatriForm extends AppCompatActivity {

    private ActivityLabourPatriFormBinding labourPatriFormBinding;
    private SaeedSonsViewModel saeedSonsViewModel;
    private String action;
    private Labours labourEditUpdate;
    private Patri patriEditUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        labourPatriFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_labour_patri_form);

        saeedSonsViewModel = ViewModelProviders.of(this).get(SaeedSonsViewModel.class);

        //Used for both labour and patri
        Bundle bundle = getIntent().getExtras();
        String activityValue = bundle.get("ListItem").toString();
        action = bundle.get("action").toString();

        if (activityValue.equals("Labour")) {
            //initialize UI
            labourPatriFormBinding.headerText.setText(activityValue);
            labourPatriFormBinding.formName.setHint("Labour Name");
            labourPatriFormBinding.formNo.setHint("Labour No");
            labourPatriFormBinding.formCnic.setHint("CNIC (Optional)");
            if (action.equals("edit")) {
                //Load the data
                long id = bundle.getLong("itemId");
                labourEditUpdate = saeedSonsViewModel.getAllLaboursById(id);
                loadLabourData();
            }

        } else {
            //initialize UI
            labourPatriFormBinding.headerText.setText(activityValue);
            labourPatriFormBinding.formName.setHint("Patri Name");
            labourPatriFormBinding.formNo.setHint("Patri No");
            labourPatriFormBinding.formCnic.setHint("CNIC (Optional)");

            if (action.equals("edit")) {
                //Load the data
                long id = bundle.getLong("itemId");
                patriEditUpdate = saeedSonsViewModel.getPatriById(id);
                loadPatriData();
            }
        }

        labourPatriFormBinding.saveBtn.setOnClickListener(view -> {
            if(isDataFieldsEmpty()){
                Toast.makeText(LabourPatriForm.this,"Please fill all field to continue",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (activityValue.equals("Labour")) {
                if (action.equals("edit")){
                    labourEditUpdate.setName(labourPatriFormBinding.formName.getText().toString());
                    labourEditUpdate.setPhoneNo(labourPatriFormBinding.formNo.getText().toString());
                    labourEditUpdate.setNic(labourPatriFormBinding.formCnic.getText().toString());

                    //TODO Save admin

                    labourEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());

                    saeedSonsViewModel.updateLabour(labourEditUpdate);
                }
                else {
                    labourEditUpdate=new Labours();
                    labourEditUpdate.setName(labourPatriFormBinding.formName.getText().toString());
                    labourEditUpdate.setPhoneNo(labourPatriFormBinding.formNo.getText().toString());
                    labourEditUpdate.setNic(labourPatriFormBinding.formCnic.getText().toString());


                    //TODO Save admin
                    labourEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());
                    labourEditUpdate.setMadeBy(1);
                    saeedSonsViewModel.addLabour(labourEditUpdate);
                }

            } else {
                if (action.equals("edit")){
                    patriEditUpdate.setName(labourPatriFormBinding.formName.getText().toString());
                    patriEditUpdate.setPhoneNo(labourPatriFormBinding.formNo.getText().toString());
                    patriEditUpdate.setNic(labourPatriFormBinding.formCnic.getText().toString());

                    //TODO Save admin

                    patriEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());
                    saeedSonsViewModel.updatePatri(patriEditUpdate);
                }
                else {
                    patriEditUpdate=new Patri();
                    patriEditUpdate.setName(labourPatriFormBinding.formName.getText().toString());
                    patriEditUpdate.setPhoneNo(labourPatriFormBinding.formNo.getText().toString());
                    patriEditUpdate.setNic(labourPatriFormBinding.formCnic.getText().toString());

                    //TODO Save admin
                    patriEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());
                    patriEditUpdate.setMadeBy(1);
                    saeedSonsViewModel.addPatri(patriEditUpdate);
                }

            }
        });


    }

    private void loadLabourData() {
        labourPatriFormBinding.formNo.setText(labourEditUpdate.getPhoneNo());
        labourPatriFormBinding.formName.setText(labourEditUpdate.getName());
        labourPatriFormBinding.formCnic.setText(labourEditUpdate.getNic());
    }

    private void loadPatriData() {
        labourPatriFormBinding.formName.setText(patriEditUpdate.getName());
        labourPatriFormBinding.formNo.setText(patriEditUpdate.getPhoneNo());
        labourPatriFormBinding.formCnic.setText(patriEditUpdate.getNic());
    }

    private Boolean isDataFieldsEmpty() {
        if (TextUtils.isEmpty(labourPatriFormBinding.formName.getText())
                || TextUtils.isEmpty(labourPatriFormBinding.formNo.getText())
                || TextUtils.isEmpty(labourPatriFormBinding.formCnic.getText())) {
            return true;
        }
        return false;
    }

}
