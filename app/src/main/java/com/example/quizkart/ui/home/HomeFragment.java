package com.example.quizkart.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.example.quizkart.adapter.CategoryAdapter;
import com.example.quizkart.databinding.FragmentHomeBinding;
import com.example.quizkart.models.Category;
import com.example.quizkart.models.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    private CategoryAdapter categoryAdapter;
    private List<Category> viewItems = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPref;
    private DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sharedPref = this.getActivity().getPreferences(MODE_PRIVATE);
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("userprofile").child(mauth.getUid());
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    UserInformation userProfile = dataSnapshot.getValue(UserInformation.class);
                    String varusername = userProfile.getUserName() + ' ' + userProfile.getUserSurname();
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("Email", mauth.getCurrentUser().getEmail().toString());
                    editor.putString("Firstname", userProfile.getUserName().toString());
                    editor.putString("Lastname", userProfile.getUserSurname());
                    editor.putString("phoneno", userProfile.getUserPhoneno());
                    editor.commit();
                    binding.usernameview.setText("Hey, "+sharedPref.getString("Firstname","").toString());
                    Toast.makeText(getActivity(), "Welcome "+ userProfile.getUserName(), Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getActivity(), "Data is not exist", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled( DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("categories");
        layoutManager = new StaggeredGridLayoutManager(2, 1);
        binding.quizRecycler.setLayoutManager(layoutManager);
        binding.quizRecycler.setHasFixedSize(true);

        categoryAdapter = new CategoryAdapter(this, viewItems);
        binding.quizRecycler.setAdapter(categoryAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Category category = dataSnapshot.getValue(Category.class);
                    viewItems.add(category);
                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }



    private void setcredintails() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}