package com.project.rapidline.Database.dao;

import com.project.rapidline.Database.entity.Customers;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
@Dao()
public interface CustomerDao {
    @Insert
    public long addCustomer(Customers customers);

    @Delete
    public void deleteCustomer(Customers customers);

    @Update
    public void updateCustomer(Customers customers);

    @Query("Select * from tbl_customers")
    public LiveData<List<Customers>> getAllCustomers();

    @Query("Select * from tbl_customers where id=:custId")
    public Customers getCustomerById(long custId);

}
