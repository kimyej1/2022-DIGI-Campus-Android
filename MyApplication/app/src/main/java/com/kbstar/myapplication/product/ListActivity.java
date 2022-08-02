package com.kbstar.myapplication.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.kbstar.myapplication.R;
import com.kbstar.myapplication.apiservice.ApiService;
import com.kbstar.myapplication.databinding.ActivityListBinding;
import com.kbstar.myapplication.recycler.ProductAdapter;
import com.kbstar.myapplication.helper.AppApplication;
import com.kbstar.myapplication.vo.ProductVO;
import com.kbstar.myapplication.vo.UserVO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    ProductAdapter adapter;
    ArrayList<ProductVO> items = new ArrayList<>();
    ApiService apiService;
    ActivityListBinding binding;
    ActionBar actionBar;

    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Main에서 넘긴 User 정보
        UserVO loginUser = getIntent().getParcelableExtra("loginUser");
        userName = loginUser.getUserName();
        setTitle(userName + " 님의 냉장고 🤍");


        //초기에는 빈 리스트를 설정.. 항목이 아무것도 안찍히고.. 나중에 서버에서 데이터를 받아서 화면 갱신하면 됨...
        adapter = new ProductAdapter(this, items, userName);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);

        apiService = AppApplication.apiService;
        actionBar = getSupportActionBar();

        Call<ArrayList<ProductVO>> call = apiService.listFruit();
        call.enqueue(new Callback<ArrayList<ProductVO>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductVO>> call, Response<ArrayList<ProductVO>> response) {
                ArrayList<ProductVO> result = response.body();
                //recyclerview 의 항목을 만들어주는 adapter 에 적용한 데이터에 서버에서 받은 데이터.. 셋팅하고..
                items.addAll(result);
                //화면 갱신해.. 명령..
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<ProductVO>> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int size = items.size();
                items.clear();
                adapter.notifyItemRangeRemoved(0, size);
                makeList(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // qr 화면으로 이동
        binding.qrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QrActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_home) {
            Intent intent = new Intent(ListActivity.this, ListActivity.class);
            startActivity(intent);
            finish();
        } else if(item.getItemId() == R.id.menu_bag){
            Intent intent = new Intent(ListActivity.this, BagActivity.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void makeList(TabLayout.Tab tab){
        Call<ArrayList<ProductVO>> call = null;

        if(tab == binding.tabLayout.getTabAt(0))
            call =apiService.listFruit();
        else if(tab == binding.tabLayout.getTabAt(1))
            call = apiService.listMeat();
        else if(tab == binding.tabLayout.getTabAt(2))
            call= apiService.listDairy();

        call.enqueue(new Callback<ArrayList<ProductVO>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductVO>> call, Response<ArrayList<ProductVO>> response) {
                ArrayList<ProductVO> result = response.body();
                items.addAll(result);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<ProductVO>> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });
    }
}