package com.project.rapidline.repository;

import android.app.Application;

import com.project.rapidline.Database.RapidLineDatabase;
import com.project.rapidline.Database.dao.AdminDao;
import com.project.rapidline.Database.dao.AgentDao;
import com.project.rapidline.Database.dao.BailDao;
import com.project.rapidline.Database.dao.CompanyDao;
import com.project.rapidline.Database.dao.CustomerDao;
import com.project.rapidline.Database.dao.ItemDao;
import com.project.rapidline.Database.dao.LabourDao;
import com.project.rapidline.Database.dao.PatriDao;
import com.project.rapidline.Database.dao.TransporterDao;
import com.project.rapidline.Database.entity.Admins;
import com.project.rapidline.Database.entity.Agents;
import com.project.rapidline.Database.entity.Bails;
import com.project.rapidline.Database.entity.Customers;
import com.project.rapidline.Database.entity.KindOfItem;
import com.project.rapidline.Database.entity.Labours;
import com.project.rapidline.Database.entity.Patri;
import com.project.rapidline.Database.entity.Transporters;
import com.project.rapidline.Models.BailMinimal;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import androidx.lifecycle.LiveData;

public class RapidLineRepository {

    private Application application;
    //    private CityDao cityDao;
    private AdminDao adminDao;
    private CompanyDao companyDao;
    private AgentDao agentDao;
    private BailDao bailDao;
    private CustomerDao customerDao;
    private LabourDao labourDao;
    private PatriDao patriDao;
    private TransporterDao transporterDao;
    private ItemDao itemDao;

    public RapidLineRepository(Application application) {
        this.application = application;

        RapidLineDatabase rapidLineDatabase = RapidLineDatabase.getInstance(application);

        adminDao = rapidLineDatabase.getAdminDao();
        companyDao = rapidLineDatabase.getCompanyDao();
        agentDao = rapidLineDatabase.getAgentDao();
        bailDao = rapidLineDatabase.getBailDao();
        customerDao = rapidLineDatabase.getCustomerDao();
        labourDao = rapidLineDatabase.getLabourDao();
        patriDao = rapidLineDatabase.getPatriDao();
        transporterDao = rapidLineDatabase.getTransporterDao();
        itemDao=rapidLineDatabase.getItemDao();
    }

    //
//    public LiveData<List<Cities>> getAllCities(){return cityDao.getCities();}
    //Admins
    public LiveData<List<Admins>> getAllAdmins() {
        return adminDao.getAllAdmins();
    }

    public Admins getAdminById(final long adminId) throws ExecutionException,InterruptedException {
        Callable<Admins> adminsCallable=new Callable<Admins>() {
            @Override
            public Admins call() throws Exception {
                return adminDao.getAdminById(adminId);
            }
        };
        Future<Admins> future=Executors.newSingleThreadExecutor().submit(adminsCallable);
        return future.get();

    }

