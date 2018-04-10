package com.example.android.project;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.project.DatabaseFiles.*;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

public class IdleActivity extends AppCompatActivity {

    private TextView tvAmnt;
    private int amnt;
    private String amnt_url="https://buspaykar.000webhostapp.com/amntcheck.php";
    private BusRoutesHelper sqlhelper;
    private BusStopHelper busStopHelper;
    private String userId;
    private String pswrd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idle);
        tvAmnt=(TextView)findViewById(R.id.tv_amnt_display);
        Intent intent=getIntent();                                          //getting userdetails from previous activity;
        userId=intent.getStringExtra("Id");
        pswrd=intent.getStringExtra("pswrd");
        refreshAmnt();
        //Bus bus1=new Bus(100,"Navy nagar,Army area,Afghan Church,Colaba Post Office|");
        //sqlhelper=new BusRoutesHelper(this);
        // sqlhelper.addBus(bus1);
        //tvAmnt.setText(sqlhelper.getBusAndStops(100));          //display it using in something like recyclerview
        new BackgroundSQLiteDatabaseTask().execute();
    }


    public int refreshAmnt(){                                               //refreshing money left in wallet
        final RequestQueue requestQueue= Volley.newRequestQueue(IdleActivity.this);
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        params.put("pswrd",pswrd);

        BackgroundProcess backgroundProcess=new BackgroundProcess(amnt_url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Successfully logged in",response.toString());
                try {
                    tvAmnt.setText(Integer.toString(response.getInt("amnt")));
                    amnt=response.getInt("amnt");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error loggin in ",error.toString());
            }
        });

        requestQueue.start();
        backgroundProcess.setRetryPolicy(new DefaultRetryPolicy(50000,5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(backgroundProcess);
        return amnt;
    }

    public void buyButton(View view){                                                           //onclick method for payment option
        Intent intent=new Intent(IdleActivity.this,CustTicketActivity.class);
        intent.putExtra("userId",userId);
        intent.putExtra("pswrd",pswrd);
        startActivity(intent);

    }

    public void refresh(View view){
        refreshAmnt();
    }
    public void transHistory(View view){                                                            //onCLick method to view tickets bought
        Intent intent=new Intent(IdleActivity.this,CustTransHistoryActivity.class);     //so far
        startActivity(intent);
    }

    private class BackgroundSQLiteDatabaseTask extends AsyncTask<Void, Void, Void> {             //class for doing database tasks

        @Override
        protected Void doInBackground(Void... voids) {

            //ArrayList<String> x=busStopHelper.getBuses("St. Xavier's College","Navy Nagar");
            return null;

        }

        @Override
        protected void onPostExecute(Void avoid) {
            //tvAmnt.setText(busNo);
        }
    }







}
