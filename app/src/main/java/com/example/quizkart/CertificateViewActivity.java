package com.example.quizkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quizkart.databinding.ActivityCertificateViewBinding;
import com.example.quizkart.models.QuizResult;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
    private FirebaseAuth mauth = FirebaseAuth.getInstance();
    private StorageReference storageReference;
    private static int REQUEST_CODE = 100;
    OutputStream outputStream;
    String quizid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCertificateViewBinding = ActivityCertificateViewBinding.inflate(getLayoutInflater());
        View view = activityCertificateViewBinding.getRoot();
        setContentView(view);
        storageReference = FirebaseStorage.getInstance().getReference(mauth.getUid());
        
        initializeUI();


        download.setOnClickListener(v -> {
            if(ContextCompat.checkSelfPermission(CertificateViewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED) {
                saveimage();
            }
            else {
                askPermission();
            }


        });
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(CertificateViewActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveimage();
            }
            else{
                Toast.makeText(getApplicationContext(), "Please provide Required permission", Toast.LENGTH_LONG).show();
                Log.d(""+grantResults.length, "Result"+grantResults[0]);
            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void saveimage() {
        File dir = new File(Environment.getExternalStorageDirectory(), "quizkart");
        if(!dir.exists()) {
            dir.mkdir();
        }
        if(dir.exists()) {
            try {
                BitmapDrawable drawable = (BitmapDrawable) certificate.getDrawable();
                Bitmap certificate = drawable.getBitmap();

                File file = new File(dir, quizid+".jpg");
                outputStream = new FileOutputStream(file);
                certificate.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                Toast.makeText(getApplicationContext(), "Successfully Saved", Toast.LENGTH_LONG).show();
                outputStream.flush();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.d("Error in certificate Save" , ""+e);
            }
            catch (IOException e) {
                e.printStackTrace();
                Log.d("Error in certificate Save" , ""+e);
            }
        }


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
        quizid = result.getQuizName();
        skill.setText("Skill: "+result.getQuizName());
        score.setText(String.format(Locale.getDefault(), "Score : %s / %s", scorem, maxScore));
        obtained_date.setText("Obtained Date: "+result.getDate());
        StorageReference certificatestore = storageReference.child("certificates").child(result.getQuizName());
        certificatestore.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(CertificateViewActivity.this).load(uri).into(certificate);
            }
        });

    }
}