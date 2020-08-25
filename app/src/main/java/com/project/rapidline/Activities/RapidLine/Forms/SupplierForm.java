package com.project.rapidline.Activities.RapidLine.Forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.project.rapidline.Models.RapidLine.Supplier;
import com.project.rapidline.R;
import com.project.rapidline.databinding.ActivitySupplierFormBinding;
import com.project.rapidline.utils.Responses;
import com.project.rapidline.viewmodel.RapidLine.RapidLineViewModel;
import com.project.rapidline.viewmodel.SaeedSons.SaeedSonsViewModel;

public class SupplierForm extends AppCompatActivity {

    private ActivitySupplierFormBinding supplierFormBinding;
    private SaeedSonsViewModel saeedSonsViewModel;
    private String action;
    private Supplier mSupplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supplierFormBinding= DataBindingUtil.setContentView(this,R.layout.activity_supplier_form);

        saeedSonsViewModel=ViewModelProviders.of(this).get(SaeedSonsViewModel.class);

        Bundle bundle = getIntent().getExtras();
        action = bundle.get("action").toString();

        //initialize UI
        if (action.equals("edit")) {
            //Load the data
            supplierFormBinding.supplierName.setEnabled(false);
            String id = bundle.getString("itemId");
            saeedSonsViewModel.getSupplierById(id).observe(this,supplier -> {
                mSupplier=supplier;
                loadData();
            });
        }

        supplierFormBinding.addsupplierBtn.setOnClickListener(view -> {
            if (isDataFieldsEmpty()) {
                Toast.makeText(this, "Please fill all field to continue",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (action.equals("edit")) {
                mSupplier.setSupplierName(supplierFormBinding.supplierName.getText().toString());
                mSupplier.setSupplierNo(supplierFormBinding.supplierNum.getText().toString());
                mSupplier.setSupplierNic(supplierFormBinding.supplierNic.getText().toString());


                saeedSonsViewModel.updateSupplier(mSupplier);
                Toast.makeText(this, "Sidekick updated sucessfully", Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                mSupplier=new Supplier();
                mSupplier.setSupplierName(supplierFormBinding.supplierName.getText().toString());
                mSupplier.setSupplierNo(supplierFormBinding.supplierNum.getText().toString());
                mSupplier.setSupplierNic(supplierFormBinding.supplierNic.getText().toString());


                saeedSonsViewModel.addSupplier(mSupplier).observe(this,response -> {
                    Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                    if (response.equals(Responses.SUPPLIER_ADDED))
                        finish();
                });

            }

        });

        supplierFormBinding.backBtn.setOnClickListener(view -> {
            finish();
        });
    }

    private void loadData() {
        supplierFormBinding.supplierName.setText(mSupplier.getSupplierName());
        supplierFormBinding.supplierNum.setText(mSupplier.getSupplierNo());
        supplierFormBinding.supplierNic.setText(mSupplier.getSupplierNic());
    }

    private Boolean isDataFieldsEmpty() {
        if (TextUtils.isEmpty(supplierFormBinding.supplierName.getText())
                || TextUtils.isEmpty(supplierFormBinding.supplierNum.getText())) {
            return true;
        }
        return false;
    }

}