package com.kbstar.b09progress;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progress;
    Button button;
    Button button2;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = findViewById(R.id.progressBar);  // 막대형
        progress.setIndeterminate(false);           // false: 멈춤, true: 움직임
        progress.setProgress(70);                   // 70% 진행

        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // dialog progress bar

                dialog = new ProgressDialog(MainActivity.this);

                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("Spinner Test");
                dialog.show();
            }
        });
    }

    public void kbClick(View view)
    {
        if(dialog != null)
        {
            dialog.dismiss();
        }
    }
}