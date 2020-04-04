package com.project.rapidline.viewmodel;

import android.app.Activity;
import android.app.Application;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SaeedSonsViewModel extends AndroidViewModel {

    private RapidLineRepository rapidLineRepository;

    public ArrayList<String> getListAllCities() {
        return listAllCities;
    }

    private ArrayList<String> listAllCities;

    private MutableLiveData<List<Admins>> listAllAdmins;
    private MutableLiveData<List<Agents>> listAllAgents;
    private MutableLiveData<List<Customers>> listAllCustomers;
    private MutableLiveData<List<Transporters>> listAllTransporters;
    private MutableLiveData<List<Labours>> listAllLabours;
    private MutableLiveData<List<Patri>> listAllPatris;
    private MutableLiveData<List<Bails>> listAllBails;
    private MutableLiveData<List<KindOfItem>> listAllItems;

    public SaeedSonsViewModel(@NonNull Application application) {
        super(application);

        rapidLineRepository = new RapidLineRepository(application);

//        listAllAdmins = rapidLineRepository.getAllAdmins();

        listAllAgents = new MutableLiveData<>();


        listAllCustomers = new MutableLiveData<>();
        listAllTransporters = new MutableLiveData<>();
        listAllLabours =new MutableLiveData<>();
        listAllPatris = new MutableLiveData<>();
        listAllBails = new MutableLiveData<>();

        listAllCities = StaticClasses.cities;

        listAllItems = new MutableLiveData<>();

    }

//    public LiveData<List<Cities>> getListAllCities() {
//        return listAllCities;
//    }

    public LiveData<List<Admins>> getListAllAdmins() {
        return listAllAdmins;
    }


//    public Admins getAdminById(long adminId) {
//
//        try {
//            return rapidLineRepository.getAdminById(adminId);
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public void addAdmin(final Admins admin) {
//        rapidLineRepository.addAdmin(admin);
//    }


    //Agents
    public LiveData<List<Agents>> getListAllAgents() {
        rapidLineRepository.getAllAgents().addSnapshotListener((queryDocumentSnapshots, e) -> {

            if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                return;
            }
            List<Agents> agentsList = new ArrayList<>();

            if (!queryDocumentSnapshots.isEmpty()) {

                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                    agentsList.add(new Agents(doc.getId(),
                            doc.getString("agentNumber"),
                            doc.getString("dealType"),
                            doc.getDouble("dealAmount"),
                            doc.getString("madeBy"),
                            doc.getDate("madeDateTime")));
                }
                listAllAgents.postValue(agentsList);

            }
        });

        return listAllAgents;
    }

    public LiveData<Agents> getAgentById(String agentId) {
        MutableLiveData<Agents> data = new MutableLiveData<>();
        rapidLineRepository.getAgentById(agentId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot doc) {
                        if (doc.exists())
                            data.postValue(new Agents(doc.getId(),
                                    doc.getString("agentNumber"),
                                    doc.getString("dealType"),
                                    doc.getDouble("dealAmount"),
                                    doc.getString("madeBy"),
                                    doc.getDate("madeDateTime")));
                    }
                });
        return data;
    }

    public LiveData<String> addAgent(final Agents agent) {

         return rapidLineRepository.addAgent(agent);
    }

    public void updateAgent(final Agents agent) {
        rapidLineRepository.updateAgent(agent);
    }

    public void deleteAgentById(String agentId) {
        rapidLineRepository.deleteAgentById(agentId);
    }

    public LiveData<List<Customers>> getListAllCustomers() {


        rapidLineRepository.getAllCustomers().addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                return;
            }
            List<Customers> customersList = new ArrayList<>();
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (doc.exists())
                        customersList.add(new Customers(doc.getId(),
                                doc.getString("companyNo"),
                                doc.getString("city"),
                                doc.getString("address"),
                                doc.getString("pocName"),
                                doc.getString("pocNo"),
                                doc.getString("madeBy"),
                                doc.getDate("madeDateTime")));
                }
                listAllCustomers.postValue(customersList);

            }
        });

        return listAllCustomers;
    }

    public LiveData<Customers> getCustById(String cusId) {
        MutableLiveData<Customers> data = new MutableLiveData<>();

        rapidLineRepository.getCustomerById(cusId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot doc) {
                        if (doc.exists())
                            data.postValue(new Customers(doc.getId(),
                                    doc.getString("companyNo"),
                                    doc.getString("city"),
                                    doc.getString("address"),
                                    doc.getString("pocName"),
                                    doc.getString("pocNo"),
                                    doc.getString("madeBy"),
                                    doc.getDate("madeDateTime")));
                    }
                });
        return data;
    }

    public LiveData<String> addCustomer(final Customers customer) {
        return rapidLineRepository.addCustomer(customer);
    }

    public void updateCustomer(final Customers customer) {
        rapidLineRepository.updateCustomer(customer);
    }

    public void deleteCustomerById(String cusId) {
        rapidLineRepository.deleteCustomerById(cusId);
    }

    //Transporters
    public LiveData<List<Transporters>> getListAllTransporters() {
        rapidLineRepository.getAllTransporters().addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                return;
            }
            List<Transporters> transportersList = new ArrayList<>();
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (doc.exists())
                        transportersList.add(new Transporters(doc.getId(),
                                doc.getString("companyNo"),
                                doc.getString("city"),
                                doc.getString("pocName"),
                                doc.getString("pocNo"),
                                doc.getString("madeBy"),
                                doc.getDate("madeDateTime")));
                }

            }
            listAllTransporters.postValue(transportersList);
        });

        return listAllTransporters;
    }

    public MutableLiveData<Transporters> getAllTransporterById(String transpId) {
        MutableLiveData<Transporters> data = new MutableLiveData<>();
        rapidLineRepository.getTransporterById(transpId).get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists())
                        data.postValue(new Transporters(doc.getId(),
                                doc.getString("companyNo"),
                                doc.getString("city"),
                                doc.getString("pocName"),
                                doc.getString("pocNo"),
                                doc.getString("madeBy"),
                                doc.getDate("madeDateTime")));
                });
        return data;
    }

    public LiveData<String> addTransporter(final Transporters transporter) {
        return rapidLineRepository.addTransporter(transporter);
    }

    public void updateTransporter(final Transporters transporter) {
        rapidLineRepository.updateTransporter(transporter);
    }

    public void deleteTransporterById(String transpId) {
        rapidLineRepository.deleteTransporterById(transpId);
    }

    //Labours
    public LiveData<List<Labours>> getListAllLabours() {
        rapidLineRepository.getAllLabours().addSnapshotListener((queryDocumentSnapshots, e) -> {

            if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                return;
            }
            List<Labours> laboursList = new ArrayList<>();
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    laboursList.add(new Labours(doc.getId(),
                            doc.getString("nic"),
                            doc.getString("phoneNumber"),
                            doc.getString("madeBy"),
                            doc.getDate("madeDateTime")));
                }
            }
            listAllLabours.postValue(laboursList);
        });


        return listAllLabours;
    }

    public LiveData<Labours>  getAllLaboursById(String labourId) {
        MutableLiveData<Labours> data=new MutableLiveData<>();
        rapidLineRepository.getAllLaboursById(labourId).get()
                .addOnSuccessListener(doc -> {
                    if(doc.exists())
                        data.postValue(new Labours(doc.getId(),
                                doc.getString("nic"),
                                doc.getString("phoneNumber"),
                                doc.getString("madeBy"),
                                doc.getDate("madeDateTime")));
                });
        return data;
    }

    public LiveData<String> addLabour(final Labours labour) {
        return rapidLineRepository.addLabour(labour);
    }

    public void updateLabour(final Labours labour) {
        rapidLineRepository.updateLabour(labour);
    }

    public void deleteLabourById(String labourId) {
        rapidLineRepository.deleteLabourById(labourId);
    }

    //Patri
    public LiveData<List<Patri>> getListAllPatris() {
        rapidLineRepository.getAllPatris().addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                return;
            }
            List<Patri> patriList = new ArrayList<>();
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    patriList.add(new Patri(doc.getId(),
                            doc.getString("nic"),
                            doc.getString("phoneNumber"),
                            doc.getString("madeBy"),
                            doc.getDate("madeDateTime")));
                }
            }
            listAllPatris.postValue(patriList);
        });
        return listAllPatris;
    }

    public LiveData<Patri> getPatriById(String patriId) {
        MutableLiveData<Patri> data=new MutableLiveData<>();
        rapidLineRepository.getPatriById(patriId).get()
                .addOnSuccessListener(doc -> {
                    if(doc.exists())
                        data.postValue(new Patri(doc.getId(),
                                doc.getString("nic"),
                                doc.getString("phoneNumber"),
                                doc.getString("madeBy"),
                                doc.getDate("madeDateTime")));
                });
        return data;
    }

    public LiveData<String> addPatri(final Patri patri) {

        return rapidLineRepository.addPatri(patri);
    }

    public void updatePatri(final Patri patri) {
        rapidLineRepository.updatePatri(patri);
    }

    public void deletePatriById(String patriId) {
        rapidLineRepository.deletePatriById(patriId);
    }


    //Bails
    public LiveData<List<Bails>> getListAllBails() {
        rapidLineRepository.getAllBails().addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                return;
            }
            List<Bails> bailsList = new ArrayList<>();
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    bailsList.add(new Bails(doc.getId(),
                            doc.getString("fromCity"),
                            doc.getString("toCity"),
                            doc.getString("kindId"),
                            doc.getDouble("qty") ,
                            doc.getString("senderId"),
                            doc.getString("receiverId"),
                            doc.getString("transporterId"),
                            doc.getString("agentId"),
                            doc.getDouble("volume"),
                            doc.getDouble("weight"),
                            doc.getString("madeBy"),
                            doc.getDate("madeDateTime"),
                            doc.getString("transportCharge"),
                            doc.getString("labourCharge"),
                            doc.getString("electric_charge"),
                            doc.getString("packingCharge"),
                            doc.getString("comments")));
                }
            }

        });


        return listAllBails;
    }

    public LiveData<Bails> getBailById(String bailId) {
        MutableLiveData<Bails> data=new MutableLiveData<>();
        rapidLineRepository.getBailsById(bailId).get()
                .addOnSuccessListener(doc -> {
                    if(doc.exists())
                        data.postValue(new Bails(doc.getId(),
                                doc.getString("fromCity"),
                                doc.getString("toCity"),
                                doc.getString("kindId"),
                                doc.getDouble("qty"),
                                doc.getString("senderId"),
                                doc.getString("receiverId"),
                                doc.getString("transporterId"),
                                doc.getString("agentId"),
                                doc.getDouble("volume"),
                                doc.getDouble("weight"),
                                doc.getString("madeBy"),
                                doc.getDate("madeDateTime"),
                                doc.getString("transportCharge"),
                                doc.getString("labourCharge"),
                                doc.getString("electric_charge"),
                                doc.getString("packingCharge"),
                                doc.getString("comments")));
                });
        return data;
    }

    public LiveData<List<Bails>> getBailDataRv() {
        MutableLiveData<List<Bails>> data=new MutableLiveData<>();
        rapidLineRepository.getAllBails().addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                return;
            }
            List<Bails> bailsList = new ArrayList<>();
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    bailsList.add(new Bails(doc.getId(),
                            doc.getString("senderId"),
                            doc.getString("receiverId"),
                            doc.getString("madeBy"),
                            doc.getDate("madeDateTime")));
                }
            }
            data.postValue(bailsList);
        });

        return data;
    }
    public LiveData<List<Bails>> getBailDataRvByDate() {
        MutableLiveData<List<Bails>> data=new MutableLiveData<>();
        rapidLineRepository.getAllBails().orderBy("madeDateTime", Query.Direction.DESCENDING)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                return;
            }
            List<Bails> bailsList = new ArrayList<>();
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    bailsList.add(new Bails(doc.getId(),
                            doc.getString("senderId"),
                            doc.getString("receiverId"),
                            doc.getString("madeBy"),
                            doc.getDate("madeDateTime")));
                }
            }
            data.postValue(bailsList);
        });

        return data;
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
        rapidLineRepository.getAllItems().addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                return;
            }
            List<KindOfItem> itemList = new ArrayList<>();
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    itemList.add(new KindOfItem(doc.getId(),
                            doc.getString("madeBy"),
                            doc.getDate("madeDateTime")));
                }

            }
            listAllItems.postValue(itemList);
        });
        return listAllItems;
    }

    public void addItem(KindOfItem item) {
        rapidLineRepository.addItem(item);
    }

    public void updateItem(KindOfItem item) {
        rapidLineRepository.updateItem(item);
    }

    public void deleteItem(String item) {
        rapidLineRepository.deleteItemById(item);
    }
}
