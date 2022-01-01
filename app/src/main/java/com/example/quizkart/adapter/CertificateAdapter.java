package com.example.quizkart.adapter;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.example.quizkart.R;
import com.example.quizkart.TestDetailsActivity;
import com.example.quizkart.models.Category;
import com.example.quizkart.models.QuizResult;
import com.example.quizkart.ui.certificate.CertificateFragment;
import com.example.quizkart.ui.home.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class CertificateAdapter extends RecyclerView.Adapter<CertificateAdapter.CategoryViewHolder> {
    List<QuizResult> resultList;
    private FragmentActivity context;

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
        QuizResult result = (QuizResult) resultList.get(position);
        holder.quizName.setText(result.getQuizName());
        String url = getImageUrl(result.getQuizName());
        String score = Integer.toString(result.getScore());
        String maxMarks = Integer.toString(result.getMaxScore());
        // for image we need to add glide image fetching library from netwok
        Glide.with(context).load(url).into(holder.quizImage);
        holder.quizScore.setText(String.format(Locale.getDefault(), "%s / %s", score, maxMarks));
        holder.quizDate.setText(result.getDate());
        holder.itemView.setOnClickListener(view -> {
            try{
                Intent i = new Intent(view.getContext(), TestDetailsActivity.class);
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

    private String getImageUrl(String quizName) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("category").child(quizName);
        final String[] url = new String[1];
        url[0] = "";
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Category category = snapshot.getValue(Category.class);
                    url[0] = category.getUrl();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return url[0];
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
