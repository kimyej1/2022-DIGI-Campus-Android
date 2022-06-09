package com.kbstar.d03imagefragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    ImageFragment imageFragment;
    int[] imageResourceList = { R.drawable.dream01, R.drawable.dream02, R.drawable.dream03 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        imageFragment = (ImageFragment) manager.findFragmentById(R.id.imageFragment);
    }

    public void changeImage(int index) {
        imageFragment.setImage( imageResourceList[index] );
    }

    public void changeImage2(int index) {
        imageFragment.setImage( imageResourceList[index] );
    }
}