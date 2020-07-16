package com.project.rapidline.viewmodel.RapidLine;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.project.rapidline.Models.RapidLine.Bilty;
import com.project.rapidline.Models.RapidLine.Shipment;
import com.project.rapidline.Models.SaeedSons.Bails;
import com.project.rapidline.repository.RapidLineRepository;
import com.project.rapidline.repository.SaeedSonsRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShipmentViewModel extends AndroidViewModel {

    private RapidLineRepository rapidLineRepository;
    private SaeedSonsRepository saeedSonsRepository;


    public ShipmentViewModel(@NonNull Application application) {
        super(application);
        saeedSonsRepository = new SaeedSonsRepository(application);
        rapidLineRepository = new RapidLineRepository(application);
    }

    //Bails
    public LiveData<List<Bilty>> getListAllBiltyByCity(List<String> stopList) {
        MutableLiveData<List<Bilty>> data = new MutableLiveData<>();

        List<Bilty> biltyList = new ArrayList<>();

        //Get all bails
        saeedSonsRepository.getAllBails().whereIn("toCity", stopList).addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
                return;
            }

            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (!doc.getId().equals("BailCounter") && doc.getString("transporterId").toLowerCase().equals("rapid line")) {
                        biltyList.add(new Bilty(doc.getId(),
                                doc.getString("fromCity"),
                                doc.getString("toCity"),
                                doc.getString("kindId"),
                                doc.getDouble("qty"),
                                doc.getString("senderId"),
                                doc.getString("receiverId"),
                                doc.getString("transporterId"),
                                doc.getId(),
                                doc.getString("agentId"),
                                doc.getDouble("volume"),
                                doc.getDouble("weight"),
                                doc.getString("madeBy"),
                                doc.getDate("madeDateTime"),
                                doc.getString("transportCharge"),
                                doc.getString("labourCharge"),
                                doc.getString("electric_charge"),
                                doc.getString("packingCharge"),
                                doc.getString("comments")
                        ));


                    }
                }
            }

            //Get all bilty
            rapidLineRepository.getAllBilty().whereIn("toCity", stopList).addSnapshotListener((biltySnapshots, e1) -> {
                if (e1 != null) {
                    return;
                }

                if (!biltySnapshots.isEmpty()) {
                    for (QueryDocumentSnapshot doc : biltySnapshots) {
                        biltyList.add(doc.toObject(Bilty.class)) ;
                    }
                }
                data.postValue(biltyList);
            });

        });


        return data;
    }

    public void addShipment(Shipment shipment){
        rapidLineRepository.addShipment(shipment);
    }

}
