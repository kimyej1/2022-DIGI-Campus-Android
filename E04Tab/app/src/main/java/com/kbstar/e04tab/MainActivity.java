package com.kbstar.e04tab;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolBar = this.findViewById(R.id.toolBar);
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(false);

        setSupportActionBar(toolBar);

    }
}