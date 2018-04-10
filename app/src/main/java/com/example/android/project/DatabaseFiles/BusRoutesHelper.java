package com.example.android.project.DatabaseFiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by DE on 11-02-2018.
 */

public class BusRoutesHelper extends SQLiteOpenHelper{

    private Context context;
    private static final String DATABASE_NAME = "busStopHelper.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME="busRouteTable";
    private static final String COLUMN_BUS_NUMBER="busNumber";
    private static final String COLUMN_BUS_ROUTE="busStops";

    public BusRoutesHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {                           //for creating table
        String CREATE_BUS_ROUTES_TABLE="CREATE TABLE IF NOT EXISTS " +   TABLE_NAME+  "(" +
                COLUMN_BUS_NUMBER +   " INTEGER PRIMARY KEY, "   +
                COLUMN_BUS_ROUTE    +   " TEXT);";
        db.execSQL(CREATE_BUS_ROUTES_TABLE);
        Log.d("Database Stuff","BusRouteTable createddddddddddddddd");



        /* Bus bus1=new Bus(100,"Navy Nagar,Colaba Post Office,St. Xaviers College|");
            busRoutesHelper.addBus(bus1);
            Bus bus2=new Bus(101,"Navy Nagar,Colaba Post Office,St. Xaviers College|");
            busRoutesHelper.addBus(bus2);
        */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {          //when dataase is being updated
        final String DROP_TABLE="DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }


    public void addBus(Bus bus){                                        //method for adding a bus
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_BUS_NUMBER,bus.getmBusNumber());
        cv.put(COLUMN_BUS_ROUTE, String.valueOf(bus.getmBusRoute()));

        db.insert(TABLE_NAME,null,cv);

    }

    public void updateBus(Bus bus){                                     //method for updating a bus
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_BUS_NUMBER,bus.getmBusNumber());
        cv.put(COLUMN_BUS_ROUTE, String.valueOf(bus.getmBusRoute()));
        db.update(TABLE_NAME,cv,COLUMN_BUS_NUMBER+"=?",new String[]{String.valueOf(bus.getmBusNumber())});
        db.close();
    }

    public void deleteBus(int busNo){                                      //method for deleting a bus
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,COLUMN_BUS_NUMBER+"=?",
                new String[]{   String.valueOf( busNo)  });
        db.close();
    }


    public void deleteAll(){                                                     //method to delete all rows in table
        SQLiteDatabase db=this.getWritableDatabase();
        String DELETE_QUERY="DELETE FROM "+TABLE_NAME;
        db.execSQL(DELETE_QUERY);
        db.close();
    }

    public String[] allBusNosInDatabase(){                                   //returns list of all buses in table
        List<Bus> busList=new LinkedList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT "+COLUMN_BUS_NUMBER+" FROM "+TABLE_NAME;
        Cursor cr=db.rawQuery(query,null);
        String[] busNo=new String[cr.getCount()];

        if(cr.moveToFirst()){
            int i=0;
            do{
                busNo[i]=Integer.toString(cr.getInt(0));
                i++;
            }while (cr.moveToNext());
        }
        db.close();
        return busNo;
    }
    public String getBusAndStops(int busId){                                      //method for getting stops of a busnumber
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cr=db.query(TABLE_NAME,
                new String[]{ COLUMN_BUS_ROUTE},
                COLUMN_BUS_NUMBER+"=?",
                new String[]{ String.valueOf( busId )},
                null,
                null,
                null
        );
        /*if(cr!=null){
            cr.moveToFirst();

        }*/

        if(cr.moveToFirst()){
            return cr.getString(0);
        }
        else
            return "Bus not found";
    }
}



