package com.kbstar.myapplication.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.kbstar.myapplication.R;
import com.kbstar.myapplication.databinding.ActivityBagBinding;
import com.kbstar.myapplication.helper.DBHelper;
import com.kbstar.myapplication.recycler.BagAdapter;
import com.kbstar.myapplication.vo.CartVO;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BagActivity extends AppCompatActivity {

    ActivityBagBinding binding;
    ActionBar actionBar;

    String userName;
    SQLiteDatabase db;

    ArrayList<CartVO> items = new ArrayList<>();
    BagAdapter adapter;
    DecimalFormat myFormatter = new DecimalFormat("###,###");

    private int totalPrice = 0;
    private int totalCnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBagBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        actionBar = getSupportActionBar();
        userName = getIntent().getStringExtra("userName");
        setTitle(userName + " 님의 장바구니 🛍");

        db = new DBHelper(this, "testdb", null, 1).getWritableDatabase();


        String sql = "SELECT imageId, item, sum(price), sum(cnt) FROM myCart GROUP BY item";
        Cursor cursor = db.rawQuery(sql, null);

        int dataCnt = cursor.getCount();

        for(int i=0; i<dataCnt; i++) {
            cursor.moveToNext();

            String imageId = cursor.getString(0);
            String name = cursor.getString(1);
            int price = cursor.getInt(2);
            int cnt = cursor.getInt(3);

            CartVO cart = new CartVO(imageId, name, price, cnt);
            items.add(cart);

            totalPrice += price;
            totalCnt += cnt;
        }
        cursor.close();

        adapter = new BagAdapter(this, items);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.bagRecycler.setLayoutManager(layoutManager);
        binding.bagRecycler.setAdapter(adapter);

        binding.totalPrice.setText("총 " + myFormatter.format(totalCnt) + " 개    |    "
                + myFormatter.format(totalPrice) + " 원");

        // 지울 때 리스트 다시 보여주기
        adapter.bagDeleteLiveData.observe(this, new Observer<CartVO>() {
            @Override
            public void onChanged(CartVO cartVO) {
                items.remove(cartVO);
                totalCnt -= cartVO.getCnt();
                totalPrice -= cartVO.getPrice();

                adapter.notifyDataSetChanged();
                binding.totalPrice.setText("총 " + myFormatter.format(totalCnt) + " 개    |    "
                        + myFormatter.format(totalPrice) + " 원");
            }
        });

        // 다음 버튼 클릭
        binding.bagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalCnt != 0) {
                    Intent intent = new Intent(BagActivity.this, MapActivity.class);
                    intent.putExtra("userName", userName);
                    intent.putExtra("items", items);
                    startActivity(intent);
                } else if(totalCnt == 0) {
                    Toast.makeText(getApplicationContext(), "장바구니에 상품이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_home) {
            Intent intent = new Intent(BagActivity.this, ListActivity.class);
            startActivity(intent);
            finish();
        } else if(item.getItemId() == R.id.menu_bag){
            Intent intent = new Intent(BagActivity.this, BagActivity.class);
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
}