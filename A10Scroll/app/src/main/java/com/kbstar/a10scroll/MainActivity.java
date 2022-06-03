package com.kbstar.a10scroll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {

    private ScrollView scrollView;
    private ImageView imageView;
    private BitmapDrawable bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView = findViewById(R.id.scrollView);
        imageView = findViewById(R.id.imageView);
        scrollView.setHorizontalScrollBarEnabled(true);

        Resources res = getResources();
        bitmap = (BitmapDrawable) res.getDrawable(R.drawable.kbstar);

        int width = bitmap.getIntrinsicWidth();
        int height = bitmap.getIntrinsicHeight();

        System.out.println("-----------------------------------------" + width + ", " + height);

        imageView.setImageDrawable(bitmap);
        imageView.getLayoutParams().width = width;
        imageView.getLayoutParams().height = height;
    }

    public void onButtonClicked(View v)
    {
        Resources res = getResources();
        bitmap = (BitmapDrawable) res.getDrawable(R.drawable.kbstar2);

        int width = bitmap.getIntrinsicWidth();
        int height = bitmap.getIntrinsicHeight();

        imageView.setImageDrawable(bitmap);
        imageView.getLayoutParams().width = width;
        imageView.getLayoutParams().height = height;
    }
}