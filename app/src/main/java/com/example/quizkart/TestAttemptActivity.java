package com.example.quizkart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizkart.databinding.ActivityTestAttemptBinding;
import com.example.quizkart.models.Option;
import com.example.quizkart.models.Question;
import com.example.quizkart.models.QuizAttempted;
import com.example.quizkart.models.QuizModel;
import com.example.quizkart.models.QuizResult;
import com.example.quizkart.models.UserInformation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TestAttemptActivity extends AppCompatActivity implements View.OnClickListener {

    private Question mCurrentQuestion;
    private boolean mIsEvaluated;
    public static String QUIZ_ID;

    private int mPointer = 0;
    private QuizModel mSelectedQuiz;

    private List<Question> mQuestions;
    private List<Question> mUserAttempts;
    private boolean isQuizDisplayed = false;
    private DatabaseReference databaseReference;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    ActivityTestAttemptBinding activityTestAttemptBinding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTestAttemptBinding = ActivityTestAttemptBinding.inflate(getLayoutInflater());
        View view = activityTestAttemptBinding.getRoot();
        setContentView(view);
        start(getIntent().getExtras());

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
        LayoutInflater inflater = getLayoutInflater();
        View summarylayout = inflater.inflate(R.layout.layout_custom_dialog_confirmation, null);
        TextView scoreview = summarylayout.findViewById(R.id.score_text);

        TextView grade = summarylayout.findViewById(R.id.grade);
        String msg;
        
        AlertDialog.Builder summary = new AlertDialog.Builder(this);
        summary.setView(summarylayout);
        summary.setPositiveButton(R.string.quiz_review_confirmation, (dialog, which) -> {
            onReviewClicked();
            dialog.dismiss();
        });
        summary.setCancelable(false);
        summary.setNegativeButton(R.string.user_confirmation_cancel, (dialog, which) -> {
            dialog.dismiss();
            dismissView();
        });
        grade.setText("Grade : " + Double.toString(percentage));
        
        scoreview.setText(String.format(Locale.getDefault(), "Score : %s / %s", Integer.toString(score), Integer.toString(total)));

        if(percentage > 80.0) {
            summary.setTitle("Congratulation, You passed the test");
            msg = "Congratulation, You earned a certificate, you can view and download certificate from achievements tab on the dashbnoard";
        }
        else{
            summary.setTitle("Sorry, You didn't earn required score");
            msg = "Come again when you are ok";
        }
        
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

        AlertDialog dialog = summary.create();
        dialog.show();
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
                    onSubmitClicked();
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
                , userAttempt.getQuestion(), userAttempt.getMarks()));



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
        if(activityTestAttemptBinding.rgSingleChoiceHolder.getChildCount() > 0) {
            activityTestAttemptBinding.rgSingleChoiceHolder.removeAllViews();
        }

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
                     option.getValue().setIs_correct(true);
                }
            });

            radioButton.setChecked(singleOption.isIs_correct());

            // Review mode changes
            if (isReviewMode) {
                radioButton.setEnabled(false);
                Option correctOption = question.getOptions().get(option.getKey());

                if (singleOption.isIs_correct()) {
                    radioButton.setTextColor(ContextCompat.getColor(this,
                            R.color.color_red_deadline));
                }

                if (correctOption != null && correctOption.isIs_correct()) {
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
                onNextClicked();
                break;
            case R.id.img_quiz_attempt_previous:
                onPreviousClicked();
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

    public void onNextClicked() {

        if (mPointer < mQuestions.size()) {

            if (mPointer == (mQuestions.size() - 1)) {
                // Last question reached. Submit button clicked
                if (!mIsEvaluated) {
                    showSubmitConfirmation();
                } else {
                    dismissView();
                }
            } else {

                mPointer++;

                if (!mIsEvaluated) {
                    loadQuestion(mUserAttempts.get(mPointer));
                } else {
                    loadQuestionForReview(mUserAttempts.get(mPointer), mQuestions.get(mPointer));
                }

                // Last question reached, show submit button
                if (mPointer == (mQuestions.size() - 1)) {
                    showSubmitButton();
                }

            }
            enablePreviousButton();
            updateQuestionStatus();
        }
    }

    public void onReviewClicked() {
        mIsEvaluated = true;
        mPointer = 0;
        disablePreviousButton();
        loadQuestionForReview(mQuestions.get(mPointer), mUserAttempts.get(mPointer));
        disablePreviousButton();
        showNextButton();
        updateQuestionStatus();
    }

    public void onPreviousClicked() {
        if (mPointer > 0) {
            // First question
            mPointer--;

            if (!mIsEvaluated) {
                loadQuestion(mUserAttempts.get(mPointer));
            } else {
                loadQuestionForReview(mQuestions.get(mPointer), mUserAttempts.get(mPointer));
            }

            if (mPointer == 0) {
                disablePreviousButton();
            }

            showNextButton();
            updateQuestionStatus();
        }
    }

    public void onSubmitClicked() {
        if (!mIsEvaluated) {

            int maxMarks = mSelectedQuiz.getMarks();


            int userScore = 0;
            final String[] url = {""};

            // Evaluating user's score based on performance
            for (Question userAttempt : mUserAttempts) {
                if (mQuestions.contains(userAttempt)) {
                    userScore += userAttempt.getMarks();
                }
            }

            int finalUserScore = userScore;

            double userPercentage = 100 * (((double) finalUserScore) / maxMarks);

            QuizAttempted quizAttempted = new QuizAttempted();
            quizAttempted.setmMaxMarks(maxMarks);
            quizAttempted.setmPercentage((int) userPercentage);
            quizAttempted.setmQuizTitle(QUIZ_ID);
            quizAttempted.setmScore(userScore);

            DatabaseReference db = FirebaseDatabase.getInstance().getReference("result").child(mAuth.getUid());
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

            if(userPercentage > 80.0) {
                generateCertificate(QUIZ_ID, currentDate);

            }
            QuizResult quizResult = new QuizResult(QUIZ_ID, userScore, maxMarks, currentDate);
            db.child(QUIZ_ID).setValue(quizResult);
            loadResultSummary(finalUserScore, maxMarks, userPercentage);

        } else {
            dismissView();
        }
    }


    private void generateCertificate(String quizId, String date) {

        DatabaseReference userprofile = FirebaseDatabase.getInstance().getReference("userprofile").child(mAuth.getUid());
        userprofile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserInformation user = snapshot.getValue(UserInformation.class);
                    String firstname = user.getUserName();
                    String lastname = user.getUserSurname();
                    String name = firstname + ' '+lastname;
                    String quizdate = date;

                    Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.cartificate)
                            .copy(Bitmap.Config.ARGB_8888, true);

                    Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);

                    Paint paint = new Paint();
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.BLACK);
                    paint.setTypeface(tf);
                    paint.setTextAlign(Paint.Align.CENTER);
                    paint.setTextSize(convertToPixels( 110));

                    Rect textRect = new Rect();
                    paint.getTextBounds(name, 0, name.length(), textRect);

                    Canvas canvas = new Canvas(bm);

                    Log.d("Certificate Values:","name:"+quizId+name+date);



                    canvas.drawText(name, 1350, 1025, paint);
                    paint.setTextSize(convertToPixels( 80));
                    canvas.drawText(quizId, 1350, 1275, paint);
                    paint.setTextSize(convertToPixels( 50));
                    canvas.drawText(date, 500, 1600, paint);
                    ByteArrayOutputStream cert = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.PNG, 100, cert);
                    byte[] data = cert.toByteArray();
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference(mAuth.getUid());
                    StorageReference certificatestore = storageReference.child("certificates").child(QUIZ_ID);
                    UploadTask uploadTask = certificatestore.putBytes(data);
                    uploadTask.addOnFailureListener(e -> Toast.makeText(TestAttemptActivity.this, "Error: something went wrong", Toast.LENGTH_SHORT).show())
                            .addOnSuccessListener(taskSnapshot -> {

                            });
                } else
                    Log.d("Error on Test attempt", "Userinfo not found");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    public int convertToPixels(int nDP)
    {
        final float conversionScale = getResources().getDisplayMetrics().density;

        return (int) ((nDP * conversionScale) + 0.5f) ;

    }

    public void start(@Nullable Bundle extras) {

        if (extras == null || !extras.containsKey("quiz_id")) {
            showInvalidInput();
            return;
        }

        QUIZ_ID = extras.getString("quiz_id");

        showLoading();
        fetchQuizById(QUIZ_ID);

    }

    private void fetchQuizById(String quizId) {
        databaseReference = FirebaseDatabase.getInstance().getReference("questions").child(quizId);
        try {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //Retrieving questions snapshots
                    HashMap<String, Question> questionHashMap = new HashMap<>();
                    int c=1;
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        //fetching question
                        String question = dataSnapshot.child("question").getValue(String.class);
                        //fetching options
                        DataSnapshot options = dataSnapshot.child("options");
                        HashMap<String, Option> optionHashMap= new HashMap<>();
                        for(int i=1; i<=4; i++){
                            Option op = options.child("option"+i).getValue(Option.class);
                            optionHashMap.put("option"+i, op);
                        }
                        questionHashMap.put("q"+c, new Question(question, 1, optionHashMap));
                        c++;
                    }
                    QuizModel quiz = new QuizModel(QUIZ_ID, 15, questionHashMap, false);
                    if(!isQuizDisplayed)
                    {
                        displayQuiz(quiz);
                        isQuizDisplayed = true;
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    showError();


                }
            });
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void displayQuiz(QuizModel currentQuiz) {
        mSelectedQuiz = currentQuiz;
        mQuestions = new ArrayList<>(mSelectedQuiz.getQuestions().values());
        mUserAttempts = new ArrayList<>();
        //mUserAttempts = (List<Question>) (new ArrayList<>(mSelectedQuiz.getmQuestions().values())).clone();

        // Clear all is-correct flags from user attempts, it will be used to store user's answers
        for (Question question : mQuestions) {
            Question copyQuestion = new Question(question);
            copyQuestion.resetOptions();
            mUserAttempts.add(copyQuestion);
        }

        // Since we are currently in first question
        disablePreviousButton();

        loadQuestion(mUserAttempts.get(mPointer));

        loadTitle(QUIZ_ID);
    }



    private void updateQuestionStatus() {
        loadAttemptedStatusText(mPointer + 1, mQuestions.size());
    }

}