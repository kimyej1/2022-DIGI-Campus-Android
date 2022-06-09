package com.kbstar.d02fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    MainFragment mainFragment;
    MenuFragment menuFragment;
    KbFragment kbFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = new MainFragment();
        menuFragment = new MenuFragment();
        kbFragment = new KbFragment();
    }

    public void changeFragment(int index) {

        if(index == 1) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, menuFragment)
                    .commit();
        }

        if(index == 0) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, mainFragment)
                    .commit();
        }

        if(index == 2) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, kbFragment)
                    .commit();
        }
    }
}