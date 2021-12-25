package com.example.quizkart.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.quizkart.DashBoardActivity;
import com.example.quizkart.R;
import com.example.quizkart.adapter.CategoryAdapter;
import com.example.quizkart.databinding.FragmentHomeBinding;
import com.example.quizkart.models.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    private CategoryAdapter categoryAdapter;
    private List<Category> viewItems = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPref;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sharedPref = this.getActivity().getPreferences(MODE_PRIVATE);
        binding.usernameview.setText("Hey, "+sharedPref.getString("Firstname","").toString());
        Toast.makeText(getActivity(), sharedPref.getString("Firstname","").toString(), Toast.LENGTH_LONG).show();
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



    private String readJSONDataFromFile() throws IOException{
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();
        try {
            String jsonString = null;
            inputStream = getResources().openRawResource(R.raw.categories);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while((jsonString = bufferedReader.readLine()) != null)
            {
                builder.append(jsonString);
            }
        } finally {
            if(inputStream != null)
                inputStream.close();
        }
        return new String(builder);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}