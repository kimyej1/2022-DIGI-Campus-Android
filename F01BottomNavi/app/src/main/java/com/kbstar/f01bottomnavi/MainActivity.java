package com.kbstar.f01bottomnavi;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.navi1:
                        showToast("menu 1");
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, fragment1)
                                .commit();
                        break;
                    case R.id.navi2:
                        showToast("menu 2");
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, fragment2)
                                .commit();
                        break;
                    case R.id.navi3:
                        showToast("menu 3");
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, fragment3)
                                .commit();
                        break;
                    default:
                        showToast("default");
                        break;
                }
                return false;
            }
        });
    }

    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

}