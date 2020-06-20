package com.project.rapidline.Models.RapidLine;

import androidx.annotation.NonNull;

public class Supplier {
    private String supplierName;

    private String supplierNo;

    private String supplierNic;

    public Supplier(String suppName){
        this.supplierName=suppName;
    }

    public Supplier(String supplierName, String supplierNo, String supplierNic) {
        this.supplierName = supplierName;
        this.supplierNo = supplierNo;
        this.supplierNic = supplierNic;
    }

    public Supplier(){

    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    public String getSupplierNic() {
        return supplierNic;
    }

    public void setSupplierNic(String supplierNic) {
        this.supplierNic = supplierNic;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getSupplierName();
    }
}
