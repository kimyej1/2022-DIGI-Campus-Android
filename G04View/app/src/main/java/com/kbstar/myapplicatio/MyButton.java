package com.kbstar.myapplicatio;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;


public class MyButton extends AppCompatButton {

    public MyButton(Context context) {
        super(context);
        init(context);
    }

    public MyButton(Context context, AttributeSet attrs) {   // set : 중복 제거
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setBackgroundColor(Color.BLUE);
        setTextColor(Color.WHITE);
        /*
            res/values/dimens.xml 에 정의하고
            getDimension()에 의해 픽셀값으로 자동 변환한다.
         */
        float textSize = getResources().getDimensionPixelSize(R.dimen.text_size);
        setTextSize(textSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch(action) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(Color.MAGENTA);
                setTextColor(Color.BLACK);
                break;
            case MotionEvent.ACTION_UP:     // break 만날떄까지 계속!
            case MotionEvent.ACTION_MOVE:
                setBackgroundColor(Color.CYAN);
                setTextColor(Color.YELLOW);
                break;
        }
        invalidate();
        return true;
    }
}
