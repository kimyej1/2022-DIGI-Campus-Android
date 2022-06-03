package com.kbstar.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // R.layout = res > layout 이라는 뜻!

        /*
            JS
            var btn = document.getElementById('btn');
            let btn = document.querySelector('#btn');

            JQuery
            let btn = $('#btn');
         */

        textView = findViewById(R.id.textView); // 머 찾아올 땐 find!!!
        System.out.println("-------------------" + textView.getText().toString());
    }

}