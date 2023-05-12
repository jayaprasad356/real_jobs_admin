package com.app.realjobadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.app.realjobadmin.constants.IConstants;
import com.app.realjobadmin.helper.Session;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    Session session;
    Activity activity;
    String link,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity = SplashActivity.this;
        session = new Session(activity);
        handler = new Handler();
        GotoActivity();
    }

    private void GotoActivity()
    {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Session session = new Session(SplashActivity.this);
                if (session.getData(IConstants.LOGIN_TYPE).equals("Admin")){
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }else if (session.getData(IConstants.LOGIN_TYPE).equals("Super Admin")){
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }else if (session.getData(IConstants.LOGIN_TYPE).equals("employee")){
                    Intent intent = new Intent(SplashActivity.this, JoiningActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    Intent intent=new Intent(SplashActivity.this,TabActivity.class);
                    startActivity(intent);
                    finish();

                }



            }
        },2000);
    }
}