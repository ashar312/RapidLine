package com.project.rapidline.Database.dao;

import com.project.rapidline.Database.entity.CompanyDetails;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao()
public interface CompanyDao {
    @Insert
    public long addCompany(CompanyDetails companyDetails);
}
