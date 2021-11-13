package com.example.quizkart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.quizkart.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = activityLoginBinding.getRoot();
        setContentView(view);
        activityLoginBinding.gotoRegister.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));
        activityLoginBinding.forgotPassword.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class)));
    }
    public void slideUp(View view){
        //startActivity(new Intent(LoginTestActivity.this, WelcomeActivity.class));
        //customType(LoginTestActivity.this,"bottom-to-up");
        //this.overridePendingTransition(R.anim.bottom_to_up, R.anim.bottom_to_up2);
    }
}