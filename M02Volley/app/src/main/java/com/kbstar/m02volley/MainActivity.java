package com.kbstar.m02volley;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout layout_logout, layout_login;
    private Button btnGoLogin, btnGoRegist, btnLogout;
    private TextView tv_login;

    private static String LOGIN_ID;  // static 안써도됨
    private static String LOGIN_NAME;
    private static String LOGIN_LEVEL;

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

        Intent intent = getIntent();
        LOGIN_ID = intent.getStringExtra("LOGIN_ID");

        if(LOGIN_ID == null) {  // not logged in
            layout_login.setVisibility(View.INVISIBLE);
            layout_logout.setVisibility(View.VISIBLE);

        } else {    // logged in
            LOGIN_NAME = intent.getStringExtra("LOGIN_NAME");
            LOGIN_LEVEL = intent.getStringExtra("LOGIN_LEVEL");

            layout_login.setVisibility(View.VISIBLE);
            layout_logout.setVisibility(View.INVISIBLE);
            tv_login.setText("Hello, " + LOGIN_NAME + "!");
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    public void logout() {
        new AlertDialog.Builder(this)
                .setTitle("Result")
                .setMessage("Logout for sure?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tv_login.setText("");
                        layout_login.setVisibility(View.INVISIBLE);
                        layout_logout.setVisibility(View.VISIBLE);

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        // 중복된 화면들 다 꺼주기
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
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