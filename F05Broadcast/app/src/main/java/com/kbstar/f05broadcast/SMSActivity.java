package com.kbstar.f05broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SMSActivity extends AppCompatActivity {

    private EditText editText;
    private EditText editText2;
    private EditText editText3;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 전달받은 intent의 sender, msg, time을 출력
        Intent intent = getIntent();
        showSmsInfo(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        showSmsInfo(intent);
        super.onNewIntent(intent);
    }

    private void showSmsInfo(Intent intent) {
        if(intent != null) {
            String sender = intent.getStringExtra("sender");
            String msg = intent.getStringExtra("msg");
            String time = intent.getStringExtra("time");

            editText.setText(msg);
            editText2.setText(sender);
            editText3.setText(time);
        }
    }
}