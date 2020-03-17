package com.project.rapidline.viewmodel;

import android.app.Application;

import com.project.rapidline.repository.RapidLineRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class DataViewModel extends AndroidViewModel {

    private RapidLineRepository rapidLineRepository;
//    private LiveData<List<Cities>> listAllCities;

    public DataViewModel(@NonNull Application application) {
        super(application);

        rapidLineRepository=new RapidLineRepository(application);
//        listAllCities=rapidLineRepository.getAllCities();
    }

//    public LiveData<List<Cities>> getListAllCities() {
//        return listAllCities;
//    }

}
