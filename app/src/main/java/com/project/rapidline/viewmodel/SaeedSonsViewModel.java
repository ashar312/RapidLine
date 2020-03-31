package com.project.rapidline.viewmodel;

import android.app.Application;

import com.project.rapidline.Database.entity.Admins;
import com.project.rapidline.Database.entity.Agents;
import com.project.rapidline.Database.entity.Bails;
import com.project.rapidline.Database.entity.Customers;
import com.project.rapidline.Database.entity.KindOfItem;
import com.project.rapidline.Database.entity.Labours;
import com.project.rapidline.Database.entity.Patri;
import com.project.rapidline.Database.entity.Transporters;
import com.project.rapidline.Models.BailMinimal;
import com.project.rapidline.Models.StaticClasses;
import com.project.rapidline.repository.RapidLineRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SaeedSonsViewModel extends AndroidViewModel {

    private RapidLineRepository rapidLineRepository;

    public ArrayList<String> getListAllCities() {
        return listAllCities;
    }

    private ArrayList<String> listAllCities;

    private LiveData<List<Admins>> listAllAdmins;
    private LiveData<List<Agents>> listAllAgents;
    private LiveData<List<Customers>> listAllCustomers;
    private LiveData<List<Transporters>> listAllTransporters;
    private LiveData<List<Labours>> listAllLabours;
    private LiveData<List<Patri>> listAllPatris;
    private LiveData<List<Bails>> listAllBails;
    private LiveData<List<KindOfItem>> listAllItems;

    public SaeedSonsViewModel(@NonNull Application application) {
        super(application);

        rapidLineRepository=new RapidLineRepository(application);
        listAllAdmins=rapidLineRepository.getAllAdmins();
        listAllAgents=rapidLineRepository.getAllAgents();
        listAllCustomers=rapidLineRepository.getAllCustomers();
        listAllTransporters=rapidLineRepository.getAllTransporters();
        listAllLabours=rapidLineRepository.getAllLabours();
        listAllPatris=rapidLineRepository.getAllPatris();
        listAllBails=rapidLineRepository.getAllBails();

        listAllCities= StaticClasses.cities;

        listAllItems=rapidLineRepository.getAllItems();

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
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addAdmin(final Admins admin) {
        rapidLineRepository.addAdmin(admin);
    }

    public void updateAdmin(final Admins admin) {
        rapidLineRepository.updateAdmin(admin);
    }

    public void deleteAdmin(final Admins admin) {
        rapidLineRepository.deleteAdmin(admin);
    }


    //Agents
    public LiveData<List<Agents>> getListAllAgents() {
        return listAllAgents;
    }

    public Agents getAgentById(long agentId) {
        try {
            return rapidLineRepository.getAgentById(agentId);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addAgent(final Agents agent) {
        rapidLineRepository.addAgent(agent);
    }

    public void updateAgent(final Agents agent) {
        rapidLineRepository.updateAgent(agent);
    }

    public void deleteAgent(final Agents agent) {
        rapidLineRepository.deleteAgent(agent);
    }

    public LiveData<List<Customers>> getListAllCustomers() {
        return listAllCustomers;
    }

    public Customers getCustById(long cusId){
        try {
            return rapidLineRepository.getCustomerById(cusId);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addCustomer(final Customers customer) {
        rapidLineRepository.addCustomer(customer);
    }
    public void updateCustomer(final Customers customer) {
        rapidLineRepository.updateCustomer(customer);
    }

    public void deleteCustomer(final Customers customer) {
        rapidLineRepository.deleteCustomer(customer);
    }
    public void deleteCustomerById(long cusId){
        rapidLineRepository.deleteCustomerById(cusId);
    }

    //Transporters
    public LiveData<List<Transporters>> getListAllTransporters() {
        return listAllTransporters;
    }

    public Transporters getAllTransporterById(long transpId) {
        try {
            return rapidLineRepository.getTransporterById(transpId);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addTransporter(final Transporters transporter) {
        rapidLineRepository.addTransporter(transporter);
    }

    public void updateTransporter(final Transporters transporter) {
        rapidLineRepository.updateTransporter(transporter);
    }

    public void deleteTransporter(final Transporters transporter) {
        rapidLineRepository.deleteTransporter(transporter);
    }

    public void deleteTransporterById(long transpId) {
     rapidLineRepository.deleteTransporterById(transpId);
    }

    //Labours
    public LiveData<List<Labours>> getListAllLabours() {
        return listAllLabours;
    }

    public Labours getAllLaboursById(long labourId) {
        try {
            return rapidLineRepository.getAllLaboursById(labourId);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addLabour(final Labours labour) {
        rapidLineRepository.addLabour(labour);
    }

    public void updateLabour(final Labours labour) {
        rapidLineRepository.updateLabour(labour);
    }

    public void deleteLabour(final Labours labour) {
        rapidLineRepository.deleteLabour(labour);
    }
    public void deleteLabourById(long labourId) {
        rapidLineRepository.deleteLabourById(labourId);
    }

    //Patri
    public LiveData<List<Patri>> getListAllPatris() {
        return listAllPatris;
    }

    public Patri getPatriById(long patriId) {
        try {
            return rapidLineRepository.getPatriById(patriId);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addPatri(final Patri patri) {
        rapidLineRepository.addPatri(patri);
    }

    public void updatePatri(final Patri patri) {
        rapidLineRepository.updatePatri(patri);
    }

    public void deletePatri(final Patri patri) {
        rapidLineRepository.deletePatri(patri);
    }
    public void deletePatriById(long patriId) {
        rapidLineRepository.deletePatriById(patriId);
    }


    //Bails
    public LiveData<List<Bails>> getListAllBails() {
        return listAllBails;
    }

    public Bails getBailById(long bailId) {
        try {
            return rapidLineRepository.getBailsById(bailId);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<BailMinimal> data(){
        try {
            return rapidLineRepository.getBailsrv();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public BailMinimal getBailPrintData(long bailId){
        try {
            return rapidLineRepository.getBailPrintData(bailId);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addBail(final Bails bail) {
     rapidLineRepository.addBail(bail);
    }

    public void updateBail(final Bails bail) {
        rapidLineRepository.updateBail(bail);
    }

    public void deleteBail(final Bails bail) {
        rapidLineRepository.deleteBail(bail);
    }

    //Items

    public LiveData<List<KindOfItem>> getListAllItems() {
        return listAllItems;
    }

    public void addItem(KindOfItem item){rapidLineRepository.addItem(item);}

    public void updateItem(KindOfItem item){rapidLineRepository.updateItem(item);}

    public void deleteItem(KindOfItem item){rapidLineRepository.deleteItem(item);}
}
