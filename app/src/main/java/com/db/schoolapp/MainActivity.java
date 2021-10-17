package com.db.schoolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.db.schoolapp.Activity.DashboardActivity;
import com.db.schoolapp.Activity.LoginActivity;
import com.db.schoolapp.Constants.StoreDataPrefernce;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        StoreDataPrefernce prefernce = new StoreDataPrefernce(MainActivity.this);
        String userId = prefernce.getUserId();
        Log.d("userId", userId);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (prefernce.getUserId().equals("null")) {
                    Intent intent = new Intent(MainActivity.this,   LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }


            }
        }, 5000);
    }

}