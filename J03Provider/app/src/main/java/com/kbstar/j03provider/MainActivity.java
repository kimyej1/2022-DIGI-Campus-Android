package com.kbstar.j03provider;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
    Content Provider
        다른 앱에서 데이터에 접근하는 경우, 대신 데이터를 접근할 수 있는 컨텐츠를 제공해준다.
        제공되는 데이터는 Resolver를 통해서 접근

        안드로이드 4대 구성요소
        AndroidManifest에 등록되어야 한다.
        앱 보안적 요소가 강함(데이터를 어떻게 안전하게 처리할 수 있을까?
 */
public class MainActivity extends AppCompatActivity {

    Button btnInsert, btnUpdate, btnDelete, btnSelect;
    TextView debugText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnInsert);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnSelect = findViewById(R.id.btnSelect);
        debugText = findViewById(R.id.debugText);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });
    }

    public void insert() {

    }

    public void update() {

    }

    public void select() {

    }

    public void delete() {

    }
}