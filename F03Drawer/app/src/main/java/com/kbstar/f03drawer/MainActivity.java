package com.kbstar.f03drawer;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements FragmentCallback, NavigationView.OnNavigationItemSelectedListener {

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    DrawerLayout drawer;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this); // 클릭된걸 감지하는 녀석

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment1)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {  // drawer 떠있음 -> drawer만 닫기
            drawer.closeDrawer(GravityCompat.START);
        } else {                                        // drawer 없음 -> 원래 back 처럼 동작
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.menu1) {
            showToast("First Menu Selected.");
            onFragmentSelected(0, null);
        } else if(itemId == R.id.menu2) {
            showToast("Second Menu Selected.");
            onFragmentSelected(1, null);
        } else if(itemId == R.id.menu3) {
            showToast("Third Menu Selected.");
            onFragmentSelected(2, null);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentSelected(int position, Bundle bundle) {
        Fragment selectedFragment = null;

        if(position == 0) {
            selectedFragment = fragment1;
            toolbar.setTitle("Fragment 1");
        } else if(position == 1) {
            selectedFragment = fragment2;
            toolbar.setTitle("Fragment 2");
        } else if(position == 2) {
            selectedFragment = fragment3;
            toolbar.setTitle("Fragment 3");
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, selectedFragment)
                .commit();
    }

    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

}