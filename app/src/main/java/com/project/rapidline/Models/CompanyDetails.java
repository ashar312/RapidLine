package com.project.rapidline.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_compDetails")
public class CompanyDetails {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "companyName")
    public String compname;

    @ColumnInfo(name = "companyNo")
    public String compno;

    @ColumnInfo(name = "email")
    public String email;

    @Ignore
    public CompanyDetails(){

    }

    public CompanyDetails(long id, String compname, String compno, String email) {
        this.id = id;
        this.compname = compname;
        this.compno = compno;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getCompname() {
        return compname;
    }

    public String getCompno() {
        return compno;
    }

    public String getEmail() {
        return email;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCompname(String compname) {
        this.compname = compname;
    }

    public void setCompno(String compno) {
        this.compno = compno;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

