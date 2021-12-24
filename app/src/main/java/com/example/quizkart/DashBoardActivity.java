package com.example.quizkart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quizkart.databinding.NavHeaderDashBoardBinding;
import com.example.quizkart.models.UserInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizkart.databinding.ActivityDashBoardBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DashBoardActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDashBoardBinding binding;
    private FirebaseAuth mauth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private NavHeaderDashBoardBinding navHeaderDashBoardBinding;
    private ImageView profilePicImageView;
    private TextView username;
    private TextView useremail;
    private SharedPreferences sharedPref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashBoardBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarDashBoard.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        profilePicImageView = binding.navView.getHeaderView(0).findViewById(R.id.prfileimage);
        username = binding.navView.getHeaderView(0).findViewById(R.id.username);
        useremail = binding.navView.getHeaderView(0).findViewById(R.id.emailid);
        mauth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("userprofile").child(mauth.getUid());
        storageReference = firebaseStorage.getReference();

        useremail.setText(mauth.getCurrentUser().getEmail());

        // Get the image stored on Firebase via "User id/Images/Profile Pic.jpg".
        storageReference.child(mauth.getUid()).child("Images").child("Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri){

                Glide.with(DashBoardActivity.this).load(uri).centerInside().into(profilePicImageView);

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    UserInformation userProfile = dataSnapshot.getValue(UserInformation.class);
                    String varusername = userProfile.getUserName() + ' ' + userProfile.getUserSurname();
                    username.setText(varusername);
                }
                else
                    Toast.makeText(getApplicationContext(), "Data is not exist", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled( DatabaseError databaseError) {
                Toast.makeText(DashBoardActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dash_board);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        ColorStateList csl = new ColorStateList(
                new int[][] {
                        new int[] {-android.R.attr.state_checked}, // unchecked
                        new int[] { android.R.attr.state_checked}  // checked
                },
                new int[] {
                        Color.BLACK,
                        Color.RED
                }
        );
        navigationView.setItemTextColor(csl);
        navigationView.setItemIconTintList(csl);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_logout:
                showSignOutAlert();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dash_board);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    private void showSignOutAlert() {
        FirebaseUser currentUser = mauth.getCurrentUser();
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.sign_out_title))
                .setMessage(getString(R.string.sign_out_message))
                .setPositiveButton(getResources().getString(R.string.sign_out_ok), (dialog, which) -> {
                    try {
                        if (currentUser != null)
                        {
                            SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.clear();
                            editor.apply();
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(DashBoardActivity.this, LoginActivity.class));
                            finishAffinity();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                })
                .setNegativeButton(getString(R.string.sign_out_cancel), (dialog, which) -> dialog.dismiss())
                .create().show();
    }
   // keep this in try and catch
    public void gotoSettings(MenuItem item) {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }
    public void moveToProfile(View view) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }
}