package com.project.rapidline.Database.dao;

import com.project.rapidline.Database.entity.Admins;
import com.project.rapidline.Database.entity.Bails;
import com.project.rapidline.Models.BailMinimal;

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


    @Query("Select tbl_bails.id,tbl_bails.biltyNo,tbl_bails.madeDateTime,0 as qty," +
            "(Select companyName from tbl_customers where tbl_bails.senderId=tbl_customers.id) AS sender," +
            "(Select companyName from tbl_customers where tbl_bails.receiverId=tbl_customers.id) AS receiver," +
            "(Select username from tbl_admins where tbl_bails.madeBy=tbl_admins.id) as agentName" +
            " from tbl_bails")
    public List<BailMinimal> getBailsRv();


    @Query("Select tbl_bails.id,tbl_bails.biltyNo,tbl_bails.madeDateTime,tbl_bails.fromCity," +
            "tbl_bails.toCity,tbl_bails.qty,tbl_bails.volume,tbl_bails.weight," +
            "(Select companyName from tbl_customers where tbl_bails.senderId=tbl_customers.id) AS sender," +
            "(Select companyName from tbl_customers where tbl_bails.receiverId=tbl_customers.id) AS receiver," +
            "(Select companyName from tbl_transporters where tbl_bails.transporterId=tbl_transporters.id) as transporter," +
            "(Select name from tbl_agents where tbl_bails.agentId=tbl_agents.id) as agentName," +
            "(Select name from tbl_kindOfItem where tbl_bails.kindId=tbl_kindOfItem.id) as itemName" +
            " from tbl_bails where id=:bailId")
    public BailMinimal getBailPrint(long bailId);

}
