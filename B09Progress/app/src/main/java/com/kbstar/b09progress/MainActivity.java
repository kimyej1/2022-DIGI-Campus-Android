package com.kbstar.b09progress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progress;
    Button button;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = findViewById(R.id.progressBar);  // 막대형
        progress.setIndeterminate(false);           // false: 멈춤, true: 움직임
        progress.setProgress(70);                   // 70% 진행
    }
}