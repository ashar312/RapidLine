package com.project.rapidline.Database.dao;

import com.project.rapidline.Database.entity.Agents;
import com.project.rapidline.Database.entity.Customers;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


public interface AgentDao {
    @Insert
    public long addAgent(Agents agents);

    @Delete
    public void deleteAgent(Agents agents);

    @Update
    public void updateAgent(Agents agents);

    @Query("Select * from tbl_agents")
    public LiveData<List<Agents>> getAllAgents();

    @Query("Select * from tbl_agents where id=:agentId")
    public Agents getAgentById(long agentId);

    @Query("Delete from tbl_agents where id=:agentId")
    public void deleteAgentById(long agentId);

}
