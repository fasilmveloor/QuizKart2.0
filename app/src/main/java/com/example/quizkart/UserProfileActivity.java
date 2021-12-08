package com.example.quizkart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.quizkart.databinding.ActivityUserProfileBinding;
import com.example.quizkart.presenter.ProfilePresenter;

public class UserProfileActivity extends AppCompatActivity {

    ActivityUserProfileBinding activityUserProfileBinding;
    // UI Elements
    private ImageView mImgUserPic;
    private EditText mEtSlackHandle;
    private ImageButton mBtnEditProfile;
    private EditText mEtUserName;
    private TextView mTvUserEmail;
    private ProgressBar mProgressBar;
    // UI element ends

    private Bundle extras;
    
    ProfilePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.anim_nothing);
        activityUserProfileBinding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        View view = activityUserProfileBinding.getRoot();
        setContentView(view);
        extras = getIntent().getExtras();
        initializeGUI();
        //mPresenter.start(extras);
    }

    private void initializeGUI() {
        mImgUserPic = activityUserProfileBinding.imgUserPic;
        mEtSlackHandle = activityUserProfileBinding.tietSlackHandle;
        mBtnEditProfile = activityUserProfileBinding.btnEditProfile;
        mEtUserName = activityUserProfileBinding.etUserName;
        mTvUserEmail = activityUserProfileBinding.tvEmailProfile;
        mProgressBar = activityUserProfileBinding.pbProfile;
        mProgressBar.setIndeterminate(true);
    }

    public void loadUserPic(String picUrl) {
        if (picUrl == null) {
            return;
        }
        Glide.with(getApplicationContext()).load(picUrl).apply(RequestOptions.circleCropTransform()).into(mImgUserPic);
    }

    public void loadUserName(String userName) {
        if (userName == null) {
            return;
        }
        mEtUserName.setText(userName);
    }

    public void loadSlackHandle(String slackHandle) {
        if (slackHandle != null && !slackHandle.isEmpty()) {
            mEtSlackHandle.setText(slackHandle);
        }
    }

    public void loadEmailAddress(String emailAddress) {
        mTvUserEmail.setText(emailAddress);
    }

    public void loadUserTrack(String userTrack) {
        if (userTrack == null) {
            return;
        }
//        mCourseTrack.set.setText(userTrack);
    }

    public void onPictureUploadError() {
        Toast.makeText(this, getString(R.string.error_uploading_picture), Toast.LENGTH_SHORT).show();
    }

    public void onSaveError() {
        Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
    }

    public void onProfileSaved() {
        Toast.makeText(this, getString(R.string.profile_saved_successfully), Toast.LENGTH_SHORT).show();
        // Navigate to home activity
        Intent homeIntent = new Intent(this, DashBoardActivity.class);
        if (extras != null) {
            homeIntent.putExtras(extras);
        }
        startActivity(homeIntent);
        this.finish();
    }




    public void showLoading() {

        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {

        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }


}