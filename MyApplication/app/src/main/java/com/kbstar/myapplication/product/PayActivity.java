package com.kbstar.myapplication.product;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.kbstar.myapplication.databinding.ActivityPayBinding;

public class PayActivity extends AppCompatActivity {

    ActivityPayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}