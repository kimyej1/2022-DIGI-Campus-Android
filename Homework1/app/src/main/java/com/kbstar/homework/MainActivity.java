package com.kbstar.homework;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

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

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment1)
                .commit();


        /////////////// Tabs
        TabLayout tabs = findViewById(R.id.tabs);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int idx = tab.getPosition();    // 0, 1, 2 중에 어떤 탭 선택?
                Fragment selected = null;       // 처음에는 아무것도 선택 안되어있음

                if(idx == 0)
                    selected = fragment1;
                else if(idx == 1)
                    selected = fragment2;
                else if(idx == 2)
                    selected = fragment3;

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, selected)
                        .commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }
}