package com.example.quizkart;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizkart.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding activitySignUpBinding;
    EditText emailInput, passwordInput, password_confirm;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = activitySignUpBinding.getRoot();
        setContentView(view);
        emailInput = activitySignUpBinding.inputEmail;
        passwordInput = activitySignUpBinding.inputPassword;
        password_confirm = activitySignUpBinding.confirmPassword;
        auth = FirebaseAuth.getInstance();
        activitySignUpBinding.gotoLogin.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));
    }

    public void slideUp(View view){
        String email, password, confirmPassword;
        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();
        confirmPassword = password_confirm.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Please enter your E-mail address",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Please enter your Password",Toast.LENGTH_LONG).show();
        }
        if (password.length() == 0){
            Toast.makeText(getApplicationContext(),"Please enter your Password",Toast.LENGTH_LONG).show();
        }
        if (password.length()<8){
            Toast.makeText(getApplicationContext(),"Password must be more than 8 digit",Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(confirmPassword)){
            Toast.makeText(getApplicationContext(),"Please enter password again",Toast.LENGTH_LONG).show();
        }
        if(!TextUtils.equals(password, confirmPassword)) {
            Toast.makeText(getApplicationContext(),"password did not match"+password+":"+confirmPassword,Toast.LENGTH_LONG).show();
        }
        else{
            auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "ERROR",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(SignUpActivity.this, "Registration Successfull",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignUpActivity.this, EditProfileActivity.class));
                                overridePendingTransition(R.anim.bottom_to_up, R.anim.bottom_to_up2);
                                finish();
                            }
                        }
                    });
        }
    }
}