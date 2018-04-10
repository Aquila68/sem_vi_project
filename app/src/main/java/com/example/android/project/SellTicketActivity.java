package com.example.android.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.android.project.DatabaseFiles.BackgroundProcess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SellTicketActivity extends AppCompatActivity {

    private EditText etEnterToken;
    private String conId;
    final String del_url ="https://buspaykar.000webhostapp.com/token_del.php";
    private String source,dest,currtime;
    private int cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_ticket);
        etEnterToken=(EditText)findViewById(R.id.et_enter_token);
        Intent intent=getIntent();
        conId=intent.getStringExtra("conId");
    }

    public void sendToken(View view){
        String token=etEnterToken.getText().toString();
        Map<String,String> params=new HashMap<String, String>();
        params.put("token",token);
        params.put("conId",conId);

        final RequestQueue requestQueue= Volley.newRequestQueue(SellTicketActivity.this);

        BackgroundProcess backgroundProcess=new BackgroundProcess(del_url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Amount ",response.toString());
                try {
                    //JSONArray j=new JSONArray(response.toString());
                    //cost=response.get("o")
                    currtime=response.getString("currtime");
                    dest=response.getString("dest");
                    cost=response.getInt("cost");
                    source=response.getString("source");
                    Log.d("at sellticket",currtime);
                    Toast.makeText(SellTicketActivity.this,currtime+" "+dest+" "+cost+" "+source,Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",error.toString());
            }
        });

        requestQueue.start();
        backgroundProcess.setRetryPolicy(new DefaultRetryPolicy(50000,5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(backgroundProcess);

    }


}
