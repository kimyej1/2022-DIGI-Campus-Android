package com.kbstar.f04service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = editText.getText().toString();

                Intent intent = new Intent(
                        getApplicationContext(),
                        MyService.class);
                intent.putExtra("msg", msg);
                intent.putExtra("name", "김국민");
                // 필요한 부가정보를 계속 추가
                startService(intent);
            }
        });

        // 하고싶은 일이 있으면..추가
        // 액티비티가 새로 만들어질 때 전달될 인텐트
        Intent newIntent = getIntent();
        processIntent(newIntent);

    }

    private void processIntent(Intent intent)
    {
        if(intent != null)
        {
            String cmd = intent.getStringExtra("cmd"); // display
            String msg = intent.getStringExtra("msg"); // from MyService
            editText.setText(msg);
        }
    }

    // 액티비티가 이미 만들어져 있을 때 전달된 인텐트 처리를 위한 재사용
    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
        super.onNewIntent(intent);
    }
}