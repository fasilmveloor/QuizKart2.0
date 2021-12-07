package com.example.quizkart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.quizkart.databinding.ActivityTestDetailsBinding;

public class TestDetailsActivity extends AppCompatActivity {

    ActivityTestDetailsBinding activityTestDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String value = getIntent().getStringExtra("name");
        activityTestDetailsBinding = ActivityTestDetailsBinding.inflate(getLayoutInflater());
        View view = activityTestDetailsBinding.getRoot();
        setContentView(view);
        activityTestDetailsBinding.textView5.setText(value);
    }
}