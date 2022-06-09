package com.kbstar.c05parcelable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpen = findViewById(R.id.btnOpen);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                MyData data = new MyData("Kildong Hong", 34);
                intent.putExtra("datakey", data);

                MyData data2 = new MyData("SS Lee", 77);
                intent.putExtra("datakey2", data2);

                Bundle bundle = intent.getExtras();
                MyData md = bundle.getParcelable("datakey");
                MyData md2 = bundle.getParcelable("datakey2");

                System.out.println("XMIT ------------------- name =" + md.getName());
                System.out.println("XMIT ------------------- age =" + md.getAge());
                System.out.println("XMIT ------------------- name =" + md2.getName());
                System.out.println("XMIT ------------------- age =" + md2.getAge());

                startActivity(intent);
            }
        });
    }
}