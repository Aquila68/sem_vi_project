package com.example.android.project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.project.DatabaseFiles.CustTicketHelper;
import com.example.android.project.Others.ListAdd;

import java.util.ArrayList;

public class CustTransHistoryActivity extends AppCompatActivity {


    SQLiteDatabase db;
    CustTicketHelper custTicketHelper;
    Cursor cursor;
    ListAdd listAdd;
    ListView listView;
    TextView tv;
    ArrayList<Integer> ID_Array;
    ArrayList<Integer> COST_Array;
    ArrayList<String> SOURCE_Array;
    ArrayList<String> DEST_Array;
    ArrayList<String> TRANSTIME_Array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_trans_history);

        listView=(ListView)findViewById(R.id.lt1);
        //tv=(TextView)findViewById(R.id.tv_lol);
        ID_Array=new ArrayList<>();
        SOURCE_Array=new ArrayList<>();
        DEST_Array=new ArrayList<>();
        COST_Array=new ArrayList<>();
        TRANSTIME_Array=new ArrayList<>();


        custTicketHelper=new CustTicketHelper(this);
        db = custTicketHelper.getWritableDatabase();
        /*db.execSQL("DELETE from "+custTicketHelper.TABLE_NAME +";");
        Log.d("DELEte from custtichist","done");*/

        ShowSQLiteDBdata();



       /*
        cursor = db.rawQuery("SELECT * FROM "+custTicketHelper.TABLE_NAME+";", null);
        String x="";
        if (cursor.moveToFirst())
            do {
                x=x+" "+cursor.getString(cursor.getColumnIndex(custTicketHelper.COLUMN_TRANS_TIME));
            } while (cursor.moveToNext());

            tv.setText(x);*/
        db.close();

    }


    private void ShowSQLiteDBdata() {

        db = custTicketHelper.getWritableDatabase();

        cursor = db.rawQuery("SELECT * FROM "+custTicketHelper.TABLE_NAME+";", null);

        ID_Array.clear();
        SOURCE_Array.clear();
        DEST_Array.clear();
        COST_Array.clear();
        TRANSTIME_Array.clear();
        Log.d("CustTransHistory","omikmki"/*+cursor.getString(cursor.getColumnIndex(custTicketHelper.COLUMN_TRANS_TIME))*/);
        if (cursor.moveToFirst()) {
            do {

                ID_Array.add(cursor.getInt(cursor.getColumnIndex(custTicketHelper.COLUMN_ID)));

                SOURCE_Array.add(cursor.getString(cursor.getColumnIndex(custTicketHelper.COLUMN_SOURCE)));

                DEST_Array.add(cursor.getString(cursor.getColumnIndex(custTicketHelper.COLUMN_DEST)));

                COST_Array.add(cursor.getInt(cursor.getColumnIndex(custTicketHelper.COLUMN_COST)));

                TRANSTIME_Array.add(cursor.getString(cursor.getColumnIndex(custTicketHelper.COLUMN_TRANS_TIME)));

               Log.d("CustTransHistory","asdas"/*+cursor.getString(cursor.getColumnIndex(custTicketHelper.COLUMN_TRANS_TIME))*/);
            } while (cursor.moveToNext());
        }

        listAdd = new ListAdd(CustTransHistoryActivity.this,

                ID_Array,
                SOURCE_Array,
                DEST_Array,
                COST_Array,
                TRANSTIME_Array
        );

        listView.setAdapter(listAdd);

        cursor.close();
    }
}
