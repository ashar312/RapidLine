package com.project.rapidline.Database.dao;

import com.project.rapidline.Database.entity.Admins;
import com.project.rapidline.Database.entity.Patri;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao()
public interface AdminDao {
    @Insert
    public long addAdmin(Admins admins);

    @Delete
    public void deleteAdmin(Admins admins);

    @Update
    public void updateAdmin(Admins admins);

    @Query("Select * from tbl_admins")
    public LiveData<List<Admins>> getAllAdmins();

    @Query("Select * from tbl_admins where id=:adminId")
    public LiveData<Admins> getAdminById(long adminId);
}
