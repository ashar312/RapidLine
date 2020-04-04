package com.project.rapidline.repository;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.project.rapidline.Database.dao.AdminDao;
import com.project.rapidline.Database.dao.BailDao;
import com.project.rapidline.Database.entity.Admins;
import com.project.rapidline.Database.entity.Agents;
import com.project.rapidline.Database.entity.Bails;
import com.project.rapidline.Database.entity.Customers;
import com.project.rapidline.Database.entity.KindOfItem;
import com.project.rapidline.Database.entity.Labours;
import com.project.rapidline.Database.entity.Patri;
import com.project.rapidline.Database.entity.Transporters;
import com.project.rapidline.Models.BailMinimal;
import com.project.rapidline.Models.Responses;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class RapidLineRepository {

    private static final String TAG = "RapidLineRepository";

    private Application application;
    //    private CityDao cityDao;
    private AdminDao adminDao;

    private BailDao bailDao;

    protected FirebaseFirestore firestore;
    private DocumentReference saeedSonReference;

    private final String Database = "Database";
    private final String SaeedSonDbName = "SaeedSonsDB";
    private final String AdminTableName = "Admins";
    private final String AgentTableName = "Agents";
    private final String CustomerTableName = "Customers";
    private final String TransporterTableName = "Transporters";
    private final String PatriTableName = "Patris";
    private final String LabourTableName = "Labours";
    private final String BailTableName = "Bails";
    private final String KindTableName = "KindOfItem";

    public RapidLineRepository(Application application) {
        this.application = application;

        firestore = FirebaseFirestore.getInstance();
        saeedSonReference = firestore.collection(Database).document(SaeedSonDbName);


    }

    //
//    public LiveData<List<Cities>> getAllCities(){return cityDao.getCities();}
    //Admins
    public LiveData<List<Admins>> getAllAdmins() {
//        List<Admins> adminsList=new ArrayList<>();
//        firestore.collection(Database).document(SaeedSonDbName).collection(AdminTableName)
//                .addSnapshotListener((documentSnapshot, e) -> {
//                    if (e != null) {
////                        Log.w(TAG, "Listen failed.", e);
//                        return;
//                    }
//
//                    if(!documentSnapshot.isEmpty()){
//                        for (QueryDocumentSnapshot doc : documentSnapshot) {
//                            adminsList.add(new Admins())
//                        }
//                });
//
//        MutableLiveData<List<Admins>> mutableLiveData=new MutableLiveData<>();
//        mutableLiveData.postValue(adminDao.getAllAdmins().getValue());
        return adminDao.getAllAdmins();
    }

    public CollectionReference getAdmins() {
        return saeedSonReference.collection(AdminTableName);
    }

    public MutableLiveData<String> addAdmin(final Admins admin) {
        MutableLiveData<String> response = new MutableLiveData<>();

        saeedSonReference.collection(AdminTableName).document(admin.getUsername())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            //username already present
                            response.postValue(Responses.ADMIN_EXISTS);
                        } else {
                            saeedSonReference.collection(AdminTableName)
                                    .document(admin.getUsername())
                                    .set(admin.toHashMap());
                            response.postValue(Responses.ADMIN_ADDED);
                        }
                    }
                });
