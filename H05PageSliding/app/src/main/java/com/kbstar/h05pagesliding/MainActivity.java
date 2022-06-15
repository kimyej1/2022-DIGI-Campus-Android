package com.kbstar.h05pagesliding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    boolean isOpen = false;     // 왼쪽으로 갈지 오른쪽으로 갈지 결정할 flag
    LinearLayout slidingMenu;
    Button button;
    Animation leftAni;
    Animation rightAni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slidingMenu = findViewById(R.id.slidingMenu);
        button = findViewById(R.id.button);

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