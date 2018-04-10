package com.example.android.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class FullscreenActivity extends AppCompatActivity {

    private Button registerButton;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        //button setup for login button
        loginButton=(Button)findViewById(R.id.sign_in);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(FullscreenActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });

        //button setup for register button
        registerButton=(Button)findViewById(R.id.sign_up);
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(FullscreenActivity.this,Register.class);
                startActivity(intent);
                finish();

            }
        });




    }








}