//
//        saeedSonReference.collection(AdminTableName).whereEqualTo("username", admin.getUsername())
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        if (task.getResult().isEmpty()) {
//                            saeedSonReference.collection(AdminTableName)
//                                    .document(admin.getUsername())
//                                    .set(admin.toHashMap());
//                            response.postValue(Responses.ADMIN_ADDED);
//                            return;
//                        }
//
//                        for (QueryDocumentSnapshot users : task.getResult()) {
//                            if (admin.getUsername().equals(users.getString("username"))) {
//                                response.postValue(Responses.ADMIN_EXISTS);
//                            }
//                        }
//                    }
//                });

        return response;
    }

    //Agents
    public CollectionReference getAllAgents() {
        return saeedSonReference.collection(AgentTableName);
    }

    public DocumentReference getAgentById(String agentId) {
        return saeedSonReference.collection(AgentTableName).document(agentId);
    }

    public LiveData<String> addAgent(final Agents agent) {
        MutableLiveData<String> response = new MutableLiveData<>();
        saeedSonReference.collection(AgentTableName).document(agent.getAgentName())
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    response.postValue(Responses.AGENT_EXISTS);
                } else {
                    saeedSonReference.collection(AgentTableName)
                            .document(agent.getAgentName()).set(agent.toHashMap());
                    response.postValue(Responses.AGENT_ADDED);
                }
            }
        });

        return response;
    }

    public void updateAgent(final Agents agent) {
        saeedSonReference.collection(AgentTableName)
                .document(agent.getAgentName()).update(agent.toHashMap());
    }

    public void deleteAgentById(String agentId) {
        saeedSonReference.collection(AgentTableName)
                .document(agentId).delete();
    }

    //Customers
    public CollectionReference getAllCustomers() {

        return saeedSonReference.collection(CustomerTableName);

    }

    public DocumentReference getCustomerById(String custId) {
        return saeedSonReference.collection(CustomerTableName).document(custId);

    }

    public LiveData<String> addCustomer(final Customers customer) {

        MutableLiveData<String> response = new MutableLiveData<>();
        saeedSonReference.collection(CustomerTableName).document(customer.getCompanyName())
                .get().
                addOnSuccessListener(documentSnapshot -> {
                    Log.d(TAG, "sucess: ");
                    response.postValue(Responses.CUSTOMER_EXISTS);
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "failed: ");
                    saeedSonReference.collection(CustomerTableName).
                            document(customer.getCompanyName()).set(customer.toHashMap());
                    response.postValue(Responses.CUSTOMER_ADDED);
                });

        return response;
    }

    public void updateCustomer(final Customers customer) {
        saeedSonReference.collection(CustomerTableName).
                document(customer.getCompanyName()).update(customer.toHashMap());
    }

    public void deleteCustomerById(String cusId) {
        saeedSonReference.collection(CustomerTableName).
                document(cusId).delete();
    }

    private void BackgroundWork(Runnable runnable) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(runnable);
    }

    //Transpoter
    public CollectionReference getAllTransporters() {
        return saeedSonReference.collection(TransporterTableName);

    }

    public DocumentReference getTransporterById(String transpId) {
        return saeedSonReference.collection(TransporterTableName).document(transpId);
    }

    public LiveData<String> addTransporter(final Transporters transporter) {

        MutableLiveData<String> response = new MutableLiveData<>();
        saeedSonReference.collection(TransporterTableName).document(transporter.getCompanyName())
                .get().
                addOnSuccessListener(documentSnapshot -> {
                    response.postValue(Responses.TRANSPORTER_EXISTS);
                })
                .addOnFailureListener(e -> {
                    saeedSonReference.collection(TransporterTableName)
                            .document(transporter.getCompanyName()).set(transporter.toHashMap());
                    response.postValue(Responses.TRANSPORTER_ADDED);
                });

        return response;
    }

    public void updateTransporter(final Transporters transporter) {
        saeedSonReference.collection(TransporterTableName)
                .document(transporter.getCompanyName()).update(transporter.toHashMap());
    }

    public void deleteTransporterById(String transpId) {
        saeedSonReference.collection(TransporterTableName)
                .document(transpId).delete();
    }

    //Labour
    public CollectionReference getAllLabours() {

        return saeedSonReference.collection(LabourTableName);

//        return labourDao.getAllLabours();
    }

    public DocumentReference getAllLaboursById(String labourId) {

        return saeedSonReference.collection(LabourTableName).document(labourId);
    }

    public LiveData<String> addLabour(final Labours labour) {

        MutableLiveData<String> response = new MutableLiveData<>();

        saeedSonReference.collection(LabourTableName).document(labour.getName())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    response.postValue(Responses.LABOUR_EXISTS);
                })
                .addOnFailureListener(e -> {
                    saeedSonReference.collection(LabourTableName).
                            document(labour.getName()).set(labour.toHashMap());
                    response.postValue(Responses.LABOUR_ADDED);
                });

        return response;
    }

    public void updateLabour(final Labours labour) {
        saeedSonReference.collection(LabourTableName).
                document(labour.getName()).update(labour.toHashMap());
    }

    public void deleteLabourById(String labourId) {
        saeedSonReference.collection(LabourTableName).
                document(labourId).delete();
    }


    //Patri
    public CollectionReference getAllPatris() {
        return saeedSonReference.collection(PatriTableName);
    }

    public DocumentReference getPatriById(String patriId) {
        return saeedSonReference.collection(PatriTableName).document(patriId);
    }

    public LiveData<String> addPatri(final Patri patri) {
        MutableLiveData<String> response = new MutableLiveData<>();

        saeedSonReference.collection(PatriTableName).document(patri.getName())
                .get()
                .addOnSuccessListener(runnable -> {
                    response.postValue(Responses.PATRI_EXISTS);
                })
                .addOnFailureListener(e -> {
                    saeedSonReference.collection(PatriTableName)
                            .document(patri.getName()).set(patri.toHashMap());
                    response.postValue(Responses.PATRI_ADDED);
                });

        return response;
    }

    public void updatePatri(final Patri patri) {

        saeedSonReference.collection(PatriTableName)
                .document(patri.getName()).update(patri.toHashMap());
    }

    public void deletePatriById(String patriId) {
        saeedSonReference.collection(PatriTableName)
                .document(patriId).delete();
    }


    //Bails
    public CollectionReference getAllBails() {

        return saeedSonReference.collection(BailTableName);
    }

    public DocumentReference getBailsById(String bailId) {
        return saeedSonReference.collection(BailTableName).document(bailId);
    }


    public LiveData<List<Customers>> getBailCustomerData(String senderId, String receiverId) {
        return null;
    }

    public void addBail(final Bails bail) {
        saeedSonReference.collection(BailTableName)
                .document(bail.getBiltyNo()).set(bail.toHashMap());


    }

    public void updateBail(final Bails bail) {
        saeedSonReference.collection(BailTableName)
                .document(bail.getBiltyNo()).update(bail.toHashMap());
    }

    public void deleteBail(final Bails bail) {
        saeedSonReference.collection(BailTableName)
                .document(bail.getBiltyNo()).delete();
    }

    //Items
    public CollectionReference getAllItems() {

        return saeedSonReference.collection(KindTableName);
//        return itemDao.getAllItem();
    }

    public void addItem(KindOfItem item) {
        saeedSonReference.collection(KindTableName)
                .document(item.getName()).set(item.toHashMap());
    }

    public void updateItem(KindOfItem item) {

        saeedSonReference.collection(KindTableName)
                .document(item.getName()).update(item.toHashMap());
    }

    public void deleteItemById(String itemId) {

        saeedSonReference.collection(KindTableName)
                .document(itemId).delete();
    }



}
