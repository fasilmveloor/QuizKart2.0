package com.example.quizkart;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizkart.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding activitySignUpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = activitySignUpBinding.getRoot();
        setContentView(view);
    }

    public void slideUp(View view){
        //startActivity(new Intent(SignUpActivity.this, WelcomeActivity.class));
        //customType(LoginTestActivity.this,"bottom-to-up");
        //this.overridePendingTransition(R.anim.bottom_to_up, R.anim.bottom_to_up2);
    }
}