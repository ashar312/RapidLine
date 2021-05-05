package com.project.rapidline.repository;

import android.app.Application;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.project.rapidline.Activities.RapidLine.Forms.MaintainanceChartForm;
import com.project.rapidline.Models.RapidLine.Bilty;
import com.project.rapidline.Models.RapidLine.Drivers;
import com.project.rapidline.Models.RapidLine.MaintainanceChart;
import com.project.rapidline.Models.RapidLine.Shipment;
import com.project.rapidline.Models.RapidLine.VechileFitness;
import com.project.rapidline.Models.RapidLine.OfficeStaff;
import com.project.rapidline.Models.RapidLine.SideKick;
import com.project.rapidline.Models.RapidLine.Vechile;
import com.project.rapidline.utils.Responses;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RapidLineRepository {

    private Application application;

    private DocumentReference rapidLineReference;


    private final String Database = "Database";
    private final String RapidLineDbName = "RapidLineDB";
    private final String DriverTableName = "Drivers";
    private final String SideKickTableName = "Sidekicks";
    private final String OfficeStaffTableName = "OfficeStaff";
    private final String VechileTableName = "Vechiles";
    private final String VechileDataTableName = "Category";
    private final String MaintainanceTableName = "MaintainanceChart";
    private final String MaintainanceDataTableName = "MaintainanceData";
    private final String BiltyTableName = "Bilty";
    private final String ShipmentTableName="Shipments";

    public RapidLineRepository(Application application) {
        this.application = application;

        rapidLineReference = FirebaseFirestore.getInstance()
                .collection(Database).document(RapidLineDbName);


    }


    public CollectionReference getAllDrivers() {
        return rapidLineReference.collection(DriverTableName);
    }

    public LiveData<String> addDriver(Drivers driver) {
        MutableLiveData<String> response = new MutableLiveData<>();

        rapidLineReference.collection(DriverTableName).document(driver.getDriverName())
                .get(Source.DEFAULT)
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists())
                        response.setValue(Responses.DRIVER_EXISTS);
                    else {
                        rapidLineReference.collection(DriverTableName).document(driver.getDriverName())
                                .set(driver);
                        response.setValue(Responses.DRIVER_ADDED);
                    }

                })
                .addOnFailureListener(e -> {
                    rapidLineReference.collection(DriverTableName).document(driver.getDriverName())
                            .set(driver);
                    response.setValue(Responses.DRIVER_ADDED);
                });
        return response;
    }

    public void updateDriver(Drivers driver) {
        rapidLineReference.collection(DriverTableName)
                .document(driver.getDriverName()).set(driver);
    }

    public void deleteDriver(String key) {
        rapidLineReference.collection(DriverTableName)
                .document(key).delete();
    }

    public CollectionReference getAllSideKicks() {
        return rapidLineReference.collection(SideKickTableName);
    }

    public LiveData<String> addSideKick(SideKick sideKick) {
        MutableLiveData<String> response = new MutableLiveData<>();

        rapidLineReference.collection(SideKickTableName).document(sideKick.getSideKickName())
                .get(Source.DEFAULT)
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists())
                        response.setValue(Responses.SIDEKICK_EXISTS);
                    else {
                        rapidLineReference.collection(SideKickTableName).document(sideKick.getSideKickName())
                                .set(sideKick);
                        response.setValue(Responses.SIDEKICK_ADDED);
                    }

                })
                .addOnFailureListener(e -> {
                    rapidLineReference.collection(SideKickTableName).document(sideKick.getSideKickName())
                            .set(sideKick);
                    response.setValue(Responses.SIDEKICK_ADDED);
                });
        return response;
    }

    public void updateSideKick(SideKick sideKick) {
        rapidLineReference.collection(SideKickTableName).document(sideKick.getSideKickName())
                .set(sideKick);
    }

    public void deleteSideKick(String key) {
        rapidLineReference.collection(SideKickTableName)
                .document(key).delete();
    }


    public CollectionReference getAllOfficeStaff() {
        return rapidLineReference.collection(OfficeStaffTableName);
    }

    public LiveData<String> addOfficeStaff(OfficeStaff staff) {
        MutableLiveData<String> response = new MutableLiveData<>();

        rapidLineReference.collection(OfficeStaffTableName).document(staff.getStaffName())
                .get(Source.DEFAULT)
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists())
                        response.setValue(Responses.STAFF_EXISTS);
                    else {
                        rapidLineReference.collection(OfficeStaffTableName).document(staff.getStaffName())
                                .set(staff);
                        response.setValue(Responses.STAFF_ADDED);
                    }

                })
                .addOnFailureListener(e -> {
                    rapidLineReference.collection(OfficeStaffTableName).document(staff.getStaffName())
                            .set(staff);
                    response.setValue(Responses.STAFF_ADDED);
                });
        return response;
    }

    public void updateStaff(OfficeStaff staff) {
        rapidLineReference.collection(OfficeStaffTableName).document(staff.getStaffName())
                .set(staff);
    }

    public void deleteStaff(String key) {
        rapidLineReference.collection(OfficeStaffTableName)
                .document(key).delete();
    }


    public CollectionReference getAllVechiles() {
        return rapidLineReference.collection(VechileTableName);
    }

    public LiveData<String> addVechile(Vechile vechile) {
        MutableLiveData<String> response = new MutableLiveData<>();

        rapidLineReference.collection(VechileTableName).document(vechile.getRegNo())
                .get(Source.DEFAULT)
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists())
                        response.setValue(Responses.VECHILE_EXISTS);
                    else {
                        rapidLineReference.collection(VechileTableName).document(vechile.getRegNo())
                                .set(vechile);
                        response.setValue(Responses.VECHILE_ADDED);
                    }

                })
                .addOnFailureListener(e -> {
                    rapidLineReference.collection(VechileTableName).document(vechile.getRegNo())
                            .set(vechile);
                    response.setValue(Responses.VECHILE_ADDED);
                });
        return response;
    }

    public void updateVechile(Vechile vechile) {
        rapidLineReference.collection(VechileTableName).document(vechile.getRegNo())
                .set(vechile);
    }

    public void deleteVechile(String key) {
        rapidLineReference.collection(VechileTableName)
                .document(key).delete();
    }


    public DocumentReference getAllVechileData() {
        return rapidLineReference.collection(VechileTableName).document(VechileDataTableName);
    }



    public void addVechileFitness(String vechileNo, int fitnessPercentage, Date lastTestTaken) {
        rapidLineReference.collection(VechileTableName).document(vechileNo)
                .update("fitnessPercentage",fitnessPercentage,"lastTestTaken",lastTestTaken);
    }

    //Maintainance
    public DocumentReference getAllMaintainanceData() {
        return rapidLineReference.collection(MaintainanceTableName).document(MaintainanceDataTableName);
    }

    public CollectionReference getAllMaintainanceRecord() {
        return rapidLineReference.collection(MaintainanceTableName);
    }

    public void addMaintainanceRecord(MaintainanceChart maintainanceChart) {
        rapidLineReference.collection(MaintainanceTableName).document(maintainanceChart.getRegNo())
                .set(maintainanceChart);
    }

    //Bilty table
    public CollectionReference getAllBilty() {
        return rapidLineReference.collection(BiltyTableName);
    }

    public void addBilty(Bilty bilty) {
        rapidLineReference.collection(BiltyTableName).document(bilty.getBiltyNo())
                .set(bilty);
    }

    public void updateBilty(Bilty bilty) {
        rapidLineReference.collection(BiltyTableName).document(bilty.getBiltyNo())
                .set(bilty).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(application, "Updated", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteBilty(String key) {
        rapidLineReference.collection(BiltyTableName).document(key)
                .delete();
    }

    public void updateBiltyShipmentStatus(List<String> biltyList){
       for(String bilty:biltyList){
            rapidLineReference.collection(BiltyTableName).document(bilty)
            .update("shipped",true);
       }
    }



    //SHIPMENTS

    public void addShipment(Shipment shipment){
        rapidLineReference.collection(ShipmentTableName).document(shipment.getShipmentNo())
        .set(shipment);
    }

}
