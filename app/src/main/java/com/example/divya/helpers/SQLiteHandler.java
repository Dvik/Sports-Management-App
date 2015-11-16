package com.example.divya.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.divya.models.Notify;

public class SQLiteHandler extends SQLiteOpenHelper {
	 
    private static final String TAG = SQLiteHandler.class.getSimpleName();
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;
 
    // Database Name
    private static final String DATABASE_NAME = "sportsdatabase";
 
    
    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_LOGIN_TABLE ="CREATE TABLE userinfo ("+
                "name text ,"+
                "roll text ,"+
                "contact text ,"+
                "year text ,"+
                "sid text ,"+
                "designation text )";

        String NOTIFY_TABLE ="CREATE TABLE notify ("+
                "nid text ,"+
                "sport text ,"+
                "details text ,"+
                "date text ,"+
                "time text ,"+
                "status text )";


        db.execSQL(CREATE_LOGIN_TABLE);
        db.execSQL(NOTIFY_TABLE);

        Log.d(TAG, "Database tables created");
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS userinfo");
        db.execSQL("DROP TABLE IF EXISTS notify");

        // Create tables again
        onCreate(db);
    }
 

    public void addUser(String name,String roll,String contact,String year,String sid,String designation)
    {
    	
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        

        values.put("name", name);
        values.put("roll", roll);
        values.put("contact", contact);
        values.put("year", year);
        values.put("sid", sid);
        values.put("designation", designation);

        long sql_id = db.insert("userinfo", null, values);
        db.close(); // Closing database connection
 
        Log.d(TAG, "New user inserted into sqlite: " + sql_id);
    }


    public void addNotification(String id, String sports, String details, String date, String time, boolean important)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put("nid", id);
        values.put("sport", sports);
        values.put("details", details);
        values.put("date", date);
        values.put("time", time);
        if(important) {
            values.put("status", "yes");
        }
        else
            values.put("status", "no");



        long sql_id = db.insert("notify", null, values);
        db.close(); // Closing database connection

    }


    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM userinfo";
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(cursor.getColumnIndex("name")));
            user.put("roll", cursor.getString(cursor.getColumnIndex("roll")));
            user.put("contact", cursor.getString(cursor.getColumnIndex("contact")));
            user.put("year", cursor.getString(cursor.getColumnIndex("year")));
            user.put("sid", cursor.getString(cursor.getColumnIndex("sid")));
            user.put("designation", cursor.getString(cursor.getColumnIndex("designation")));

           
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());
 
        return user;
    }

    public ArrayList<Notify> getNotifyDetails() {
        ArrayList<Notify> list = new ArrayList<Notify>();
        String selectQuery = "SELECT  * FROM notify";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor .moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                String nid = cursor.getString(cursor.getColumnIndex("nid"));
                String sport = cursor.getString(cursor.getColumnIndex("sport"));
                String details = cursor.getString(cursor.getColumnIndex("details"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String time = cursor.getString(cursor.getColumnIndex("time"));


                list.add(new Notify(nid,sport,details,date,time,true));
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();
        // return user

        return list;
    }


    public int getRowCount() {
        String countQuery = "SELECT  * FROM userinfo";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
 
        // return row count
        return rowCount;
    }

    public int getNotificationRowCount() {
        String countQuery = "SELECT  * FROM notify";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }
    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        if(db!=null)
        {
        	
        
        	db.delete("userinfo", null, null);
        	db.close();
        }
 
        Log.d(TAG, "Deleted all user info from sqlite");
    }
    public void deleteNotifications() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        if(db!=null)
        {


            db.delete("notify", null, null);
            db.close();
        }

        Log.d(TAG, "Deleted all notifications info from sqlite");
    }

    public void deleteThisNotification(String nid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            db.delete("notify", "nid = ?", new String[] { nid });
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            db.close();
        }
    }

}