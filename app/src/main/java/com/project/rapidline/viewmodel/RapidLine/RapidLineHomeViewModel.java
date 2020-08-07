package com.project.rapidline.viewmodel.RapidLine;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.project.rapidline.repository.RapidLineRepository;
import com.project.rapidline.viewmodel.SaeedSons.AdminViewModel;

import java.util.ArrayList;
import java.util.List;

public class RapidLineHomeViewModel extends AdminViewModel {

    private RapidLineRepository rapidLineRepository;
    private MutableLiveData<Integer> driverCount;
    private MutableLiveData<Integer> sidekickCount;
    private MutableLiveData<Integer> staffCount;
    private MutableLiveData<Integer> vechilesCount;
    private MutableLiveData<List<Integer>> biltyCountInfo;

    public RapidLineHomeViewModel(@NonNull Application application) {
        super(application);
        rapidLineRepository = new RapidLineRepository(application);

        driverCount = new MutableLiveData<>();
        sidekickCount = new MutableLiveData<>();
        staffCount = new MutableLiveData<>();
        vechilesCount = new MutableLiveData<>();

        biltyCountInfo=new MutableLiveData<>();
    }

    public LiveData<Integer> getDriverCount() {
        rapidLineRepository.getAllDrivers()
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }
                    if (queryDocumentSnapshots.isEmpty()) {
                        driverCount.postValue(0);
                    } else {
                        driverCount.postValue(queryDocumentSnapshots.size());
                    }
                });
        return driverCount;
    }

    public LiveData<Integer> getSidekickCount() {
        rapidLineRepository.getAllSideKicks()
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }
                    if (queryDocumentSnapshots.isEmpty()) {
                        sidekickCount.postValue(0);
                    } else {
                        sidekickCount.postValue(queryDocumentSnapshots.size());
                    }
                });
        return sidekickCount;
    }

    public LiveData<Integer> getStaffCount() {
        rapidLineRepository.getAllOfficeStaff()
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }
                    if (queryDocumentSnapshots.isEmpty()) {
                        staffCount.postValue(0);
                    } else {
                        staffCount.postValue(queryDocumentSnapshots.size());
                    }
                });
        return staffCount;
    }

    public LiveData<Integer> getVechileCount() {
        rapidLineRepository.getAllVechiles()
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }
                    if (queryDocumentSnapshots.isEmpty()) {
                        vechilesCount.postValue(0);
                    } else {
                        vechilesCount.postValue(queryDocumentSnapshots.size());
                    }
                });
        return vechilesCount;
    }

    public LiveData<List<Integer>> getBiltyCount() {
        rapidLineRepository.getAllBilty()
                .addSnapshotListener(MetadataChanges.INCLUDE, (queryDocumentSnapshots, e) -> {
                    if (e != null)
                        return;

                    List<Integer> biltyinfo=new ArrayList<>();


                    if(queryDocumentSnapshots.isEmpty()){
                        biltyinfo.add(0);
                        biltyinfo.add(0);
                        biltyinfo.add(0);

                        biltyCountInfo.postValue(biltyinfo);
                    }
                    else {
                        int offline=0,online=0,pending=0;

                        for (QueryDocumentSnapshot snapshots:queryDocumentSnapshots){
                            if(snapshots.getMetadata().hasPendingWrites()){
                                offline+=1;
                            }
                            else {
                                online+=1;
                            }
                        }
                        biltyinfo.add(offline);
                        biltyinfo.add(online);
                        biltyinfo.add(pending);

                        biltyCountInfo.postValue(biltyinfo);
                    }


                });
        return biltyCountInfo;

    }


}
