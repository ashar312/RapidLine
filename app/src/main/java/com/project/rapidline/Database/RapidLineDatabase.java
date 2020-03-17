package com.project.rapidline.Database;

import android.content.Context;
import android.os.AsyncTask;

import com.project.rapidline.Database.entity.Admins;
import com.project.rapidline.utils.Converters;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Admins.class},version = 1)
@TypeConverters(Converters.class)
public abstract class RapidLineDatabase extends RoomDatabase {

    private static RapidLineDatabase instance;

//    public abstract CityDao getCityDao();

    public static synchronized RapidLineDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context,RapidLineDatabase.class,"rapidline_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    //to initialize the database with data
    private static RoomDatabase.Callback callback=new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InitializeDataAsyncTask(instance).execute();

            //get city
//            List<Cities> cityList=instance.getCityDao().getCities();
//            for (Cities city:cityList) {
//                Log.v("city",city.city_name);
//            }

        }
    };

    //Asyntask used to initialize
    private static class InitializeDataAsyncTask extends AsyncTask<Void,Void,Void> {


        public InitializeDataAsyncTask(RapidLineDatabase rapidLineDatabase){

        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }

}
