package com.project.rapidline.Models.RapidLine;

import com.google.firebase.firestore.Exclude;

public class OfficeStaff {

    private String staffName;

    private String staffNo;

    private String staffNic;

    private String staffEmail;

    private String key;

    public OfficeStaff(){

    }

    public OfficeStaff(String staffName, String staffNo, String staffNic, String staffEmail) {
        this.staffName = staffName;
        this.staffNo = staffNo;
        this.staffNic = staffNic;
        this.staffEmail = staffEmail;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public String getStaffNic() {
        return staffNic;
    }

    public void setStaffNic(String staffNic) {
        this.staffNic = staffNic;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }


    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

}
