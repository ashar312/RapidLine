package com.project.rapidline.viewmodel.RapidLine;

import android.app.Application;

import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.project.rapidline.Models.RapidLine.Bilty;
import com.project.rapidline.Models.RapidLine.MaintainanceChart;
import com.project.rapidline.Models.RapidLine.MaintainanceData;
import com.project.rapidline.Models.RapidLine.VechileData;
import com.project.rapidline.Models.RapidLine.Drivers;
import com.project.rapidline.Models.RapidLine.VechileFitness;
import com.project.rapidline.Models.RapidLine.OfficeStaff;
import com.project.rapidline.Models.RapidLine.SideKick;
import com.project.rapidline.Models.RapidLine.Vechile;
import com.project.rapidline.repository.RapidLineRepository;
import com.project.rapidline.repository.SaeedSonsRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class RapidLineViewModel extends AndroidViewModel {
    private RapidLineRepository rapidLineRepository;
    private SaeedSonsRepository saeedSonsRepository;

    private MutableLiveData<List<Drivers>> listAllDrivers;
    private MutableLiveData<List<OfficeStaff>> listAllStaff;
    private MutableLiveData<List<SideKick>> listAllSideKick;
    private MutableLiveData<List<Vechile>> listAllVechile;
    private MutableLiveData<List<Bilty>> listAllBilty;

    public RapidLineViewModel(@NonNull Application application) {
        super(application);

        rapidLineRepository = new RapidLineRepository(application);
        saeedSonsRepository = new SaeedSonsRepository(application);

        listAllDrivers = new MutableLiveData<>();
        listAllStaff = new MutableLiveData<>();
        listAllSideKick = new MutableLiveData<>();
        listAllVechile = new MutableLiveData<>();
        listAllBilty=new MutableLiveData<>();
    }


    //Drivers

    public LiveData<List<Drivers>> getAllDrivers() {
        rapidLineRepository.getAllDrivers()
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null)
                        return;

                    List<Drivers> driversList = new ArrayList<>();

                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            Drivers driver = doc.toObject(Drivers.class);
                            driver.setKey(doc.getId());
                            driversList.add(driver);
                        }
                    }


                    listAllDrivers.postValue(driversList);

                });

        return listAllDrivers;
    }

    public LiveData<Drivers> getDriverById(String key) {
        MutableLiveData<Drivers> data = new MutableLiveData<>();

        rapidLineRepository.getAllDrivers().document(key)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        data.postValue(documentSnapshot.toObject(Drivers.class));
                    }
                });
        return data;
    }

    public LiveData<String> addDriver(Drivers driver) {
        return rapidLineRepository.addDriver(driver);
    }

    public void updateDriver(Drivers driver) {
        rapidLineRepository.updateDriver(driver);
    }

    public void deleteDriver(String key) {
        rapidLineRepository.deleteDriver(key);
    }


    //Office Staff
    public LiveData<List<OfficeStaff>> getAllOfficeStaff() {
        rapidLineRepository.getAllOfficeStaff()
                .addSnapshotListener((queryDocumentSnapshots, e) -> {

                    if (e != null)
                        return;

                    List<OfficeStaff> officeStaffList = new ArrayList<>();

                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            OfficeStaff officeStaff = doc.toObject(OfficeStaff.class);
                            officeStaff.setKey(doc.getId());
                            officeStaffList.add(officeStaff);
                        }
                    }

                    listAllStaff.postValue(officeStaffList);
                });

        return listAllStaff;
    }


    public LiveData<OfficeStaff> getOfficeStaffById(String key) {
        MutableLiveData<OfficeStaff> data = new MutableLiveData<>();

        rapidLineRepository.getAllOfficeStaff().document(key)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        data.postValue(documentSnapshot.toObject(OfficeStaff.class));
                    }
                });
        return data;
    }

    public LiveData<String> addOffice(OfficeStaff staff) {
        return rapidLineRepository.addOfficeStaff(staff);
    }

    public void updateOffice(OfficeStaff staff) {
        rapidLineRepository.updateStaff(staff);
    }

    public void deleteOffice(String key) {
        rapidLineRepository.deleteStaff(key);
    }


    //SideKicks
    public LiveData<List<SideKick>> getAllSideKicks() {
        rapidLineRepository.getAllSideKicks()
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null)
                        return;

                    List<SideKick> sideKickList = new ArrayList<>();

                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            SideKick sideKick = doc.toObject(SideKick.class);
                            sideKick.setKey(doc.getId());
                            sideKickList.add(sideKick);

                        }
                    }

                    listAllSideKick.postValue(sideKickList);
                });
        return listAllSideKick;
    }

    public LiveData<SideKick> getSideKickById(String key) {
        MutableLiveData<SideKick> data = new MutableLiveData<>();

        rapidLineRepository.getAllSideKicks().document(key)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        data.postValue(documentSnapshot.toObject(SideKick.class));
                    }
                });
        return data;
    }

    public LiveData<String> addSideKick(SideKick sideKick) {
        return rapidLineRepository.addSideKick(sideKick);
    }

    public void updateSideKick(SideKick sideKick) {
        rapidLineRepository.updateSideKick(sideKick);
    }

    public void deleteSideKick(String key) {
        rapidLineRepository.deleteSideKick(key);
    }


    //Vechiles
    public LiveData<List<Vechile>> getAllVechiles() {
        rapidLineRepository.getAllVechiles()
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null)
                        return;

                    List<Vechile> vechileList = new ArrayList<>();

                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            if (!doc.getId().equals("Category")) {
                                Vechile vechile = doc.toObject(Vechile.class);
                                vechile.setKey(doc.getId());
                                vechileList.add(vechile);
                            }
                        }
                    }

                    listAllVechile.postValue(vechileList);
                });

        return listAllVechile;
    }

    public LiveData<Vechile> getVechilesById(String key) {
        MutableLiveData<Vechile> data = new MutableLiveData<>();

        rapidLineRepository.getAllVechiles().document(key)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        data.postValue(documentSnapshot.toObject(Vechile.class));
                    }
                });
        return data;
    }

    public LiveData<String> addVechile(Vechile vechile) {
        return rapidLineRepository.addVechile(vechile);
    }

    public void updateVechile(Vechile vechile) {
        rapidLineRepository.updateVechile(vechile);
    }

    public void deleteVechile(String key) {
        rapidLineRepository.deleteVechile(key);
    }

    public LiveData<List<String>> getAllVechileCategory() {
        MutableLiveData<List<String>> data = new MutableLiveData<>();

        rapidLineRepository.getAllVechileData().get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        VechileData category = documentSnapshot.toObject(VechileData.class);
                        data.postValue(category.getVechileCategory());
                    }
                });

        return data;
    }

    public LiveData<List<String>> getAllVechileModels() {
        MutableLiveData<List<String>> data = new MutableLiveData<>();

        List<String> modelList = new ArrayList<>();
        int currYear = Calendar.getInstance().get(Calendar.YEAR);
        int startYear = 1980;

        for (int i = startYear; i <= currYear; i++) {
            modelList.add(String.valueOf(i));
        }

        data.postValue(modelList);

        return data;
    }


    public void addVechileFitness(String vechileNo, int fitnessPercentage, Date lastTestTaken) {
        rapidLineRepository.addVechileFitness(vechileNo,fitnessPercentage,lastTestTaken);
    }

    public LiveData<MaintainanceData> getAllMaintainanceData() {
        MutableLiveData<MaintainanceData> data = new MutableLiveData<>();

        rapidLineRepository.getAllMaintainanceData().get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        data.postValue(documentSnapshot.toObject(MaintainanceData.class));
                    }
                });

        return data;
    }

    public void addMaintainanceChartRecord(MaintainanceChart maintainanceChart) {
        rapidLineRepository.addMaintainanceRecord(maintainanceChart);
    }

    public LiveData<List<List<Bilty>>> getAllBilty() {

        MutableLiveData<List<List<Bilty>>> data=new MutableLiveData<>();

        //Get all bails
        saeedSonsRepository.getAllBails().orderBy("madeDateTime", Query.Direction.DESCENDING).addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
                return;
            }

            List<Bilty> bailList = new ArrayList<>();

            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (!doc.getId().equals("BailCounter") && doc.getString("transporterId").toLowerCase().equals("rapid line")) {
                        bailList.add(new Bilty(doc.getId(),
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
            rapidLineRepository.getAllBilty().orderBy("madeDateTime", Query.Direction.DESCENDING).addSnapshotListener((biltySnapshots, e1) -> {
                if (e1 != null) {
                    return;
                }
                List<Bilty> biltyList=new ArrayList<>();
                if (!biltySnapshots.isEmpty()) {
                    for (QueryDocumentSnapshot doc : biltySnapshots) {
                        biltyList.add(doc.toObject(Bilty.class)) ;
                    }
                }
                List<List<Bilty>> dataList=new ArrayList<>();
                dataList.add(0,bailList);
                dataList.add(1,biltyList);

                data.postValue(dataList);
            });

        });


        return data;
//        rapidLineRepository.getAllBilty()
//                .addSnapshotListener((queryDocumentSnapshots, e) -> {
//                    if (e != null)
//                        return;
//
//                    List<Bilty> biltyList = new ArrayList<>();
//
//                    if (!queryDocumentSnapshots.isEmpty()) {
//                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
//                            biltyList.add(doc.toObject(Bilty.class));
//                        }
//                    }
//                    listAllBilty.postValue(biltyList);
//
//                });
//        return listAllBilty;
    }

    public LiveData<Bilty> getBiltyById(String key) {
        MutableLiveData<Bilty> data = new MutableLiveData<>();

        rapidLineRepository.getAllBilty().document(key)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        data.postValue(documentSnapshot.toObject(Bilty.class));
                    }
                });
        return data;
    }

    public void addBilty(Bilty bilty){
        rapidLineRepository.addBilty(bilty);
    }
    public void updateBilty(Bilty bilty){
        rapidLineRepository.updateBilty(bilty);
    }
    public void deleteBilty(String key){
        rapidLineRepository.deleteBilty(key);
    }



}
