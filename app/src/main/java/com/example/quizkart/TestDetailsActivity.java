package com.example.quizkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizkart.databinding.ActivityTestDetailsBinding;

import com.example.quizkart.models.QuizResult;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class TestDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public static String QUIZ_ID ;
    ActivityTestDetailsBinding activityTestDetailsBinding;
    private TextView mTvQuizTitle;
    private TextView mTvQuizDescription;
    private TextView mTvQuizAttemptedStatus;
    private FloatingActionButton mFabStart;
    private DatabaseReference databaseReference;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QUIZ_ID = getIntent().getStringExtra("name");
        activityTestDetailsBinding = ActivityTestDetailsBinding.inflate(getLayoutInflater());
        View view = activityTestDetailsBinding.getRoot();
        databaseReference = FirebaseDatabase.getInstance().getReference("result").child(mAuth.getUid()).child(getIntent().getExtras().getString("name"));
        setContentView(view);
        initializeUI();
    }

    private void initializeUI() {
        setSupportActionBar(activityTestDetailsBinding.toolbarQuizdetail);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(AppCompatResources.getDrawable(this,R.drawable.ic_clear_black_24dp));
        }
        mTvQuizTitle = activityTestDetailsBinding.quizDetailsLabelQuiz;
        mTvQuizDescription = activityTestDetailsBinding.quizDetailsLabelAbout;
        mTvQuizAttemptedStatus = activityTestDetailsBinding.quizDetailsLabelAbout;
        mFabStart = findViewById(R.id.quiz_details_fab_start);
        mFabStart.setOnClickListener(this);
    }



    @Override
    protected void onResume() {
        super.onResume();
        mFabStart.setVisibility(View.VISIBLE);
        showTitle(getIntent().getExtras().getString("name"));
        showDescription(getIntent().getExtras().getString("description"));
        enableStartButton();
        showUserScore();

    }

    public void enableStartButton() {
        mFabStart.setEnabled(true);
        mFabStart.setVisibility(View.VISIBLE);
    }

    public void showTitle(String quizTitle) {
        mTvQuizTitle.setText(quizTitle);
    }



    public void showDescription(String quizDescription) {
        mTvQuizDescription.setText(quizDescription);
    }

    public void showUserScore() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    QuizResult quizResult = snapshot.getValue(QuizResult.class);
                    String score = Integer.toString(quizResult.getScore());
                    String maxMarks = Integer.toString(quizResult.getMaxScore());
                    mTvQuizAttemptedStatus.setText(String.format(Locale.getDefault(), "%s / %s", score, maxMarks));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onError();
            }
        });

    }


    public void startQuiz() {
        Intent attemptQuizIntent = new Intent(this, TestAttemptActivity.class);
        attemptQuizIntent.putExtra("quiz_id", getIntent().getExtras().getString("name"));
        startActivity(attemptQuizIntent);
    }

    public void showInvalidInput() {
        //Toast.makeText(this, getString(R.string.invalid_input_provided),Toast.LENGTH_SHORT).show();
        dismissView();
    }

    public void onError() {
        Toast.makeText(this, getString(R.string.something_went_wrong),
                Toast.LENGTH_SHORT).show();
    }

    public void dismissView() {
        TestDetailsActivity.this.finish();
        TestDetailsActivity.this.overridePendingTransition(R.anim.slide_out_down, R.anim.anim_nothing);
    }



    public void showLoading() {

    }

    public void hideLoading() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quiz_details_fab_start:
                if (v.getVisibility() == View.VISIBLE) {
                    startQuiz();
                }
                break;
            case android.R.id.home:
                dismissView();
            default:
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_down);

    }
}