package com.example.quizkart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.quizkart.databinding.ActivityTestAttemptBinding;
import com.example.quizkart.models.Option;
import com.example.quizkart.models.Question;
import com.example.quizkart.presenter.TestAttemptPresenter;

import java.util.Locale;
import java.util.Map;

public class TestAttemptActivity extends AppCompatActivity implements View.OnClickListener {

    private Question mCurrentQuestion;
    private boolean mIsEvaluated;

    ActivityTestAttemptBinding activityTestAttemptBinding;
    TestAttemptPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTestAttemptBinding = ActivityTestAttemptBinding.inflate(getLayoutInflater());
        View view = activityTestAttemptBinding.getRoot();
        setContentView(view);
        mPresenter.start(getIntent().getExtras());

        setSupportActionBar(activityTestAttemptBinding.toolbarAttemptQuiz);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(AppCompatResources.getDrawable(this, R.drawable.ic_clear_black_24dp));
        }

        activityTestAttemptBinding.imgQuizAttemptNext.setOnClickListener(this);
        activityTestAttemptBinding.imgQuizAttemptPrevious.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showQuizQuizConfirmation();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void enablePreviousButton() {
        activityTestAttemptBinding.imgQuizAttemptPrevious.setVisibility(View.VISIBLE);
    }

    public void disablePreviousButton() {
        activityTestAttemptBinding.imgQuizAttemptPrevious.setVisibility(View.INVISIBLE);
    }

    public void showSubmitButton() {
        activityTestAttemptBinding.imgQuizAttemptNext.setImageResource(R.drawable.ic_submit_btn);
    }

    public void showNextButton() {
        activityTestAttemptBinding.imgQuizAttemptNext.setImageResource(R.drawable.ic_next_circle_filled);
    }

    public void loadQuestion(Question question) {
        this.mCurrentQuestion = question;
        hideKeyboard();
        populateQuestionDetails(mCurrentQuestion, null);
    }

    public void loadQuestionForReview(Question attemptedQuestion, Question question) {
        populateQuestionDetails(attemptedQuestion, question);
    }

    public void loadAttemptedStatusText(int currentQuestionNumber, int totalQuestions) {
        activityTestAttemptBinding.tvAttemtQuizStatus.setText(String.format(Locale.getDefault(), "%d of %d",
                currentQuestionNumber, totalQuestions));
    }

    public void loadTitle(String quizTitle) {

        activityTestAttemptBinding.toolbarAttemptQuiz.setTitle(quizTitle);
    }

    public void loadResultSummary(int score, int total, double percentage) {
        new AlertDialog.Builder(this)
                .setMessage(String.format(Locale.getDefault(), "You've got %d out of %d. " +
                        "You have scored %.2f%%", score, total, (float) percentage))
                .setTitle(R.string.quiz_summary_msg)
                .setCancelable(false)
                .setPositiveButton(R.string.quiz_review_confirmation, (dialog, which) -> {
                    mPresenter.onReviewClicked();
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.user_confirmation_cancel, (dialog, which) -> {
                    dialog.dismiss();
                    dismissView();
                })
                .create()
                .show();
    }

    public void showError() {
        Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT)
                .show();
    }

    public void showInvalidInput() {
        Toast.makeText(this, getString(R.string.invalid_input_provided),
                Toast.LENGTH_SHORT).show();
        dismissView();
    }

    public void showSubmitConfirmation() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.quiz_submit_confirmation_msg)
                .setTitle(R.string.quiz_submit_confirmation_title)
                .setCancelable(true)
                .setPositiveButton(R.string.user_confirmation_yes, (dialog, which) -> {
                    mPresenter.onSubmitClicked();
                    mIsEvaluated = true;
                })
                .setNegativeButton(R.string.user_confirmation_cancel, (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    public void dismissView() {
        TestAttemptActivity.this.finish();
        TestAttemptActivity.this.overridePendingTransition(R.anim.slide_out_down, R.anim.anim_nothing);
    }

    public void showLoading() {

    }

    public void hideLoading() {

    }

    private void populateQuestionDetails(@NonNull Question userAttempt, @Nullable Question question) {

        activityTestAttemptBinding.tvQuestionDescription.setText(String.format(Locale.getDefault(), "%s [%d marks]"
                , userAttempt.getDescription(), userAttempt.getMarks()));



        boolean isReviewMode = (question != null);
        if (isReviewMode) {
            boolean isAttemptCorrect = userAttempt.equals(question);
            activityTestAttemptBinding.tvQuestionDescription.setTextColor(isAttemptCorrect ? ContextCompat.getColor(this,
                    R.color.color_green_deadline) : ContextCompat.getColor(this,
                    R.color.color_red_deadline));
        }


        // Move scroll view to top every time question is populated
        activityTestAttemptBinding.questionHierarchyHolder.fullScroll(ScrollView.FOCUS_UP);
        activityTestAttemptBinding.rgSingleChoiceHolder.setVisibility(View.VISIBLE);
        Map<String, Option> options = userAttempt.getOptions();

        int index = 0;
        for (Map.Entry<String, Option> option : options.entrySet()) {
            Option singleOption = option.getValue();
            RadioButton radioButton = (RadioButton) LayoutInflater.from(this)
                    .inflate(R.layout.rb_single_choice,
                            activityTestAttemptBinding.flAllOptionsTypeHolder, false);

            radioButton.setText(singleOption.getDescription());
            radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                // Since it is single choice question, reset everything before setting
                if (isChecked) {
                    userAttempt.resetOptions();
                    option.getValue().setIsCorrect(true);
                }
            });

            radioButton.setChecked(singleOption.isCorrect());

            // Review mode changes
            if (isReviewMode) {
                radioButton.setEnabled(false);
                Option correctOption = question.getOptions().get(option.getKey());

                if (singleOption.isCorrect()) {
                    radioButton.setTextColor(ContextCompat.getColor(this,
                            R.color.color_red_deadline));
                }

                if (correctOption.isCorrect()) {
                    radioButton.setTextColor(ContextCompat.getColor(this,
                            R.color.color_green_deadline));
                }
            }

            activityTestAttemptBinding.rgSingleChoiceHolder.addView(radioButton, index);
            index++;

        }
    }

    @Override
    public void onBackPressed() {
        if (mIsEvaluated) {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_out_down, R.anim.anim_nothing);

        } else {
            showQuizQuizConfirmation();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_quiz_attempt_next:
                mPresenter.onNextClicked();
                break;
            case R.id.img_quiz_attempt_previous:
                mPresenter.onPreviousClicked();
                break;
            default:
                break;
        }
    }

    private void showQuizQuizConfirmation() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.quiz_dismiss_confirmation_msg)
                .setTitle(R.string.quiz_dismiss_confirmation_title)
                .setCancelable(true)
                .setPositiveButton(R.string.user_confirmation_yes, (dialog, which) -> {
                    dialog.dismiss();
                    dismissView();
                })
                .setNegativeButton(R.string.user_confirmation_cancel, (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    //method to hide input keyboard on next/previous question button click
    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        try {
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

}