package com.project.rapidline.Database.dao;

import com.project.rapidline.Database.entity.Agents;
import com.project.rapidline.Database.entity.Labours;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao()
public interface LabourDao {
    @Insert
    public long addLabour(Labours labours);

    @Delete
    public void deleteLabour(Labours labours);

    @Update
    public void updateLabour(Labours labours);

    @Query("Select * from tbl_labours")
    public LiveData<List<Labours>> getAllLabours();

    @Query("Select * from tbl_labours where id=:labourId")
    public LiveData<Labours> getLabourById(long labourId);
}
