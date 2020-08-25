package com.project.rapidline.repository;

import android.app.Application;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.project.rapidline.Models.Admins;
import com.project.rapidline.Models.Permissions;
import com.project.rapidline.Models.RapidLine.Supplier;
import com.project.rapidline.Models.SaeedSons.Agents;
import com.project.rapidline.Models.SaeedSons.Bails;
import com.project.rapidline.Models.SaeedSons.Cities;
import com.project.rapidline.Models.SaeedSons.Customers;
import com.project.rapidline.Models.SaeedSons.KindOfItem;
import com.project.rapidline.Models.SaeedSons.Labours;
import com.project.rapidline.Models.SaeedSons.Patri;
import com.project.rapidline.Models.SaeedSons.Transporters;
import com.project.rapidline.Models.BailCounters;
import com.project.rapidline.utils.Responses;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SaeedSonsRepository {


    private Application application;
    //    private CityDao cityDao;

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
    private final String CounterTableName = "BailCounter";
    private final String CitiesTableName = "Cities";
    private final String AdminRightsTableName="Admin Rights";
    private final String SupplierTableName="Supplier";


    public SaeedSonsRepository(Application application) {
        this.application = application;

        saeedSonReference = FirebaseFirestore.getInstance()
                .collection(Database).document(SaeedSonDbName);
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
                    if (documentSnapshot.exists())
                        response.postValue(Responses.ADMIN_EXISTS);
                    else {
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

    public void updateAdminById(Admins admins){
        saeedSonReference.collection(AdminTableName).document(admins.getKey())
                .set(admins);
    }

    public void deleteAdminById(String key) {
        saeedSonReference.collection(AdminTableName).document(key)
                .delete();
    }

    public CollectionReference getAllPermissions(){
        return saeedSonReference.collection(AdminRightsTableName);
    }

    public void addAdminPermissions(String key, Permissions permission){
        saeedSonReference.collection(AdminRightsTableName).document(key)
                .set(permission);
    }

    public void updateAdminPermission(String adminId,String permName,boolean value){
        saeedSonReference.collection(AdminRightsTableName).document(adminId)
                .update(permName,value);
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
                    if (documentSnapshot.exists())
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
                    if (documentSnapshot.exists())
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
                    if (documentSnapshot.exists())
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
                    if (documentSnapshot.exists())
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
                .document(bail.getBailNo()).set(bail.toHashMap());


    }

    public void updateBail(final Bails bail) {
        saeedSonReference.collection(BailTableName)
                .document(bail.getBailNo()).update(bail.toHashMap());
    }

    public void deleteBail(String bailid) {
        saeedSonReference.collection(BailTableName)
                .document(bailid).delete();
    }

    public DocumentReference getBailCounter() {
        return saeedSonReference.collection(BailTableName).document(CounterTableName);
    }

    public void updateBailCounter(BailCounters bailCounters) {
        saeedSonReference.collection(BailTableName).document(CounterTableName)
                .update(bailCounters.toHashMap());
    }


    //Items
    public CollectionReference getAllItems() {

        return saeedSonReference.collection(KindTableName);
//        return itemDao.getAllItem();
    }

    public LiveData<String> addItem(KindOfItem item) {
        MutableLiveData<String> response = new MutableLiveData<>();

        saeedSonReference.collection(KindTableName).document(item.getName())
                .get(Source.DEFAULT)
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        response.postValue(Responses.ITEM_EXISTS);
                    } else {
                        saeedSonReference.collection(KindTableName).document(item.getName())
                                .set(item);
                        response.postValue(Responses.ITEM_ADDED);
                    }
                })
                .addOnFailureListener(e -> {
                    saeedSonReference.collection(KindTableName).document(item.getName())
                            .set(item);
                    response.postValue(Responses.ITEM_ADDED);
                });

        return response;
    }

    public void updateItem(String key, String value) {

        saeedSonReference.collection(KindTableName)
                .document(key).update("name", value);
    }

    public void deleteItemById(String itemId) {
        saeedSonReference.collection(KindTableName)
                .document(itemId).delete();
    }

    //Cities
    public CollectionReference getAllCities() {
        return saeedSonReference.collection(CitiesTableName);
    }

    public LiveData<String> addCity(Cities cities) {
        MutableLiveData<String> response = new MutableLiveData<>();

        saeedSonReference.collection(CitiesTableName)
                .document(cities.getName())
                .get(Source.DEFAULT)
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        response.postValue(Responses.CITY_EXISTS);
                    } else {
                        saeedSonReference.collection(CitiesTableName)
                                .document(cities.getName()).set(cities);
                        response.postValue(Responses.CITY_ADDED);
                    }
                })
                .addOnFailureListener(e -> {
                    saeedSonReference.collection(CitiesTableName)
                            .document(cities.getName()).set(cities);
                    response.postValue(Responses.CITY_ADDED);
                });
        return response;
    }

    public void updateCity(String key, String value) {

        saeedSonReference.collection(CitiesTableName)
                .document(key).update("name", value);

    }

    public void deleteCityById(String itemId) {
        saeedSonReference.collection(CitiesTableName)
                .document(itemId).delete();
    }

    //Supplier
    public CollectionReference getAllSuppliers() {
        return saeedSonReference.collection(SupplierTableName);
    }

    public DocumentReference getSupplierById(String supplierId) {
        return saeedSonReference.collection(SupplierTableName).document(supplierId);
    }

    public LiveData<String> addSupplier(final Supplier supplier) {

        MutableLiveData<String> response = new MutableLiveData<>();
        saeedSonReference.collection(SupplierTableName).document(supplier.getSupplierName())
                .get(Source.DEFAULT)
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists())
                        response.postValue(Responses.SUPPLIER_EXISTS);
                    else {
                        saeedSonReference.collection(SupplierTableName).
                                document(supplier.getSupplierName()).set(supplier);
                        response.postValue(Responses.SUPPLIER_ADDED);
                    }
                })
                .addOnFailureListener(e -> {
                    saeedSonReference.collection(SupplierTableName).
                            document(supplier.getSupplierName()).set(supplier);
                    response.postValue(Responses.SUPPLIER_ADDED);
                });

        return response;
    }

    public void updateSupplier(final Supplier supplier) {
        saeedSonReference.collection(SupplierTableName).
                document(supplier.getSupplierName()).set(supplier);
    }

    public void deleteSupplierById(String supplierId) {
        saeedSonReference.collection(SupplierTableName).
                document(supplierId).delete();
    }

}
