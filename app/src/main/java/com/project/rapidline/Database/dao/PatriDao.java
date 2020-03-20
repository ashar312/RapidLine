package com.project.rapidline.Database.dao;

import com.project.rapidline.Database.entity.Labours;
import com.project.rapidline.Database.entity.Patri;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao()
public interface PatriDao {
    @Insert
    public long addPatri(Patri patri);

    @Delete
    public void deletePatri(Patri patri);

    @Update
    public void updatePatri(Patri patri);

    @Query("Select * from tbl_patri")
    public LiveData<List<Patri>> getAllPatris();

    @Query("Select * from tbl_patri where id=:patriId")
    public Patri getPatriById(long patriId);

    @Query("Delete from tbl_patri where id=:patriId")
    public void deletePatriById(long patriId);
}
