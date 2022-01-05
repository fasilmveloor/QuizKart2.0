package com.example.quizkart.ui.aboutus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.quizkart.databinding.FragmentAboutusBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class AboutusFragment extends Fragment {

    FragmentAboutusBinding binding;

    @SuppressLint("IntentReset")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAboutusBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        return root;
    }
}