package com.kbstar.c04callintent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    Button button2;
    Button button4;
    Button btnJustOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button4 = findViewById(R.id.button4);
        btnJustOpen = findViewById(R.id.btnJustOpen);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String telNum = editText.getText().toString();

                // tel:010-1234-5678 형태로 줘야함
                // smsto:010-1234-5678 문자 보내기
                telNum = "tel:" + telNum;

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 방법 1
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);

                startActivityResult.launch(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 방법 2
                Intent intent = new Intent();
                ComponentName component = new ComponentName(
                    "com.kbstar.c04callintent",
                    "com.kbstar.c04callintent.MenuActivity"
                );
                intent.setComponent(component);

                startActivityResult.launch(intent);
            }
        });

        btnJustOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);  // Result 안받아도 될 때
            }
        });
    }

    // callback method : 콜백 함수, 자동으로 호출되는 함수
    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    String name = result.getData().getStringExtra("name");
                    String company = result.getData().getStringExtra("company");
                    int resultCode = result.getResultCode();

                    if (resultCode == 1004)
                    {
                        Toast.makeText(
                                getApplicationContext(),
                                "responsed name : " + name + ", company : " + company,
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
            }
    );

}