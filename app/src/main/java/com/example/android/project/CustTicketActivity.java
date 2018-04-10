package com.example.android.project;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.android.project.DatabaseFiles.BackgroundProcess;
import com.example.android.project.DatabaseFiles.CustTicket;
import com.example.android.project.DatabaseFiles.CustTicketHelper;
import com.example.android.project.DatabaseFiles.DistbwBusStops;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

public class CustTicketActivity extends AppCompatActivity {

    private EditText etFrom,etTo;
    private TextView tvErrorFrom,tvErrorTo,tvCost,tvToken;
    final String add_url="https://buspaykar.000webhostapp.com/trans_add.php";
    final String check_url="https://buspaykar.000webhostapp.com/trans_check.php";
    private String source,dest,cost,time;
    CustTicketHelper custTicketHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_ticket);
        etFrom=(EditText)findViewById(R.id.et_source);
        etTo=(EditText)findViewById(R.id.et_dest);
        tvErrorFrom=(TextView)findViewById(R.id.tv_err_source);
        tvErrorTo=(TextView)findViewById(R.id.tv_err_dest);
        tvCost=(TextView)findViewById(R.id.tv_cost);
        tvToken=(TextView)findViewById(R.id.tv_token);
    }


    public void genToken(View view){                                                    //onClick method
        DistbwBusStops var=new DistbwBusStops();
        Intent intent=getIntent();
        String cust_id=intent.getStringExtra("userId");
        String pswrd=intent.getStringExtra("pswrd");

        source=etFrom.getText().toString();
        dest=etTo.getText().toString();

        String x[][]=var.bus100Matrix;
        cost=Integer.toString(var.costCalc(var.distCalc(x, source, dest)));             //calculating cost of trip
        tvCost.setText(cost);
        putInTable(cust_id);

        Handler handler=new Handler();
        handler.postDelayed(receiveTicket(cust_id),60000);

        //handler.postDelayed(finishActivity(),90000);

    }

    public void putInTable(String cust_id){                                              //method to send request to generate a ticket
                                                                                            // request
        Map<String,String> params=new HashMap<String, String>();
        params.put("cust_id",cust_id);
        params.put("source",source);
        params.put("dest",dest);
        params.put("cost",cost);
        final RequestQueue requestQueue= Volley.newRequestQueue(CustTicketActivity.this);

        BackgroundProcess backgroundProcess=new BackgroundProcess(add_url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Data added",response.toString());
                try {
                    final String token=Integer.toString(response.getInt("token"));
                    //Toast.makeText(CustTicketActivity.this,token,Toast.LENGTH_LONG).show();
                    tvToken.setText(token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Data not added",error.toString());
            }
        });

        requestQueue.start();
        backgroundProcess.setRetryPolicy(new DefaultRetryPolicy(7000,4,
                2));
        requestQueue.add(backgroundProcess);
    }

    public Runnable receiveTicket(String cust_id){                                         //method containing code for checking
        final String userId=cust_id;                                                       //whether conductor received payment or not.
        Runnable runnnn=new Runnable() {
            @Override
            public void run() {

                Map<String,String> params=new HashMap<String, String>();
                params.put("cust_id",userId);
                final RequestQueue requestQueue= Volley.newRequestQueue(CustTicketActivity.this);
                BackgroundProcess backgroundProcess=new BackgroundProcess(check_url, params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            time=response.getString("currtime");
                            cost=response.getString("cost");
                            source=response.getString("source");
                            dest=response.getString("dest");
                            Log.d("at sellticket",time+cost+source+dest);
                            Toast.makeText(CustTicketActivity.this,time+cost+ source+dest,Toast.LENGTH_LONG).show();

                            if(time!=null)
                            {
                                custTicketHelper=new CustTicketHelper(CustTicketActivity.this);
                                db=custTicketHelper.getWritableDatabase();
                                           //saving the ticket in local SQLite db
                                custTicketHelper.onCreate(db);
                                CustTicket custTicket=new CustTicket(source,dest,Integer.parseInt(cost),time);
                                custTicketHelper.addCustTicket(custTicket);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Data not added",error.toString());
                    }
                });

                requestQueue.start();
                backgroundProcess.setRetryPolicy(new DefaultRetryPolicy(7000,4,
                        2));
                requestQueue.add(backgroundProcess);
            }
        };

        return runnnn;
    }

    public Runnable finishActivity(){                                         //method containing code for checking
        Runnable runnnn=new Runnable() {
            @Override
            public void run() {
                finish();

            }
        };

        return runnnn;
    }



}

