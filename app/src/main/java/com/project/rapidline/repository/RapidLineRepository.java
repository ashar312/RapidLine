package com.project.rapidline.repository;

import android.app.Application;

public class RapidLineRepository {

    private Application application;
//    private CityDao cityDao;


    public RapidLineRepository(Application application){
        this.application=application;

//        RapidLineDatabase rapidLineDatabase= RapidLineDatabase.getInstance(application);
//        cityDao=rapidLineDatabase.getCityDao();
    }

//
//    public LiveData<List<Cities>> getAllCities(){return cityDao.getCities();}

}
