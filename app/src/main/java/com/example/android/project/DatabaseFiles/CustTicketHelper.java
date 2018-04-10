package com.example.android.project.DatabaseFiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class CustTicketHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "custTicketHistory.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME="custTicketTable";
    public static final String COLUMN_ID="Id";
    public static final String COLUMN_SOURCE="Source";
    public static final String COLUMN_DEST="Dest";
    public static final String COLUMN_COST="Cost";
    public static final String COLUMN_TRANS_TIME="TransTime";

    public CustTicketHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CUST_TICKET_TABLE="CREATE TABLE IF NOT EXISTS " +   TABLE_NAME+  "(" +
                //COLUMN_ID +   " INTEGER PRIMARY KEY AUTOINCREMENT, "   +
                COLUMN_SOURCE    +   " TEXT NOT NULL," +
                COLUMN_DEST +" TEXT NOT NULL," +
                COLUMN_COST + " INTEGER NOT NULL," +
                COLUMN_TRANS_TIME+" TEXT NOT NULL);";
        db.execSQL(CREATE_CUST_TICKET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String DROP_TABLE="DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addCustTicket(CustTicket custTicket){                                        //method for adding a bus
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_SOURCE,custTicket.getmSource());
        cv.put(COLUMN_DEST, custTicket.getmDest());
        cv.put(COLUMN_COST, custTicket.getmCost());
        cv.put(COLUMN_TRANS_TIME,custTicket.getmTransTime());

        long i=db.insert(TABLE_NAME,null,cv);
        Log.d("ticket helper",Long.toString(i));
        db.close();
    }


}
