package com.example.quizkart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.quizkart.databinding.ActivityMainBinding;
import com.example.quizkart.utils.Connectivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        if(Connectivity.isNetworkAvailable(this)) {
            int SPLASH_SCREEN_TIME_OUT = 5000;
            new Handler().postDelayed(() -> {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.addAuthStateListener(authStateListener);
            }, SPLASH_SCREEN_TIME_OUT);
        }
        else
        {
            new AlertDialog.Builder(this)
                    .setTitle("Network Problem")
                    .setCancelable(false)
                    .setMessage("You are not connected with internet")
                    .setPositiveButton(getResources().getString(R.string.sign_out_ok), (dialog, which) -> onCreate(savedInstanceState))
                    .setNegativeButton(getString(R.string.sign_out_cancel), (dialog, which) -> dialog.dismiss())
                    .create().show();
        }

    }

    FirebaseAuth.AuthStateListener authStateListener = firebaseAuth -> {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser == null) {
            Intent intent = new Intent(MainActivity.this, OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }
        if (firebaseUser != null) {
            Intent intent = new Intent(MainActivity.this, DashBoardActivity.class);
            startActivity(intent);
            finish();
        }
    };
}