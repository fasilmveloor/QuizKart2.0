package com.example.quizkart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quizkart.databinding.ActivityCertificateViewBinding;
import com.example.quizkart.models.QuizResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class CertificateViewActivity extends AppCompatActivity {

    ActivityCertificateViewBinding activityCertificateViewBinding;
    private TextView skill;
    private TextView score;
    private TextView obtained_date;
    private Button download;
    private ImageView certificate;
    private Bundle resultBundle;
    private QuizResult result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCertificateViewBinding = ActivityCertificateViewBinding.inflate(getLayoutInflater());
        View view = activityCertificateViewBinding.getRoot();
        setContentView(view);
        
        initializeUI();
        download.setOnClickListener(v -> {
            Bitmap certificateImage = ((BitmapDrawable)certificate.getDrawable()).getBitmap();
            FileOutputStream outputStream;

            try {
                File sdcard = Environment.getExternalStorageDirectory();
                File dir = new File(sdcard.getAbsolutePath()+ "/quizcart");
                if(!dir.exists()) {
                    dir.mkdirs();
                }

                String filename = result.getQuizName()+".png";
                File outFile = new File(dir, filename);
                outputStream = new FileOutputStream(outFile);
                certificateImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.flush();
                outputStream.close();

                Log.d("TAG", "picture wrote to" + outFile.getAbsolutePath());

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void initializeUI() {
        skill = activityCertificateViewBinding.quizname;
        score = activityCertificateViewBinding.quizScore;
        obtained_date = activityCertificateViewBinding.obtaineddate;
        certificate = activityCertificateViewBinding.certificateView;
        download = activityCertificateViewBinding.download;
        resultBundle = getIntent().getExtras();
        result = (QuizResult) resultBundle.getSerializable("result");
        int  scorem = result.getScore();
        int maxScore = result.getMaxScore();
        skill.setText("Skill: "+result.getQuizName());
        score.setText(String.format(Locale.getDefault(), "Score : %s / %s", scorem, maxScore));
        obtained_date.setText("Obtained Date: "+result.getDate());
        Glide.with(this).load(result.getUrl()).into(certificate);
    }
}