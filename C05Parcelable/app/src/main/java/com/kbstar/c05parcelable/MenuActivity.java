package com.kbstar.c05parcelable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private TextView display;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        display = findViewById(R.id.display);

        // Button -> event
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        printIntentInfo(intent);
    }

    private void printIntentInfo(Intent intent) {
        if(intent != null) {
            Bundle bundle = intent.getExtras();
            MyData data = bundle.getParcelable("datakey");
            MyData data2 = bundle.getParcelable("datakey2");

            if(data != null) {
                display.setText("Name : " + data.getName() + "\n Age : " + data.getAge());
            }
            if(data2 != null) {
                display.append("\nName : " + data2.getName() + "\n Age : " + data2.getAge());
            }
        }

    }
}