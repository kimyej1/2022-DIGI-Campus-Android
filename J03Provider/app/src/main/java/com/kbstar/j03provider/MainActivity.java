package com.kbstar.j03provider;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/*
    Content Provider
        다른 앱에서 데이터에 접근하는 경우, 대신 데이터를 접근할 수 있는 컨텐츠를 제공해준다.
        제공되는 데이터는 Resolver를 통해서 접근

        안드로이드 4대 구성요소
        AndroidManifest에 등록되어야 한다.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}