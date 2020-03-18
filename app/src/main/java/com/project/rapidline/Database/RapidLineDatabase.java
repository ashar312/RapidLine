package com.project.rapidline.Database;

import android.content.Context;
import android.os.AsyncTask;

import com.project.rapidline.Database.dao.AdminDao;
import com.project.rapidline.Database.dao.AgentDao;
import com.project.rapidline.Database.dao.BailDao;
import com.project.rapidline.Database.dao.CompanyDao;
import com.project.rapidline.Database.dao.CustomerDao;
import com.project.rapidline.Database.dao.LabourDao;
import com.project.rapidline.Database.dao.PatriDao;
import com.project.rapidline.Database.dao.TransporterDao;
import com.project.rapidline.Database.entity.Admins;
import com.project.rapidline.Database.entity.Agents;
import com.project.rapidline.Database.entity.Bails;
import com.project.rapidline.Database.entity.CompanyDetails;
import com.project.rapidline.Database.entity.Customers;
import com.project.rapidline.Database.entity.KindOfItem;
import com.project.rapidline.Database.entity.Labours;
import com.project.rapidline.Database.entity.Patri;
import com.project.rapidline.Database.entity.Transporters;
import com.project.rapidline.utils.Converters;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Admins.class, Agents.class, Bails.class, CompanyDetails.class, Customers.class,
        KindOfItem.class, Labours.class, Patri.class, Transporters.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class RapidLineDatabase extends RoomDatabase {

    private static RapidLineDatabase instance;

//    public abstract CityDao getCityDao();
    //Dao's
    public abstract AdminDao getAdminDao();
    public abstract AgentDao getAgentDao();
    public abstract BailDao getBailDao();
    public abstract CompanyDao getCompanyDao();
    public abstract CustomerDao getCustomerDao();
    public abstract LabourDao getLabourDao();
    public abstract PatriDao getPatriDao();
    public abstract TransporterDao getTransporterDao();


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

        private AdminDao adminDao;
        private CompanyDao companyDao;

        public InitializeDataAsyncTask(RapidLineDatabase rapidLineDatabase){
            adminDao=rapidLineDatabase.getAdminDao();
            companyDao=rapidLineDatabase.getCompanyDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            CompanyDetails company1detail=new CompanyDetails();
            company1detail.setCompname("Saeed Sons");
            company1detail.setCompno("023123-232");
            company1detail.setEmail("abs@gmail.com");

            CompanyDetails company2detail=new CompanyDetails();
            company2detail.setCompname("Rapid Lines");
            company2detail.setCompno("023123-232");
            company2detail.setEmail("abs@gmail.com");

            companyDao.addCompany(company1detail);
            companyDao.addCompany(company2detail);

            Admins admin1=new Admins();
            admin1.setUsername("admin");
            admin1.setPass("admin");
            admin1.setCompanyId(1);

            adminDao.addAdmin(admin1);

            return null;
        }
    }

}
