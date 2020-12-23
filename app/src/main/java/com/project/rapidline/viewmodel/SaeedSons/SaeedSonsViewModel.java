package com.project.rapidline.viewmodel.SaeedSons;

import android.app.Application;

import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Source;
import com.project.rapidline.Models.Admins;
import com.project.rapidline.Models.RapidLine.Supplier;
import com.project.rapidline.Models.SaeedSons.Agents;
import com.project.rapidline.Models.SaeedSons.Bails;
import com.project.rapidline.Models.SaeedSons.Cities;
import com.project.rapidline.Models.SaeedSons.Customers;
import com.project.rapidline.Models.SaeedSons.KindOfItem;
import com.project.rapidline.Models.SaeedSons.Labours;
import com.project.rapidline.Models.SaeedSons.Patri;
import com.project.rapidline.Models.SaeedSons.Transporters;
import com.project.rapidline.repository.SaeedSonsRepository;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SaeedSonsViewModel extends AndroidViewModel {

    private SaeedSonsRepository saeedSonsRepository;


    private MutableLiveData<List<Cities>> listAllCities;

    private MutableLiveData<List<Admins>> listAllAdmins;
    private MutableLiveData<List<Agents>> listAllAgents;
    private MutableLiveData<List<Customers>> listAllCustomers;
    private MutableLiveData<List<Transporters>> listAllTransporters;
    private MutableLiveData<List<Labours>> listAllLabours;
    private MutableLiveData<List<Patri>> listAllPatris;
    private MutableLiveData<List<Bails>> listAllBails;
    private MutableLiveData<List<KindOfItem>> listAllItems;
    private MutableLiveData<List<Supplier>> listAllSupplier;

    public SaeedSonsViewModel(@NonNull Application application) {
        super(application);

        saeedSonsRepository = new SaeedSonsRepository(application);

//        listAllAdmins = rapidLineRepository.getAllAdmins();

        listAllAgents = new MutableLiveData<>();


        listAllCustomers = new MutableLiveData<>();
        listAllTransporters = new MutableLiveData<>();
        listAllLabours = new MutableLiveData<>();
        listAllPatris = new MutableLiveData<>();
        listAllBails = new MutableLiveData<>();

        listAllCities = new MutableLiveData<>();

        listAllItems = new MutableLiveData<>();
        listAllAdmins = new MutableLiveData<>();
        listAllSupplier = new MutableLiveData<>();
    }

//    public LiveData<List<Cities>> getListAllCities() {
//        return listAllCities;
//    }

    public LiveData<List<Admins>> getListAllAdmins() {
        saeedSonsRepository.getAdmins().addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                return;
            }

            List<Admins> adminList = new ArrayList<>();
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    Admins admins = doc.toObject(Admins.class);
                    admins.setKey(doc.getId());
                    adminList.add(admins);
                }

            }
            listAllAdmins.postValue(adminList);
        });

        return listAllAdmins;
    }

    public void deleteAdminById(String key) {
        saeedSonsRepository.deleteAdminById(key);
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
        saeedSonsRepository.getAllAgents().addSnapshotListener((queryDocumentSnapshots, e) -> {

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
        saeedSonsRepository.getAgentById(agentId).get(Source.DEFAULT)
                .addOnSuccessListener(doc -> {
                    if (doc.exists())
                        data.postValue(new Agents(doc.getId(),
                                doc.getString("agentNumber"),
                                doc.getString("dealType"),
                                doc.getDouble("dealAmount"),
                                doc.getString("madeBy"),
                                doc.getDate("madeDateTime")));
                });
        return data;
    }

    public LiveData<String> addAgent(final Agents agent) {

        return saeedSonsRepository.addAgent(agent);
    }

    public void updateAgent(final Agents agent) {
        saeedSonsRepository.updateAgent(agent);
    }

    public void deleteAgentById(String agentId) {
        saeedSonsRepository.deleteAgentById(agentId);
    }

    public LiveData<List<Customers>> getListAllCustomers() {


        saeedSonsRepository.getAllCustomers().addSnapshotListener((queryDocumentSnapshots, e) -> {
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

        saeedSonsRepository.getCustomerById(cusId).get(Source.DEFAULT)
                .addOnSuccessListener(doc -> {
                    if (doc.exists())
                        data.postValue(new Customers(doc.getId(),
                                doc.getString("companyNo"),
                                doc.getString("city"),
                                doc.getString("address"),
                                doc.getString("pocName"),
                                doc.getString("pocNo"),
                                doc.getString("madeBy"),
                                doc.getDate("madeDateTime")));
                });
        return data;
    }

    public LiveData<String> addCustomer(final Customers customer) {
        return saeedSonsRepository.addCustomer(customer);
    }

    public void updateCustomer(final Customers customer) {
        saeedSonsRepository.updateCustomer(customer);
    }

    public void deleteCustomerById(String cusId) {
        saeedSonsRepository.deleteCustomerById(cusId);
    }

    //Transporters
    public LiveData<List<Transporters>> getListAllTransporters() {
        saeedSonsRepository.getAllTransporters().addSnapshotListener((queryDocumentSnapshots, e) -> {
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
        saeedSonsRepository.getTransporterById(transpId).get(Source.DEFAULT)
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
        return saeedSonsRepository.addTransporter(transporter);
    }

    public void updateTransporter(final Transporters transporter) {
        saeedSonsRepository.updateTransporter(transporter);
    }

    public void deleteTransporterById(String transpId) {
        saeedSonsRepository.deleteTransporterById(transpId);
    }

    //Labours
    public LiveData<List<Labours>> getListAllLabours() {
        MutableLiveData<List<Labours>> data = new MutableLiveData<>();
        saeedSonsRepository.getAllLabours().addSnapshotListener((queryDocumentSnapshots, e) -> {

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
            data.postValue(laboursList);
        });


        return data;
    }

    public LiveData<Labours> getAllLaboursById(String labourId) {
        MutableLiveData<Labours> data = new MutableLiveData<>();
        saeedSonsRepository.getAllLaboursById(labourId).get(Source.DEFAULT)
                .addOnSuccessListener(doc -> {
                    if (doc.exists())
                        data.postValue(new Labours(doc.getId(),
                                doc.getString("nic"),
                                doc.getString("phoneNumber"),
                                doc.getString("madeBy"),
                                doc.getDate("madeDateTime")));
                });
        return data;
    }

    public LiveData<String> addLabour(final Labours labour) {
        return saeedSonsRepository.addLabour(labour);
    }

    public void updateLabour(final Labours labour) {
        saeedSonsRepository.updateLabour(labour);
    }

    public void deleteLabourById(String labourId) {
        saeedSonsRepository.deleteLabourById(labourId);
    }

    //Patri
    public LiveData<List<Patri>> getListAllPatris() {
        saeedSonsRepository.getAllPatris().addSnapshotListener((queryDocumentSnapshots, e) -> {
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
        MutableLiveData<Patri> data = new MutableLiveData<>();
        saeedSonsRepository.getPatriById(patriId).get(Source.DEFAULT)
                .addOnSuccessListener(doc -> {
                    if (doc.exists())
                        data.postValue(new Patri(doc.getId(),
                                doc.getString("nic"),
                                doc.getString("phoneNumber"),
                                doc.getString("madeBy"),
                                doc.getDate("madeDateTime")));
                });
        return data;
    }

    public LiveData<String> addPatri(final Patri patri) {

        return saeedSonsRepository.addPatri(patri);
    }

    public void updatePatri(final Patri patri) {
        saeedSonsRepository.updatePatri(patri);
    }

    public void deletePatriById(String patriId) {
        saeedSonsRepository.deletePatriById(patriId);
    }


    //Bails
    public LiveData<List<Bails>> getListAllBails() {
        saeedSonsRepository.getAllBails().addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                return;
            }
            List<Bails> bailsList = new ArrayList<>();
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (!doc.getId().equals("BailCounter")) {
                        bailsList.add(new Bails(doc.getId(),
                                doc.getString("fromCity"),
                                doc.getString("toCity"),
                                doc.getString("kindId"),
                                doc.getDouble("qty"),
                                doc.getString("senderId"),
                                doc.getString("receiverId"),
                                doc.getString("transporterId"),
                                doc.getString("agentId"),
                                doc.getString("madeBy"),
                                doc.getDate("madeDateTime"),
                                doc.getString("transportCharge"),
                                doc.getString("labourCharge"),
                                doc.getString("electric_charge"),
                                doc.getString("packingCharge"),
                                doc.getString("comments"),
                                doc.getBoolean("shipped")));
                    }
                }
            }

        });


        return listAllBails;
    }

    public LiveData<Bails> getBailById(String bailId) {
        MutableLiveData<Bails> data = new MutableLiveData<>();
        saeedSonsRepository.getBailsById(bailId).get(Source.DEFAULT)
                .addOnSuccessListener(doc -> {
                    if (doc.exists())
                        data.postValue(new Bails(doc.getId(),
                                doc.getString("fromCity"),
                                doc.getString("toCity"),
                                doc.getString("kindId"),
                                doc.getDouble("qty"),
                                doc.getString("senderId"),
                                doc.getString("receiverId"),
                                doc.getString("transporterId"),
                                doc.getString("agentId"),
                                doc.getString("madeBy"),
                                doc.getDate("madeDateTime"),
                                doc.getString("transportCharge"),
                                doc.getString("labourCharge"),
                                doc.getString("electric_charge"),
                                doc.getString("packingCharge"),
                                doc.getString("comments"),
                                doc.getBoolean("shipped")));
                });
        return data;
    }


    public LiveData<List<Bails>> getBailDataRv() {
        MutableLiveData<List<Bails>> data = new MutableLiveData<>();
        saeedSonsRepository.getAllBails().addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                return;
            }
            List<Bails> bailsList = new ArrayList<>();
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (!doc.getId().equals("BailCounter")) {
                        bailsList.add(new Bails(doc.getId(),
                                doc.getString("senderId"),
                                doc.getString("receiverId"),
                                doc.getString("madeBy"),
                                doc.getDate("madeDateTime"),
                                doc.getString("fromCity"),
                                doc.getString("toCity")));
                    }

                }
            }
            data.postValue(bailsList);
        });

        return data;
    }

    public LiveData<List<Bails>> getBailDataRvByDate() {
        MutableLiveData<List<Bails>> data = new MutableLiveData<>();
        saeedSonsRepository.getAllBails().orderBy("madeDateTime", Query.Direction.DESCENDING)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }
                    List<Bails> bailsList = new ArrayList<>();
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            if (!doc.getId().equals("BailCounter")) {
                                bailsList.add(new Bails(doc.getId(),
                                        doc.getString("senderId"),
                                        doc.getString("receiverId"),
                                        doc.getString("madeBy"),
                                        doc.getDate("madeDateTime"),
                                        doc.getString("fromCity"),
                                        doc.getString("toCity")));
                            }
                        }

                    }
                    data.postValue(bailsList);
                });

        return data;
    }

    public void addBail(final Bails bail) {
        saeedSonsRepository.addBail(bail);
    }

    public void updateBail(final Bails bail) {
        saeedSonsRepository.updateBail(bail);
    }

    public void deleteBail(final String bailId) {
        saeedSonsRepository.deleteBail(bailId);
    }

    //Items

    public LiveData<List<KindOfItem>> getListAllItems() {
        saeedSonsRepository.getAllItems().addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                return;
            }
            List<KindOfItem> itemList = new ArrayList<>();
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    KindOfItem item = doc.toObject(KindOfItem.class);
                    item.setKey(doc.getId());
                    itemList.add(item);
                }

            }
            listAllItems.postValue(itemList);
        });
        return listAllItems;
    }

    public LiveData<String> addItem(KindOfItem item) {
        return saeedSonsRepository.addItem(item);
    }

    public void updateItem(String key, String value) {
        saeedSonsRepository.updateItem(key, value);
    }

    public void deleteItem(String key) {
        saeedSonsRepository.deleteItemById(key);
    }




    //Supplier
    public LiveData<List<Supplier>> getListAllSuppliers() {
        saeedSonsRepository.getAllSuppliers()
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }
                    List<Supplier> supplierList = new ArrayList<>();
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            if (doc.exists())
                                supplierList.add(doc.toObject(Supplier.class));
                        }
                        listAllSupplier.postValue(supplierList);

                    }
                });

        return listAllSupplier;
    }

    public LiveData<Supplier> getSupplierById(String supplierId) {
        MutableLiveData<Supplier> data = new MutableLiveData<>();

        saeedSonsRepository.getSupplierById(supplierId).get(Source.DEFAULT)
                .addOnSuccessListener(doc -> {
                    if (doc.exists())
                        data.postValue(doc.toObject(Supplier.class));
                });

        return data;
    }

    public LiveData<String> addSupplier(final Supplier supplier) {
        return saeedSonsRepository.addSupplier(supplier);
    }

    public void updateSupplier(final Supplier supplier) {
        saeedSonsRepository.updateSupplier(supplier);
    }

    public void deleteSupplierById(String supplierId) {
        saeedSonsRepository.deleteSupplierById(supplierId);
    }


}
