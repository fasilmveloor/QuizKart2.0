package com.example.quizkart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizkart.OnBoarding.OnBoardingAdapter;
import com.example.quizkart.OnBoarding.OnBoardingPageTransformer;
import com.example.quizkart.databinding.ActivityOnBoardingBinding;

public class OnBoardingActivity extends AppCompatActivity {

    ActivityOnBoardingBinding activityOnBoardingBinding;
    OnBoardingAdapter onBoardingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOnBoardingBinding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        View view = activityOnBoardingBinding.getRoot();
        setContentView(view);

        makeStatusBarTransparent();
        onBoardingAdapter = new OnBoardingAdapter(this);
        activityOnBoardingBinding.onBoardingViewPager.setAdapter(onBoardingAdapter);
        activityOnBoardingBinding.onBoardingViewPager.setPageTransformer(false, new OnBoardingPageTransformer());

    }

    public void nextPage(View view) {
        if (view.getId() == R.id.button2) {
            if (activityOnBoardingBinding.onBoardingViewPager.getCurrentItem() < onBoardingAdapter.getCount() - 1) {
                activityOnBoardingBinding.onBoardingViewPager.setCurrentItem(activityOnBoardingBinding.onBoardingViewPager.getCurrentItem() + 1, true);
            }
            else {
                Intent i = new Intent(OnBoardingActivity.this, LoginActivity.class);
                startActivity(i);
            }
        }
    }

    private void makeStatusBarTransparent() {

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }
}