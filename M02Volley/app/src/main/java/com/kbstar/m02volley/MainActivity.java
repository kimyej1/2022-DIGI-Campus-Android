package com.kbstar.m02volley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout layout_logout, layout_login;
    private Button btnGoLogin, btnGoRegist, btnLogout;
    private TextView tv_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGoRegist = findViewById(R.id.btnGoRegist);
        btnGoLogin = findViewById(R.id.btnGoLogin);
        btnLogout = findViewById(R.id.btnLogout);

        layout_login = findViewById(R.id.layout_login);
        layout_logout = findViewById(R.id.layout_logout);
        tv_login = findViewById(R.id.tv_login);

        btnGoRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoRegist();
            }
        });

        btnGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoLogin();
            }
        });
    }

    public void gotoRegist() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    public void gotoLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}