package com.example.quizkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizkart.databinding.ActivityForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    ActivityForgotPasswordBinding activityForgotPasswordBinding;
    private FirebaseAuth mauth;
    EditText emailbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityForgotPasswordBinding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        View view = activityForgotPasswordBinding.getRoot();
        setContentView(view);
        emailbox = activityForgotPasswordBinding.inputEmailForgot;
        mauth = FirebaseAuth.getInstance();
        activityForgotPasswordBinding.backToLogin.setOnClickListener(v -> startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class)));
    }

    public void slideUp(View view){
        String email = emailbox.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplication(), "Enter your mail address", Toast.LENGTH_SHORT).show();
            return;
        }

        mauth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this, "We send you an e-mail", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}