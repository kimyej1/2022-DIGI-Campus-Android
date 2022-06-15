package com.kbstar.h01card;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Layout1 extends LinearLayout {

    ImageView imageView;
    TextView textView;
    TextView textView2;

    public void setName(String name) {
        textView.setText(name);
    }

    public String getName() {
        return textView.getText().toString();
    }

    public void setMobile(String mobile) {
        textView2.setText(mobile);
    }

    public String getMobile() {
        return textView2.getText().toString();
    }

    public void setImage(int resourceId) {
        imageView.setImageResource(resourceId);
    }

    public Layout1(Context context) {
        super(context);
        init(context);
    }

    public Layout1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout1, this, true);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
    }
}