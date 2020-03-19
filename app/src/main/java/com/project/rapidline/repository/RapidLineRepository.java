package com.project.rapidline.repository;

import android.app.Application;

import com.project.rapidline.Database.RapidLineDatabase;
import com.project.rapidline.Database.dao.AdminDao;
import com.project.rapidline.Database.dao.AgentDao;
import com.project.rapidline.Database.dao.BailDao;
import com.project.rapidline.Database.dao.CompanyDao;
import com.project.rapidline.Database.dao.CustomerDao;
import com.project.rapidline.Database.dao.LabourDao;
import com.project.rapidline.Database.dao.PatriDao;
import com.project.rapidline.Database.dao.TransporterDao;
import com.project.rapidline.Database.entity.Admins;
import com.project.rapidline.Database.entity.Agents;
import com.project.rapidline.Database.entity.Bails;
import com.project.rapidline.Database.entity.Customers;
import com.project.rapidline.Database.entity.Labours;
import com.project.rapidline.Database.entity.Patri;
import com.project.rapidline.Database.entity.Transporters;

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
//        cityDao=rapidLineDatabase.getCityDao();
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

    public LiveData<Agents> getAgentById(long agentId) {
        return agentDao.getAgentById(agentId);
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

    public Customers getCustomerById(long custId) {
        return customerDao.getCustomerById(custId);
    }

    public void addCustomer(final Customers customer) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                customerDao.addCustomer(customer);
            }
        });
    }

    public void updateCustomer(final Customers customer) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                customerDao.updateCustomer(customer);
            }
        });
    }

    public void deleteCustomer(final Customers customer) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                customerDao.deleteCustomer(customer);
            }
        });
    }

    //Transpoter
    public LiveData<List<Transporters>> getAllTransporters() {
        return transporterDao.getAllTransporters();
    }

    public LiveData<Transporters> getAllTransporterById(long transpId) {
        return transporterDao.getTransporter(transpId);
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
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                transporterDao.deleteTransporter(transporter);
            }
        });
    }

    //Labour
    public LiveData<List<Labours>> getAllLabours() {
        return labourDao.getAllLabours();
    }

    public LiveData<Labours> getAllLaboursById(long labourId) {
        return labourDao.getLabourById(labourId);
    }

    public void addLabour(final Labours labour) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                labourDao.addLabour(labour);
            }
        });
    }

    public void updateLabour(final Labours labour) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                labourDao.updateLabour(labour);
            }
        });
    }

    public void deleteLabour(final Labours labour) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                labourDao.deleteLabour(labour);
            }
        });
    }

    //Patri
    public LiveData<List<Patri>> getAllPatris() {
        return patriDao.getAllPatris();
    }

    public LiveData<Patri> getPatriById(long patriId) {
        return patriDao.getPatriById(patriId);
    }

    public void addPatri(final Patri patri) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                patriDao.addPatri(patri);
            }
        });
    }

    public void updatePatri(final Patri patri) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                patriDao.updatePatri(patri);
            }
        });
    }

    public void deletePatri(final Patri patri) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                patriDao.deletePatri(patri);
            }
        });
    }

    //Bails
    public LiveData<List<Bails>> getAllBails() {
        return bailDao.getAllBails();
    }

    public LiveData<Bails> getBailsById(long bailId) {
        return bailDao.getBailById(bailId);
    }

    public void addBail(final Bails bail) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                bailDao.addBail(bail);
            }
        });
    }

    public void updateBail(final Bails bail) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                bailDao.updateBail(bail);
            }
        });
    }

    public void deleteBail(final Bails bail) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                bailDao.deleteBail(bail);
            }
        });
    }

}
