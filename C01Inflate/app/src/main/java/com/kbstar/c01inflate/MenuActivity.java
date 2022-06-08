package com.kbstar.c01inflate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    private LinearLayout containerX;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // 버튼, 컨테이너 객체 찾아오기
        button = findViewById(R.id.button);
        System.out.println("+++++++++++++++++++++++++++++++ Button : " + button.getText().toString());
        containerX = findViewById(R.id.container);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Layout Inflater = Layout을 Inflate 해줌 = Layout을 메모리에 탑재해줌
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);   // type casting 필수!!!!
                // sub1으로부터 가져와서 container에 갖다 붙임 (activity_menu 하단에 linear layout에 붙여줘)
                inflater.inflate(R.layout.sub1, containerX, true);

                // 전체 화면이 아니라 container2에서만 찾아봐 -> checkBox라는게 있다면 그걸 로컬 변수로 객체 할당
                CheckBox checkBox = containerX.findViewById(R.id.checkBox);
                checkBox.setTag("Sub1 Loaded");

                button = containerX.findViewById(R.id.button2);
                System.out.println("=================================== Button2 : " + button.getText().toString());
            }
        });
    }
}