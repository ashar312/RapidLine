package com.project.rapidline.Database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String Database_Name = "SaeedSons.db";
    private static final String TABLE_NAME = "SenderReciever";
    private static final String Company_Name = "CompanyName";
    private static final String Company_Number = "CompanyNumber";
    private static final String City = "City";
    private static final String Address = "Address";
    private static final String PointContactName = "PointOfContactName";
    private static final String PointContactNumber = "PointOfContactNumber";




    public DatabaseHelper(Context context) {
        super(context, Database_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,CompanyName TEXT," +
                "CompanyNumber TEXT,CompanyNumber TEXT,City TEXT,Address TEXT," +
                "PointOfContactName TEXT,PointOfContactNumber TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
