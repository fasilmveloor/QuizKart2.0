package com.example.quizkart;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizkart.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding activitySignUpBinding;

    EditText mEmail, mFullName, mUserName, mPassword, mConfirmPassword;
    Button mSignUpBtn;
    TextView mLoginBtn;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEmail              = findViewById(R.id.inputEmail);
        mFullName           = findViewById(R.id.full_name);
        mUserName           = findViewById(R.id.username);
        mPassword           = findViewById(R.id.inputPassword);
        mConfirmPassword    = findViewById(R.id.confirm_password);
        mSignUpBtn          = findViewById(R.id.btnLogin);
        mLoginBtn           = findViewById(R.id.gotoLogin);

        firebaseAuth    = FirebaseAuth.getInstance();

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText() .toString() .trim();
                String password = mPassword.getText() .toString() .trim();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required");
                    return;
                }
                if(password.length()<6){
                    mPassword.setError("Password must be >= 6 characters");
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                            StartActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }else{

                        }

                    }
                });
            }
        });


        activitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = activitySignUpBinding.getRoot();
        setContentView(view);
        activitySignUpBinding.gotoLogin.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));
    }

    public void slideUp(View view){
        startActivity(new Intent(SignUpActivity.this, WelcomeActivity.class));
        //customType(LoginTestActivity.this,"bottom-to-up");
        this.overridePendingTransition(R.anim.bottom_to_up, R.anim.bottom_to_up2);
    }
}