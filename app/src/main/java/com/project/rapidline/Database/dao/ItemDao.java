package com.project.rapidline.Database.dao;

import com.project.rapidline.Database.entity.Customers;
import com.project.rapidline.Database.entity.KindOfItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao()
public interface ItemDao {
    @Insert
    public long addItem(KindOfItem kindOfItem);

    @Delete
    public void deleteItem(KindOfItem kindOfItem);

    @Update
    public void updateItem(KindOfItem kindOfItem);

    @Query("Select * from tbl_kindOfItem")
    public LiveData<List<KindOfItem>> getAllItem();

}
