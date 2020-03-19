package com.project.rapidline.viewmodel;

import android.app.Application;

import com.project.rapidline.Database.entity.Admins;
import com.project.rapidline.Database.entity.Agents;
import com.project.rapidline.Database.entity.Bails;
import com.project.rapidline.Database.entity.Customers;
import com.project.rapidline.Database.entity.Labours;
import com.project.rapidline.Database.entity.Patri;
import com.project.rapidline.Database.entity.Transporters;
import com.project.rapidline.repository.RapidLineRepository;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SaeedSonsViewModel extends AndroidViewModel {

    private RapidLineRepository rapidLineRepository;
//    private LiveData<List<Cities>> listAllCities;
    private LiveData<List<Admins>> listAllAdmins;
    private LiveData<List<Agents>> listAllAgents;
    private LiveData<List<Customers>> listAllCustomers;
    private LiveData<List<Transporters>> listAllTransporters;
    private LiveData<List<Labours>> listAllLabours;
    private LiveData<List<Patri>> listAllPatris;
    private LiveData<List<Bails>> listAllBails;

    public SaeedSonsViewModel(@NonNull Application application) {
        super(application);

        rapidLineRepository=new RapidLineRepository(application);
        listAllAdmins=rapidLineRepository.getAllAdmins();
//        listAllAgents=rapidLineRepository.getAllAgents();
        listAllCustomers=rapidLineRepository.getAllCustomers();
//        listAllTransporters=rapidLineRepository.getAllTransporters();
//        listAllLabours=rapidLineRepository.getAllLabours();
//        listAllPatris=rapidLineRepository.getAllPatris();
//        listAllBails=rapidLineRepository.getAllBails();

//        listAllCities=rapidLineRepository.getAllCities();
    }

//    public LiveData<List<Cities>> getListAllCities() {
//        return listAllCities;
//    }


    public LiveData<List<Admins>> getListAllAdmins() {
        return listAllAdmins;
    }


    public Admins getAdminById(long adminId){

        try {
            return rapidLineRepository.getAdminById(adminId);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<Agents>> getListAllAgents() {
        return listAllAgents;
    }



    public LiveData<List<Customers>> getListAllCustomers() {
        return listAllCustomers;
    }

    public Customers getCustById(long cusId){return rapidLineRepository.getCustomerById(cusId);}


    public LiveData<List<Transporters>> getListAllTransporters() {
        return listAllTransporters;
    }

    public LiveData<List<Labours>> getListAllLabours() {
        return listAllLabours;
    }

    public LiveData<List<Patri>> getListAllPatris() {
        return listAllPatris;
    }

    public LiveData<List<Bails>> getListAllBails() {
        return listAllBails;
    }
}
