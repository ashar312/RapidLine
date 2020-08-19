package com.project.rapidline.viewmodel.SaeedSons;

import android.app.Application;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Source;
import com.project.rapidline.Models.Admins;
import com.project.rapidline.Models.BailCounters;
import com.project.rapidline.Models.Permissions;
import com.project.rapidline.repository.SaeedSonsRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AdminViewModel extends AndroidViewModel {

    private SaeedSonsRepository saeedSonsRepository;
    private String adminName;

    public AdminViewModel(@NonNull Application application) {
        super(application);
        saeedSonsRepository = new SaeedSonsRepository(application);
    }

    public LiveData<String> checkAdmin(String username, String password) {
        MutableLiveData<String> response = new MutableLiveData<>();
        saeedSonsRepository.getAdmins().whereEqualTo("username", username)
                .get(Source.DEFAULT)
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        response.setValue("Either password or username is incorrect");
                    } else {
                        for (QueryDocumentSnapshot users : queryDocumentSnapshots) {
                            //Check pass
                            if (password.equals(users.getString("pass"))) {
                                response.setValue("Login Sucessfull");
                                adminName = users.getString("adminName");
                                return;
                            }
                        }
                        response.setValue("Password is incorrect");
                    }
                })
                .addOnFailureListener(e -> {
                    response.setValue("Either password or username is incorrect");
                });

        return response;
    }

    public LiveData<String> addAdmin(final Admins admin) {
        return saeedSonsRepository.addAdmin(admin);
    }

    public LiveData<Admins> getAdminById(String key) {
        MutableLiveData<Admins> data = new MutableLiveData<>();
        saeedSonsRepository.getAdmins().document(key)
                .get(Source.DEFAULT)
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Admins admins = documentSnapshot.toObject(Admins.class);
                        admins.setKey(key);
                        data.postValue(admins);
                    }
                });
        return data;
    }

    public String getAdminName() {
        return adminName;
    }

    public LiveData<Admins> getAdminInfo(String username) {
        MutableLiveData<Admins> data = new MutableLiveData<>();
        saeedSonsRepository.getAdmins().document(username)
                .addSnapshotListener((documentSnapshot, e) -> {
                    if (e != null) {
                        return;
                    }
                    if (documentSnapshot.exists()) {
                        Admins admins = new Admins(
                                documentSnapshot.getString("bailCounter"),
                                documentSnapshot.getString("biltyCounter"),
                                documentSnapshot.getString("shipmentCounter")
                        );

                        data.postValue(admins);
                    }
                });
        return data;
    }

    public void updateAdmin(Admins admins) {
        saeedSonsRepository.updateAdminById(admins);
    }

    public void updateAdminBailInfo(String bailCounter, String username) {
        saeedSonsRepository.getAdmins().document(username)
                .update("bailCounter", bailCounter);
    }


    public LiveData<BailCounters> getBailCounterData() {
        MutableLiveData<BailCounters> data = new MutableLiveData<>();
        saeedSonsRepository.getBailCounter().get(Source.DEFAULT)
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        BailCounters counters = documentSnapshot.toObject(BailCounters.class);
                        data.postValue(counters);
                    }
                });
        return data;
    }

    public void updateBiltyCounter(String biltyCounter, String username) {
        saeedSonsRepository.getAdmins().document(username)
                .update("biltyCounter", biltyCounter);
    }

    public void updateShipmentCounter(String no, String username) {
        saeedSonsRepository.getAdmins().document(username)
                .update("shipmentCounter", no);
    }

    //Admin Rights
    public LiveData<Permissions> getAdminPermissionsById(String key) {
        MutableLiveData<Permissions> data = new MutableLiveData<>();

        saeedSonsRepository.getAllPermissions().document(key)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists())
                        data.postValue(documentSnapshot.toObject(Permissions.class));
                });
        return data;
    }

    public void addAdminPermissions(String key, Permissions permission) {
        saeedSonsRepository.addAdminPermissions(key, permission);
    }

    public void updateAdminPerm(String adminId, String permName, boolean value) {
        saeedSonsRepository.updateAdminPermission(adminId, permName, value);
    }


}
