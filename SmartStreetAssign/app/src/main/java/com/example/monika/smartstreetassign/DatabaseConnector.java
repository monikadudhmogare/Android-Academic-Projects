package com.example.monika.smartstreetassign;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by monika on 10/25/17.
 */

public class DatabaseConnector extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    //define the name of database;

    private static final String DATABASE_NAME = "smartTreeDB.db";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";

    //create a comment table to save the user's comment
    private static final String TABLE_COMMENT= "comment";
    private static final String COLUMN_COMMENT = "comment";
    private static final String COLUMN_RATING = "ranking";

    SQLiteDatabase db;

    public DatabaseConnector(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Query to create comment table
        String CREATE_COMMENT_TABLE = "CREATE TABLE " +
                TABLE_COMMENT + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_COMMENT
                + " TEXT," + COLUMN_RATING + " TEXT," + COLUMN_USERNAME+" TEXT"+")";


        db.execSQL(CREATE_COMMENT_TABLE);

        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS ";


        db.execSQL(query+TABLE_COMMENT);
        this.onCreate(db);
    }

    //insert user's comment in database
    public void insertComment(Comments comment){

        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COMMENT, comment.getComment());
        contentValues.put(COLUMN_RATING, comment.getRating());
        contentValues.put(COLUMN_USERNAME, comment.getUsername());

        db.insert(TABLE_COMMENT, null, contentValues);
        db.close();
    }


}
