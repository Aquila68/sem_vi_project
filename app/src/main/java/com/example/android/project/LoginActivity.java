package com.example.android.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.android.project.DatabaseFiles.BackgroundProcess;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private EditText etUserid;
    private EditText etPswrd;
    private Spinner spLoginType;
    private String login_url="https://buspaykar.000webhostapp.com/login.php";
    private String con_login_url="https://buspaykar.000webhostapp.com/conlogin.php";
    private String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserid=(EditText)findViewById(R.id.et_login_user_id);
        etPswrd=(EditText)findViewById(R.id.et_login_password);
        spLoginType=(Spinner)findViewById(R.id.login_type_spinner);
    }


    public void doSubmit(View view){                    //button's reaction after getting clicked
       doLogin();
    }

    public void doLogin(){
        Map<String,String> params=new HashMap<>();
        final String userId=etUserid.getText().toString();
        final String pswrd=etPswrd.getText().toString();
        params.put("Id",userId);
        params.put("pswrd",pswrd);

        if(spLoginType.getSelectedItem().equals("Customer"))
            {
                url=login_url;
            }
        else if (spLoginType.getSelectedItem().equals("Conductor"))
            {
                url=con_login_url;
            }

        Toast.makeText(this,"Kindly wait and dont press the button again",Toast.LENGTH_LONG).show();
        final RequestQueue requestQueue= Volley.newRequestQueue(LoginActivity.this);

        final String finalUrl = url;
        BackgroundProcess backgroundProcess=new BackgroundProcess(url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Intent intent=null;
                Log.d("Successfully logged in",response.toString());
                if(finalUrl ==login_url) {
                    intent = new Intent(LoginActivity.this, IdleActivity.class);
                }
                else if(finalUrl==con_login_url){
                    intent = new Intent(LoginActivity.this, ConIdleActivity.class);
                }
                intent.putExtra("Id",userId);
                intent.putExtra("pswrd",pswrd);
                startActivity(intent);

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
    }
}
