package com.kbstar.h01card;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button button2;
    private Layout1 layout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        layout1 = findViewById(R.id.layout1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout1.setName("Kookmin Kim");
                layout1.setMobile("010-2222-1111");
                layout1.setImage(R.drawable.profile2);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout1.setName("Soonshin Lee");
                layout1.setMobile("010-5555-4444");
                layout1.setImage(R.drawable.movie);
            }
        });
    }
}