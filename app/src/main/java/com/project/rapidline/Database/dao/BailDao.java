package com.project.rapidline.Database.dao;

import com.project.rapidline.Database.entity.Admins;
import com.project.rapidline.Database.entity.Bails;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao()
public interface BailDao {
    @Insert
    public long addBail(Bails bails);

    @Delete
    public void deleteBail(Bails bails);

    @Update
    public void updateBail(Bails bails);

    @Query("Select * from tbl_bails")
    public LiveData<List<Bails>> getAllBails();

    @Query("Select * from tbl_bails where id=:bailId")
    public Bails getBailById(long bailId);

    @Query("Delete from tbl_bails where id=:bailId")
    public void deleteBailById(long bailId);
}
