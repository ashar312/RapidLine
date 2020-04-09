package com.project.rapidline.viewmodel;

import android.app.Application;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.project.rapidline.repository.RapidLineRepository;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class HomeViewModel extends AdminViewModel {

    private static final String TAG = "HomeViewModel";
    private RapidLineRepository rapidLineRepository;
    private MutableLiveData<Integer> customerCount;
    private MutableLiveData<Integer> transporterCount;
    private MutableLiveData<Integer> patriCount;
    private MutableLiveData<Integer> labourCount;
    private MutableLiveData<Integer> agentCount;

    private MutableLiveData<List<Integer>> bailsCountInfo;
    public HomeViewModel(@NonNull Application application) {
        super(application);
        rapidLineRepository = new RapidLineRepository(application);
        customerCount =new MutableLiveData<>();
        labourCount=new MutableLiveData<>();
        patriCount=new MutableLiveData<>();
        transporterCount=new MutableLiveData<>();
        agentCount=new MutableLiveData<>();
        bailsCountInfo=new MutableLiveData<>();
    }

    public LiveData<Integer> getCustomerCount(){
        rapidLineRepository.getAllCustomers().
                addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }
                    if(queryDocumentSnapshots.isEmpty()){
                        customerCount.postValue(0);
                    }
                    else {
                        customerCount.postValue(queryDocumentSnapshots.size());
                    }
                });

        return customerCount;
    }

    public LiveData<Integer> getAgentCount(){
        rapidLineRepository.getAllAgents()
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }
                    if(queryDocumentSnapshots.isEmpty()){
                        agentCount.postValue(0);
                    }
                    else {
                        agentCount.postValue(queryDocumentSnapshots.size());
                    }
                });

        return agentCount;
    }

    public LiveData<Integer> getTransporterCount(){
        rapidLineRepository.getAllTransporters()
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }
                    if(queryDocumentSnapshots.isEmpty()){
                        transporterCount.postValue(0);
                    }
                    else {
                        transporterCount.postValue(queryDocumentSnapshots.size());
                    }
                });

        return transporterCount;
    }

    public LiveData<Integer> getLabourCount(){
        rapidLineRepository.getAllLabours()
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }
                    if(queryDocumentSnapshots.isEmpty()){
                        labourCount.postValue(0);
                    }
                    else {
                        labourCount.postValue(queryDocumentSnapshots.size());
                    }
                });

        return labourCount;
    }

    public LiveData<Integer> getPatriCount(){
        rapidLineRepository.getAllPatris()
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }
                    if(queryDocumentSnapshots.isEmpty()){
                        patriCount.postValue(0);
                    }
                    else {
                        patriCount.postValue(queryDocumentSnapshots.size());
                    }
                });

        return patriCount;
    }

    public LiveData<List<Integer>> getBailCount(){

        rapidLineRepository.getAllBails()
                .addSnapshotListener(MetadataChanges.INCLUDE,(queryDocumentSnapshots, e) -> {
                    if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }

                    List<Integer> bailinfo=new ArrayList<>();

                    if(queryDocumentSnapshots.isEmpty()){
                        bailinfo.add(0);
                        bailinfo.add(0);
                        bailinfo.add(0);
                        bailsCountInfo.postValue(bailinfo);
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
//                        for (DocumentChange change : queryDocumentSnapshots.getDocumentChanges()) {
//                            if (change.getType() == DocumentChange.Type.ADDED) {
//                                offline+=1;
//                            }
//                            else {
//                                online+=1;
//                            }

//                            String source = queryDocumentSnapshots.getMetadata().hasPendingWrites() ?
//                                    "local cache" : "server";
//
//                            if(source.equals("local cache")){
//                            }
//
//                            else {
//
//                            }
                        bailinfo.add(offline);
                        bailinfo.add(online);
                        bailinfo.add(pending);

                        bailsCountInfo.postValue(bailinfo);
                        }
                });
        return bailsCountInfo;
    }

}
