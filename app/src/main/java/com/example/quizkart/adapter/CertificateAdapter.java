package com.example.quizkart.adapter;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quizkart.CertificateViewActivity;
import com.example.quizkart.R;
import com.example.quizkart.TestDetailsActivity;
import com.example.quizkart.models.Category;
import com.example.quizkart.models.QuizResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CertificateAdapter extends RecyclerView.Adapter<CertificateAdapter.CategoryViewHolder> {
    List<QuizResult> resultList;
    private final FragmentActivity context;

    public CertificateAdapter(FragmentActivity context, List<QuizResult> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.certificate_list, parent, false);


        // now here we create a recyclerview row items.
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        // here we will bind data in recyclerview ro items.
        QuizResult result = resultList.get(position);
        holder.quizName.setText(result.getQuizName());
        setimagefunction(holder.quizImage,result.getQuizName());

        String score = Integer.toString(result.getScore());
        String maxMarks = Integer.toString(result.getMaxScore());
        // for image we need to add glide image fetching library from netwok

        holder.quizScore.setText(String.format(Locale.getDefault(), "Score: %s / %s", score, maxMarks));
        holder.quizDate.setText("Date:"+result.getDate());
        holder.itemView.setOnClickListener(view -> {
            try{
                Intent i = new Intent(view.getContext(), CertificateViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("result", result);
                i.putExtras(bundle);
                view.getContext().startActivity(i);
            }
            catch (Exception e){
                Toast.makeText(view.getContext(),e+"",Toast.LENGTH_LONG).show();
            }

        });

    }

    private void setimagefunction(ImageView quizImage, String quizName) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("category").child(quizName);

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Category category = snapshot.getValue(Category.class);
                    Glide.with(context).load(category.getUrl()).into(quizImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public int getItemCount() {

        return resultList.size();
    }



    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView quizImage;
        TextView quizName;
        TextView quizScore;
        TextView quizDate;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            quizImage = itemView.findViewById(R.id.quiz_image);
            quizName = itemView.findViewById(R.id.quiz_name);
            quizScore = itemView.findViewById(R.id.quiz_score);
            quizDate = itemView.findViewById(R.id.quiz_date);
        }
    }
}
