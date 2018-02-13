package com.example.monika.smartstreetassign;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by monika on 10/24/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="register.db";
    public static final String TABLE_NAME="registeration";
    public static final String COL_1="ID";
    public static final String COL_2="FirstName";
    public static final String COL_3="Phone";
    public static final String COL_4="Email";
    public static final String COL_5="Password";


    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,FirstName TEXT,Phone TEXT,Email TEXT,Password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME); //Drop older table if exists
        onCreate(db);

    }

}
