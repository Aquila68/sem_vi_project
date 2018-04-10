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
 * Created by DE on 13-02-2018.
 */

public class BusStopHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "busStopHelper.db";
    private static final String TABLE_NAME = "busStopTable";
  //  private static final String KEY_ID="id";
    private static final String COLUMN_BUS_STOP_NAME="busStopName";
    private static final String COLUMN_BUSES_WHICH_STOP="busesWhichStopHere";

    public BusStopHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        BusStopHelper busStopHelper=new BusStopHelper(context);
        busStopHelper.deleteAll();
        BusStop busStop=new BusStop("Afghan Church","100|");
        busStopHelper.addBusStop(busStop);
        BusStop busStop1=new BusStop("Navy Nagar","100|");
        busStopHelper.addBusStop(busStop1);
        BusStop busStop2=new BusStop("Colaba Causeway","100|");
        busStopHelper.addBusStop(busStop2);
        BusStop busStop3=new BusStop("Regal Cinema","100|");
        busStopHelper.addBusStop(busStop3);
        BusStop busStop4=new BusStop("C.S.M.T.","100");
        busStopHelper.addBusStop(busStop4);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {                                       //madartory to override method for table creation
        String CREATE_BUS_STOP_TABLE="CREATE TABLE "    +   TABLE_NAME  +"("
              //  +   KEY_ID  +   " INT PRIMARY KEY AUTOINCREMENT,"
                +   COLUMN_BUS_STOP_NAME    +   " TEXT,"
                +   COLUMN_BUSES_WHICH_STOP +   " TEXT);";
        db.execSQL(CREATE_BUS_STOP_TABLE);
        Log.d("Database Stuff","busStopTable created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {      //mandatory to override method to recreate table
        String DROP_TABLE="DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }

    public void addBusStop(BusStop busStop){                                        //method for adding a bus
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_BUS_STOP_NAME,busStop.getmBusStopName());
        cv.put(COLUMN_BUSES_WHICH_STOP, String.valueOf(busStop.getmBusesWhichStopHere()));

        db.insert(TABLE_NAME,null,cv);
        db.close();
    }

    public void deleteBusStop(BusStop busStop){                                      //method for deleting a bus
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,COLUMN_BUS_STOP_NAME+"=?",
                new String[]{   busStop.getmBusStopName()  });
        db.close();
    }

    public void updateBusStop(BusStop busStop){                                     //method for updating a bus
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_BUS_STOP_NAME,busStop.getmBusStopName());
        cv.put(COLUMN_BUSES_WHICH_STOP, busStop.getmBusesWhichStopHere());
        db.update(TABLE_NAME,cv,COLUMN_BUS_STOP_NAME+"=?",new String[]{busStop.getmBusStopName()});
        db.close();
    }

    public List<BusStop> allBusStopsInDatabase(){                                   //returns list of all busStops
        List<BusStop> busStopList=new LinkedList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME;
        Cursor cr=db.rawQuery(query,null);
        BusStop busStop=null;

        if(cr.moveToFirst()){
            do{
                busStop=new BusStop();
                busStop.setmBusStopName(cr.getString(0));
                busStop.setmBusesWhichStopHere(cr.getString(1));
                busStopList.add(busStop);
            }while (cr.moveToNext());
        }
        return busStopList;
    }

    public void deleteAll(){                                                                   //method to delete all rows in table
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }

    public ArrayList<String> getBuses(String fromBusStop,String toBusStop){                   //method for searching bus from A to B
        SQLiteDatabase db=this.getReadableDatabase();                                         //content related to it from here
        Cursor cr=db.query(TABLE_NAME,
                new String[]{ COLUMN_BUSES_WHICH_STOP},
                COLUMN_BUS_STOP_NAME+"=?",
                new String[]{ fromBusStop },
                null,
                null,
                null
        );
        cr.moveToFirst();
        String FromBusesString=cr.getString(0);

        Cursor cr1=db.query(TABLE_NAME,
                new String[]{ COLUMN_BUSES_WHICH_STOP},
                COLUMN_BUS_STOP_NAME+"=?",
                new String[]{ toBusStop },
                null,
                null,
                null
        );
        cr1.moveToFirst();
        String ToBusesString=cr1.getString(0);
        ArrayList<String> toBuses=StringtoArrayList(ToBusesString);
        ArrayList<String> fromBuses=StringtoArrayList(FromBusesString);
        ArrayList<String> CommonBusList=new ArrayList<>();

        for(String i:toBuses){
             for(String j:fromBuses) {
                 if (i.equals(j) && !i.equals(null))
                     CommonBusList.add(i);

             }
         }
        if(CommonBusList.isEmpty())
            CommonBusList.add("No buses between given stops");
        return CommonBusList;
    }

    private ArrayList<String> StringtoArrayList(String buses){
        ArrayList<String> busList=new ArrayList<>();
        int i=0,start=0,end=0;

        do{
            if(buses.charAt(i)!=',' && buses.charAt(i)!='|'){
                end++;
            }
            else{
                busList.add(buses.substring(start,end));
                end++;
                start=end;
            }
            i++;
        }while(buses.charAt(i-1)!='|');
        return busList;
    }                                                                                        //till here

}
