package com.example.quizkart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.quizkart.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
        int SPLASH_SCREEN_TIME_OUT = 5000;
        new Handler().postDelayed(() -> {
            Intent i=new Intent(MainActivity.this, OnBoardingActivity.class);

            startActivity(i); //Invoke second Activity
            finish();//the current activity will get finished.
        }, SPLASH_SCREEN_TIME_OUT);
    }
}