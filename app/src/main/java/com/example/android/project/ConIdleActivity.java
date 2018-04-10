package com.example.android.project;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.project.DatabaseFiles.Bus;
import com.example.android.project.DatabaseFiles.BusRoutesHelper;

public class ConIdleActivity extends AppCompatActivity {

    private BusRoutesHelper busRoutesHelper;
    private Spinner spBusNo;
    //TextView tvdebug;
    private int CURRENT_BUS_NO;
    String conId,pswrd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_idle);

        Intent intent=getIntent();                                          //getting conductorsdetails from previous activity;
        conId=intent.getStringExtra("Id");
        pswrd=intent.getStringExtra("pswrd");

        spBusNo=(Spinner)findViewById(R.id.sp_bus_no);
        //tvdebug=findViewById(R.id.tvdebug);
        new retrieveBusNos().execute();

    }

    private class retrieveBusNos extends AsyncTask<Void,Void,String[]> implements AdapterView.OnItemSelectedListener {
        String[] busesNos;
        @Override
        protected String[] doInBackground(Void... voids) {
            busRoutesHelper=new BusRoutesHelper(ConIdleActivity.this);
            busRoutesHelper.deleteAll();
            SQLiteDatabase db=busRoutesHelper.getWritableDatabase();
            busRoutesHelper.onCreate(db);

            Bus bus1=new Bus(100,"Navy Nagar,Afghan Church,Colaba Causeway,Regal Cinema,C.S.M.T.|");
            BusRoutesHelper busRoutesHelper=new BusRoutesHelper(ConIdleActivity.this);

            busRoutesHelper.addBus(bus1);
            busesNos=busRoutesHelper.allBusNosInDatabase();
            db.close();
            return busesNos;
        }

        @Override
        protected void onPostExecute(String[] busNos) {
           // tvdebug.setText(busNos[0]);
            spBusNo.setOnItemSelectedListener(this);
            ArrayAdapter<String> ar=new ArrayAdapter<String>(ConIdleActivity.this,android.R.layout.simple_spinner_dropdown_item,busNos);
            ar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spBusNo.setAdapter(ar);
        }


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(ConIdleActivity.this,busesNos[position],Toast.LENGTH_LONG).show();
            CURRENT_BUS_NO=Integer.parseInt(busesNos[position]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public void sellTicket(View view){
        Intent intent=new Intent(ConIdleActivity.this,SellTicketActivity.class);
        intent.putExtra("conId",conId);
        intent.putExtra("busNo",CURRENT_BUS_NO);
        startActivity(intent);
        finish();
    }
}
