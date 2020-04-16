package com.project.rapidline.viewmodel;

import android.app.Application;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Source;
import com.project.rapidline.Database.entity.Admins;
import com.project.rapidline.Models.BailCounters;
import com.project.rapidline.repository.RapidLineRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AdminViewModel extends AndroidViewModel {

    private RapidLineRepository rapidLineRepository;
    private String adminName;

    public AdminViewModel(@NonNull Application application) {
        super(application);
        rapidLineRepository = new RapidLineRepository(application);
    }

    public LiveData<String> checkAdmin(String username, String password) {
        MutableLiveData<String> response = new MutableLiveData<>();
        rapidLineRepository.getAdmins().whereEqualTo("username", username)
                .get(Source.DEFAULT)
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if(queryDocumentSnapshots.isEmpty()){
                        response.setValue("Either password or username is incorrect");
                    }
                    else {
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
        return rapidLineRepository.addAdmin(admin);
    }

    public String getAdminName() {
        return adminName;
    }

    public LiveData<Admins> getAdminInfo(String username) {
        MutableLiveData<Admins> data = new MutableLiveData<>();
        rapidLineRepository.getAdmins().document(username)
                .get(Source.DEFAULT)
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {

                        Admins admins = new Admins(documentSnapshot.getString("bailSymbol"),
                                documentSnapshot.getDouble("bailCounter"),
                                documentSnapshot.getDouble("builtyRange"),
                                documentSnapshot.getDouble("builtyCounter"));

                        data.postValue(admins);
                    }

                });
        return data;
    }


    public void updateAdminBailInfo(int bailCounter, String username) {
        rapidLineRepository.getAdmins().document(username)
                .update("bailCounter", bailCounter);
    }


    public LiveData<BailCounters> getBailCounterData() {
        MutableLiveData<BailCounters> data = new MutableLiveData<>();
        rapidLineRepository.getBailCounter().get(Source.DEFAULT)
                .addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists()){
                        BailCounters counters = documentSnapshot.toObject(BailCounters.class);
                        data.postValue(counters);
                    }
                });
        return data;
    }

    public void updateBailCounter(BailCounters bailCounters) {
        rapidLineRepository.updateBailCounter(bailCounters);
    }


}
