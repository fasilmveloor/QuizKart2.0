package com.example.quizkart.ui.home;

import android.os.Bundle;
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
    private List<Object> viewItems = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        layoutManager = new StaggeredGridLayoutManager(2, 1);
        binding.quizRecycler.setLayoutManager(layoutManager);
        binding.quizRecycler.setHasFixedSize(true);
        addItemsFromJSON();
        categoryAdapter = new CategoryAdapter(this, viewItems);
        try {
            binding.quizRecycler.setAdapter(categoryAdapter);
        }
        catch (Exception e)
        {
            Toast.makeText(container.getContext(), e.toString(), Toast.LENGTH_LONG);
        }
        return root;
    }

    private void addItemsFromJSON() {
        try{
            String jsonDataString = readJSONDataFromFile();
            JSONArray jsonArray = new JSONArray(jsonDataString);
            for(int i=0; i<jsonArray.length(); ++i){
                JSONObject itemObj = jsonArray.getJSONObject(i);
                String name = itemObj.getString("name");
                int question = itemObj.getInt("question");
                int id = itemObj.getInt("id");
                Category category = new Category(name,question,id);
                viewItems.add(category);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
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