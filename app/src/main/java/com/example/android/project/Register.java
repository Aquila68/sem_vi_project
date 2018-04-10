package com.example.android.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.android.project.DatabaseFiles.*;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//do verification stuff;
public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText etPswrd,etName,etUserid;
    private Button btRegister;
    private TextView tvErrValidMsg;
    private final String add_url="https://buspaykar.000webhostapp.com/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName=(EditText)findViewById(R.id.et_register_name);
        etUserid=(EditText)findViewById(R.id.et_register_user_id);
        etPswrd=(EditText)findViewById(R.id.et_register_password);
        tvErrValidMsg=(TextView)findViewById(R.id.tv_err_val_msg);
        btRegister=(Button)findViewById(R.id.register_button);
        btRegister.setOnClickListener(Register.this);
    }

    @Override
    public void onClick(View view) {
        if(validation()==true)
            doRegister();
        else
            tvErrValidMsg.setVisibility(TextView.VISIBLE);
    }


    public void doRegister(){
        String name=etName.getText().toString();
        String userId=etUserid.getText().toString();
        String pswrd=etPswrd.getText().toString();
        Map<String,String> params=new HashMap<String, String>();

        params.put("name",name);
        params.put("userId",userId);
        params.put("pswrd",pswrd);

        final RequestQueue requestQueue= Volley.newRequestQueue(Register.this);

        BackgroundProcess backgroundProcess=new BackgroundProcess(add_url, params, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Log.d("Data added",response.toString());
            }
        }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("Data not added",error.toString());
            }
        });

        requestQueue.start();
        backgroundProcess.setRetryPolicy(new DefaultRetryPolicy(50000,5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(backgroundProcess);

    }

    public boolean validation()
    {
        if(etName.getText().equals(null) && etPswrd.getText().equals(null) && etUserid.getText().equals(null)){
            return true;
        }
        else
            return false;
    }
}
