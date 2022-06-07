package com.kbstar.b04event;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private View view;
    private View view2;

    GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        view = findViewById(R.id.view);
        view2 = findViewById(R.id.view2);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                float positionX = motionEvent.getX();
                float positionY = motionEvent.getY();

                if(action == motionEvent.ACTION_DOWN)
                {
                    println("Down : " + positionX + ", " + positionY);
                } else if(action == motionEvent.ACTION_UP)
                {
                    println("Up : " + positionX + ", " + positionY);
                } else if(action == motionEvent.ACTION_MOVE)
                {
                    println("Move : " + positionX + ", " + positionY);
                }

                return true;
            }
        });

        detector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                println("onDown() is called.");
                return true;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {
                println("onShowPress() is called.");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                println("onSingleTapUp() is called.");
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                println("onScroll() is called.");
                return true;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {
                println("onLongPress() is called.");
            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                println("onFling() is called.");
                return true;
            }
        });

        view2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                detector.onTouchEvent(motionEvent); // view2 안에서 일어난 터치에 대해서는 detector 이용해서 감지해라
                return true;
            }
        });
    }

    public void println(String msg)
    {
        textView.append(msg + "\n");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Method Chain
            Toast
                    .makeText(this, "BACK BUTTON is Pressed.", Toast.LENGTH_LONG)
                    .show();
        } else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP)
        {
            // Method Chain
            Toast
                    .makeText(this, "VOLUME + is Pressed.", Toast.LENGTH_LONG)
                    .show();
        } else if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
        {
            // Method Chain
            Toast
                    .makeText(this, "VOLUME - is Pressed.", Toast.LENGTH_LONG)
                    .show();
        }
        return true;
    }
}

/*
    KEYCODE_A ~ KEYCODE_Z
    KEYCODE_0 ~ KEYCODE_9
    KEYCODE_VOLUME_UP / _VOLUME_DOWN
    KEYCODE_BACK / _HOME
 */