    public void addAdmin(final Admins admin) {
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                adminDao.addAdmin(admin);
            }
        });
    }

    public void updateAdmin(final Admins admin) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                adminDao.updateAdmin(admin);
            }
        });
    }

    public void deleteAdmin(final Admins admin) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                adminDao.deleteAdmin(admin);
            }
        });
    }

    //Agents
    public LiveData<List<Agents>> getAllAgents() {
        return agentDao.getAllAgents();
    }

    public Agents getAgentById(long agentId) throws ExecutionException, InterruptedException{
        Callable<Agents> callable= () -> agentDao.getAgentById(agentId);;
        Future<Agents> future=Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void addAgent(final Agents agent) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                agentDao.addAgent(agent);
            }
        });
    }

    public void updateAgent(final Agents agent) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                agentDao.updateAgent(agent);
            }
        });
    }

    public void deleteAgent(final Agents agent) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                agentDao.deleteAgent(agent);
            }
        });
    }

    //Customers
    public LiveData<List<Customers>> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    public Customers getCustomerById(long custId) throws ExecutionException, InterruptedException {
        Callable<Customers> callable= (Callable<Customers>) () -> customerDao.getCustomerById(custId);
        Future<Customers> future=Executors.newSingleThreadExecutor().submit(callable);
        return future.get();

    }

    public void addCustomer(final Customers customer) {
        BackgroundWork(() -> customerDao.addCustomer(customer));
    }

    public void updateCustomer(final Customers customer) {
        BackgroundWork(() -> customerDao.updateCustomer(customer));
    }

    public void deleteCustomer(final Customers customer) {
        BackgroundWork(() -> customerDao.deleteCustomer(customer));
    }
    public void deleteCustomerById(long cusId){
        BackgroundWork(() -> customerDao.deleteCustomerById(cusId));
    }

    private void BackgroundWork(Runnable runnable){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(runnable);
    }

    //Transpoter
    public LiveData<List<Transporters>> getAllTransporters() {
        return transporterDao.getAllTransporters();
    }

    public Transporters getTransporterById(long transpId) throws ExecutionException, InterruptedException {
        Callable<Transporters> callable= (Callable<Transporters>) () -> transporterDao.getTransporter(transpId);
        Future<Transporters> future=Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void addTransporter(final Transporters transporter) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                transporterDao.addTransporter(transporter);
            }
        });

    }

    public void updateTransporter(final Transporters transporter) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                transporterDao.updateTransporter(transporter);
            }
        });

    }

    public void deleteTransporter(final Transporters transporter) {
        BackgroundWork(() -> transporterDao.deleteTransporter(transporter));
    }
    public void deleteTransporterById(long transpId) {
        BackgroundWork(() -> {transporterDao.deleteTransporterById(transpId);});
    }

    //Labour
    public LiveData<List<Labours>> getAllLabours() {
        return labourDao.getAllLabours();
    }

    public Labours getAllLaboursById(long labourId) throws ExecutionException, InterruptedException {
        Callable<Labours> callable= () -> labourDao.getLabourById(labourId);
        Future<Labours> future=Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void addLabour(final Labours labour) {
        BackgroundWork(() -> labourDao.addLabour(labour));
    }

    public void updateLabour(final Labours labour) {
        BackgroundWork(() -> labourDao.updateLabour(labour));
    }

    public void deleteLabour(final Labours labour) {
        BackgroundWork(() -> labourDao.deleteLabour(labour));
    }
    public void deleteLabourById(long labourId){
        BackgroundWork(() -> labourDao.deleteLabourById(labourId));
    }


    //Patri
    public LiveData<List<Patri>> getAllPatris() {
        return patriDao.getAllPatris();
    }

    public Patri getPatriById(long patriId) throws ExecutionException, InterruptedException {
        Callable<Patri> callable= () -> patriDao.getPatriById(patriId);
        Future<Patri> future=Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void addPatri(final Patri patri) {
        BackgroundWork(() -> patriDao.addPatri(patri));
    }

    public void updatePatri(final Patri patri) {
        BackgroundWork(() -> patriDao.updatePatri(patri));
    }

    public void deletePatri(final Patri patri) {
        BackgroundWork(() -> patriDao.deletePatri(patri));
    }
    public void deletePatriById(long patriId){
        BackgroundWork(() -> patriDao.deletePatriById(patriId));
    }


    //Bails
    public LiveData<List<Bails>> getAllBails() {
        return bailDao.getAllBails();
    }

    public Bails getBailsById(long bailId) throws ExecutionException, InterruptedException {
        Callable<Bails> callable= () -> bailDao.getBailById(bailId);
        Future<Bails> future=Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public List<BailMinimal> getBailsrv() throws ExecutionException, InterruptedException {
        Callable<List<BailMinimal>> minimalCallable=()->bailDao.getBailsRv();
        Future<List<BailMinimal>> listFuture=Executors.newSingleThreadExecutor().submit(minimalCallable);
        return listFuture.get();
    }

    public BailMinimal getBailPrintData(long bailId) throws ExecutionException, InterruptedException{
        Callable<BailMinimal> minimalCallable=()->bailDao.getBailPrint(bailId);
        Future<BailMinimal> listFuture=Executors.newSingleThreadExecutor().submit(minimalCallable);
        return listFuture.get();
    }

    public void addBail(final Bails bail) {
        BackgroundWork(() -> bailDao.addBail(bail));
    }

    public void updateBail(final Bails bail) {
        BackgroundWork(() -> bailDao.updateBail(bail));
    }

    public void deleteBail(final Bails bail) {
        BackgroundWork(() -> bailDao.deleteBail(bail));
    }

    //Items
    public LiveData<List<KindOfItem>> getAllItems(){return itemDao.getAllItem();}

    public void addItem(KindOfItem item){BackgroundWork(()->itemDao.addItem(item));}

    public void updateItem(KindOfItem item){BackgroundWork(()->itemDao.updateItem(item));}

    public void deleteItem(KindOfItem item){BackgroundWork(()->itemDao.deleteItem(item));}

}
