package com.project.rapidline.Database.dao;

import com.project.rapidline.Database.entity.Transporters;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao()
public interface TransporterDao {
    @Insert
    public long addTransporter(Transporters transporters);

    @Delete
    public void deleteTransporter(Transporters transporters);

    @Update
    public void updateTransporter(Transporters transporters);

    @Query("Select * from tbl_transporters")
    public LiveData<List<Transporters>> getAllTransporters();

    @Query("Select * from tbl_transporters where id=:transId")
    public LiveData<Transporters> getTransporter(long transId);
}
