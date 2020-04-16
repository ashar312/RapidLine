package com.project.rapidline.repository;

import android.app.Application;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Source;
import com.project.rapidline.Database.entity.Admins;
import com.project.rapidline.Database.entity.Agents;
import com.project.rapidline.Database.entity.Bails;
import com.project.rapidline.Database.entity.Customers;
import com.project.rapidline.Database.entity.KindOfItem;
import com.project.rapidline.Database.entity.Labours;
import com.project.rapidline.Database.entity.Patri;
import com.project.rapidline.Database.entity.Transporters;
import com.project.rapidline.Models.BailCounters;
import com.project.rapidline.utils.Responses;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class RapidLineRepository {

    private static final String TAG = "RapidLineRepository";

    private Application application;
    //    private CityDao cityDao;

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
    private final String CounterTableName="BailCounter";

    public RapidLineRepository(Application application) {
        this.application = application;

        firestore = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        firestore.setFirestoreSettings(settings);

        saeedSonReference = firestore.collection(Database).document(SaeedSonDbName);
    }

    //
//    public LiveData<List<Cities>> getAllCities(){return cityDao.getCities();}
    //Admins
//    public LiveData<List<Admins>> getAllAdmins() {
////        List<Admins> adminsList=new ArrayList<>();
////        firestore.collection(Database).document(SaeedSonDbName).collection(AdminTableName)
////                .addSnapshotListener((documentSnapshot, e) -> {
////                    if (e != null) {
//////                        Log.w(TAG, "Listen failed.", e);
////                        return;
////                    }
////
////                    if(!documentSnapshot.isEmpty()){
////                        for (QueryDocumentSnapshot doc : documentSnapshot) {
////                            adminsList.add(new Admins())
////                        }
////                });
////
////        MutableLiveData<List<Admins>> mutableLiveData=new MutableLiveData<>();
////        mutableLiveData.postValue(adminDao.getAllAdmins().getValue());
//        return adminDao.getAllAdmins();
//    }

    public CollectionReference getAdmins() {
        return saeedSonReference.collection(AdminTableName);
    }

    public MutableLiveData<String> addAdmin(final Admins admin) {
        MutableLiveData<String> response = new MutableLiveData<>();

        saeedSonReference.collection(AdminTableName).document(admin.getUsername())
                .get(Source.DEFAULT)
                .addOnSuccessListener(documentSnapshot -> {
                    //username already present
                    if(documentSnapshot.exists())
                        response.postValue(Responses.ADMIN_EXISTS);
                    else{
                        saeedSonReference.collection(AdminTableName)
                                .document(admin.getUsername())
                                .set(admin.toHashMap());
                        response.postValue(Responses.ADMIN_ADDED);
                    }
                })
                .addOnFailureListener(e -> {
                    saeedSonReference.collection(AdminTableName)
                            .document(admin.getUsername())
                            .set(admin.toHashMap());
                    response.postValue(Responses.ADMIN_ADDED);
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
                .get(Source.DEFAULT)
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists())
                        response.postValue(Responses.AGENT_EXISTS);
                    else {
                        saeedSonReference.collection(AgentTableName)
                                .document(agent.getAgentName()).set(agent.toHashMap());
                        response.postValue(Responses.AGENT_ADDED);
                    }
                })
                .addOnFailureListener(e -> {
                    saeedSonReference.collection(AgentTableName)
                            .document(agent.getAgentName()).set(agent.toHashMap());
                    response.postValue(Responses.AGENT_ADDED);
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
                .get(Source.DEFAULT)
                .addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists())
                        response.postValue(Responses.CUSTOMER_EXISTS);
                    else {
                        saeedSonReference.collection(CustomerTableName).
                                document(customer.getCompanyName()).set(customer.toHashMap());
                        response.postValue(Responses.CUSTOMER_ADDED);
                    }
                })
                .addOnFailureListener(e -> {
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
                .get(Source.DEFAULT)
                .addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists())
                        response.postValue(Responses.TRANSPORTER_EXISTS);
                    else {
                        saeedSonReference.collection(TransporterTableName).
                                document(transporter.getCompanyName()).set(transporter.toHashMap());
                        response.postValue(Responses.TRANSPORTER_ADDED);
                    }
                })
                .addOnFailureListener(e -> {
                    saeedSonReference.collection(TransporterTableName).
                            document(transporter.getCompanyName()).set(transporter.toHashMap());
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
                .get(Source.DEFAULT)
                .addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists())
                        response.postValue(Responses.LABOUR_EXISTS);
                    else {
                        saeedSonReference.collection(LabourTableName).
                                document(labour.getName()).set(labour.toHashMap());
                        response.postValue(Responses.LABOUR_ADDED);
                    }
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
                .get(Source.DEFAULT)
                .addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists())
                        response.postValue(Responses.PATRI_EXISTS);
                    else {
                        saeedSonReference.collection(PatriTableName)
                                .document(patri.getName()).set(patri.toHashMap());
                        response.postValue(Responses.PATRI_ADDED);
                    }
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

    public void deleteBail(String bailid) {
        saeedSonReference.collection(BailTableName)
                .document(bailid).delete();
    }

    public DocumentReference getBailCounter(){
        return saeedSonReference.collection(BailTableName).document(CounterTableName);
    }

    public void updateBailCounter(BailCounters bailCounters){
        saeedSonReference.collection(BailTableName).document(CounterTableName)
                .update(bailCounters.toHashMap());
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
