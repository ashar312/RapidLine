package com.project.rapidline.Form;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RadioButton;
import android.widget.Toast;

import com.project.rapidline.Database.entity.Agents;
import com.project.rapidline.utils.Responses;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivityAgentFormBinding;
import com.project.rapidline.viewmodel.SaeedSonsViewModel;

import java.util.Calendar;

public class AgentForm extends AppCompatActivity {

    private ActivityAgentFormBinding agentFormBinding;
    private SaeedSonsViewModel saeedSonsViewModel;
    private String action;
    private Agents agentEditUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        agentFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_agent_form);

        saeedSonsViewModel = ViewModelProviders.of(this).get(SaeedSonsViewModel.class);

        Bundle bundle = getIntent().getExtras();
        action = bundle.get("action").toString();

        //initialize UI
        if (action.equals("edit")) {
            //Load the data
            agentFormBinding.agentName.setEnabled(false);
            String id = bundle.getString("itemId");
//            agentEditUpdate = saeedSonsViewModel.getAgentById(id);
            saeedSonsViewModel.getAgentById(id).observe(this, agents -> {
                agentEditUpdate = agents;
                loadData();
            });
        }

        agentFormBinding.saveBtn.setOnClickListener(view -> {
            RadioButton radioButton = findViewById(agentFormBinding.agentRadio.getCheckedRadioButtonId());
            ;

            if (radioButton.getText().toString().equals("Weight")) {
                if (TextUtils.isEmpty(agentFormBinding.weightTxt.getText())) {
                    Toast.makeText(AgentForm.this, "Please fill all field to continue",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                if (TextUtils.isEmpty(agentFormBinding.quanTxt.getText())) {
                    Toast.makeText(AgentForm.this, "Please fill all field to continue",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (TextUtils.isEmpty(agentFormBinding.agentName.getText()) || TextUtils.isEmpty(agentFormBinding.agentNo.getText())) {

                Toast.makeText(AgentForm.this, "Please fill all field to continue",
                        Toast.LENGTH_SHORT).show();
                return;
            }


            if (action.equals("edit")) {
                agentEditUpdate.setAgentName(agentFormBinding.agentName.getText().toString());
                agentEditUpdate.setAgentNumber(agentFormBinding.agentNo.getText().toString());

                if (radioButton.getText().toString().equals("Weight")) {
                    agentEditUpdate.setDealType(radioButton.getText().toString());
                    agentEditUpdate.setDealAmount(Double.valueOf(agentFormBinding.weightTxt.getText().toString()));
                } else {
                    agentEditUpdate.setDealType(radioButton.getText().toString());
                    agentEditUpdate.setDealAmount(Double.valueOf(agentFormBinding.quanTxt.getText().toString()));
                }

                //TODO save admin
                agentEditUpdate.setMadeBy(getAdminName());
                agentEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());

                saeedSonsViewModel.updateAgent(agentEditUpdate);
                Toast.makeText(this, "Agent updated sucessfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                agentEditUpdate = new Agents();
                agentEditUpdate.setAgentName(agentFormBinding.agentName.getText().toString());
                agentEditUpdate.setAgentNumber(agentFormBinding.agentNo.getText().toString());


                if (radioButton.getText().toString().equals("Weight")) {
                    agentEditUpdate.setDealType(radioButton.getText().toString());
                    agentEditUpdate.setDealAmount(Double.valueOf(agentFormBinding.weightTxt.getText().toString()));

                } else {
                    agentEditUpdate.setDealType(radioButton.getText().toString());
                    agentEditUpdate.setDealAmount(Double.valueOf(agentFormBinding.quanTxt.getText().toString()));

                }

                agentEditUpdate.setMadeDateTime(Calendar.getInstance().getTime());
                agentEditUpdate.setMadeBy(getAdminName());

                saeedSonsViewModel.addAgent(agentEditUpdate).observe(this, response -> {
                    Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                    if (response.equals(Responses.AGENT_ADDED))
                        finish();
                });

            }

        });


    }

    private void loadData() {
        agentFormBinding.agentName.setText(agentEditUpdate.getAgentName());
        agentFormBinding.agentNo.setText(agentEditUpdate.getAgentNumber());

        if (agentEditUpdate.getDealType().equals("Weight")) {
            agentFormBinding.weightBtn.setChecked(true);
            agentFormBinding.weightTxt.setText(agentEditUpdate.getDealAmount().toString());
        } else {
            agentFormBinding.quanBtn.setChecked(true);
            agentFormBinding.quanTxt.setText(agentEditUpdate.getDealAmount().toString());
        }

    }

    private String getAdminName() {
        return getApplicationContext().getSharedPreferences("LoginPref", 0).getString("adminName", "");
    }
}
