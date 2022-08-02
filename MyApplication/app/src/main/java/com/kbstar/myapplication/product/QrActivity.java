package com.kbstar.myapplication.product;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kbstar.myapplication.databinding.ActivityQrBinding;

public class QrActivity extends AppCompatActivity {

    ActivityQrBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}