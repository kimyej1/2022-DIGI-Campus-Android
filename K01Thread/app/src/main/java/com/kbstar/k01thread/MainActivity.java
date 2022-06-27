package com.kbstar.k01thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;
    private TextView textView;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        textView = findViewById(R.id.textView);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyThread thread = new MyThread();
                thread.start();
            }
        });
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            for(int i = 0; i<100; i++) {
                try {
                    Thread.sleep(1000);
                } catch(Exception e) {

                }
                counter++;
                textView.setText("" + counter);
            }
        }
    }
}