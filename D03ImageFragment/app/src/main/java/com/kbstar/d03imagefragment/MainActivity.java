package com.kbstar.d03imagefragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ListFragment.ImageSelectCallback {

    ImageFragment imageFragment;
    int[] imageResourceList = { R.drawable.dream01, R.drawable.dream02, R.drawable.dream03 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        imageFragment = (ImageFragment) manager.findFragmentById(R.id.imageFragment);
    }

    @Override
    public void changeImage(int index) {

        // 이렇게 코딩하면.. 그림이 많아졌을 때 지저분해짐
//        if(index == 1) { imageFragment.setImage(R.drawable.dream01); }
//        if(index == 2) { imageFragment.setImage(R.drawable.dream02); }
//        if(index == 3) { imageFragment.setImage(R.drawable.dream03); }

        // imageResourceList 사용!
        imageFragment.setImage( imageResourceList[index] );
    }
}