package com.kbstar.h05pagesliding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean isOpen = false;     // 왼쪽으로 갈지 오른쪽으로 갈지 결정할 flag
    LinearLayout slidingMenu;
    Button button, btnMenu1, btnMenu2, btnMenu3;
    Animation leftAni;
    Animation rightAni;
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slidingMenu = findViewById(R.id.slidingMenu);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);

        button = findViewById(R.id.button);
        btnMenu1 = findViewById(R.id.btnMenu1);
        btnMenu2 = findViewById(R.id.btnMenu2);
        btnMenu3 = findViewById(R.id.btnMenu3);

        leftAni = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left);
        rightAni = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right);

        SlidingPageAnimationListener listener = new SlidingPageAnimationListener();
        leftAni.setAnimationListener(listener);
        rightAni.setAnimationListener(listener);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpen) {
                    slidingMenu.startAnimation(leftAni);
                } else {
                    slidingMenu.setVisibility(View.VISIBLE);
                    slidingMenu.startAnimation(rightAni);
                }
            }
        });

        btnMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingMenu.startAnimation(leftAni);
                textView.setText("Menu 1");

                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.face1);
            }
        });

        btnMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingMenu.startAnimation(leftAni);
                textView.setText("Menu 2");

                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.face3);
            }
        });

        btnMenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingMenu.startAnimation(leftAni);
                textView.setText("Menu 3");

                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.face5);
            }
        });
    }

    private class SlidingPageAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if(isOpen) {
                slidingMenu.setVisibility(View.INVISIBLE);
                isOpen = false;
                button.setText("OPEN");
            } else {
//                slidingMenu.setVisibility(View.VISIBLE);
                isOpen = true;
                button.setText("CLOSE");
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}