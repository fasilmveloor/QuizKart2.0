package com.example.quizkart.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizkart.R;
import com.example.quizkart.TestDetailsActivity;
import com.example.quizkart.models.Category;
import com.example.quizkart.ui.home.HomeFragment;

import java.util.List;
import java.util.Objects;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private HomeFragment context;
    List<Object> categoryList;

    public CategoryAdapter(HomeFragment context, List<Object> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row_items, parent, false);


        // now here we create a recyclerview row items.
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        // here we will bind data in recyclerview ro items.
        Category category = (Category) categoryList.get(position);
        holder.categoryName.setText(category.getName());
        holder.totalCategory.setText(String.valueOf(category.getQuestion()));

        // for image we need to add glide image fetching library from netwok
        String description = category.getName() + "This is a programming langauge, it is used for varouse purposes, This test is comprise of "+ category.getQuestion();
        holder.categoryImage.setImageResource(R.drawable.business);

        holder.itemView.setOnClickListener(view -> {
            try{
                Intent i = new Intent(view.getContext(), TestDetailsActivity.class);
                i.putExtra("name", category.getName());
                i.putExtra("description", description);
                view.getContext().startActivity(i);
            }
            catch (Exception e){
                Toast.makeText(view.getContext(),e+"",Toast.LENGTH_LONG).show();
            }

        });

    }

    @Override
    public int getItemCount() {

        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView categoryImage;
        TextView categoryName, totalCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.quiz_image);
            categoryName = itemView.findViewById(R.id.quiz_name);
            totalCategory = itemView.findViewById(R.id.quiz_questions);


        }
    }
}
