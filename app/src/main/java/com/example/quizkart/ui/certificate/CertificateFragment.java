package com.example.quizkart.ui.certificate;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quizkart.R;
import com.example.quizkart.adapter.CertificateAdapter;
import com.example.quizkart.databinding.CertificateFragmentBinding;
import com.example.quizkart.databinding.FragmentHomeBinding;
import com.example.quizkart.models.Category;
import com.example.quizkart.models.QuizResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CertificateFragment extends Fragment {

    private CertificateFragmentBinding binding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference databaseReference;
    private CertificateAdapter certificateAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<QuizResult> certificateItems = new ArrayList<>();

    public static CertificateFragment newInstance() {
        return new CertificateFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = CertificateFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        databaseReference = FirebaseDatabase.getInstance().getReference("result").child(mAuth.getUid());
        layoutManager = new StaggeredGridLayoutManager(2, 1);
        binding.certificateRecycler.setLayoutManager(layoutManager);
        binding.certificateRecycler.setHasFixedSize(true);

        certificateAdapter = new CertificateAdapter(getActivity(), certificateItems);
        binding.certificateRecycler.setAdapter(certificateAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount() > 0)
                {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        QuizResult quizResult = dataSnapshot.getValue(QuizResult.class);
                        certificateItems.add(quizResult);
                    }
                    certificateAdapter.notifyDataSetChanged();
                    binding.nocertificatemsg.setVisibility(View.GONE);
                }
                else {
                    binding.certificateRecycler.setVisibility(View.GONE);
                    binding.nocertificatemsg.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }



}