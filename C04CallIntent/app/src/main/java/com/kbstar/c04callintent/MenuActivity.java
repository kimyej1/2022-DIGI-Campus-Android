package com.kbstar.c04callintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                finish();   // window.close()랑 동일한 기능
            }
        });
    }
}

/*
    Activity : 화면 하나하나
    Intent   : activity 사이에 교환하는 데이터 덩어리
    Bundle   : intent 안에 실제 데이터가 저장되어있는 저장소(hash(dict) = key : value)
        putExtra()       : Bundle에 값 넣을 때 (rest API(map data) 라서 set 안씀)
        getStringExtra() : Bundle에서 값 가져올 때
 */