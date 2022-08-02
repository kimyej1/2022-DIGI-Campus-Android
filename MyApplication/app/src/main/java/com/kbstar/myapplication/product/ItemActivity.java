package com.kbstar.myapplication.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.kbstar.myapplication.R;
import com.kbstar.myapplication.databinding.ActivityItemBinding;
import com.kbstar.myapplication.helper.DBHelper;
import com.kbstar.myapplication.vo.ProductVO;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class ItemActivity extends AppCompatActivity {

    ActivityItemBinding binding;
    ActionBar actionBar;

    Bitmap bitmap;
    String imageUrl = "http://10.10.223.89:7777/resources/images/";
    String userName;

    DecimalFormat myFormatter = new DecimalFormat("###,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        actionBar = getSupportActionBar();

        // ListActivity에서 넘긴 상품 데이터 받아오기
        ProductVO product = getIntent().getParcelableExtra("product");
        userName = getIntent().getStringExtra("userName");
        setTitle(userName + " 님의 냉장고 🤍");

        // NumberPicker 설정
        binding.numberPicker.setMaxValue(5);
        binding.numberPicker.setMinValue(1);
        binding.numberPicker.setValue(1);

        binding.numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                binding.specTotal.setText("총 " + myFormatter.format(product.getPrice() * binding.numberPicker.getValue()) + " 원");
            }
        });

        // bitmap이 너무 커서 인텐트로 가져오지 못했음.. 그래서 서버이미지 다시 불러오기
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(imageUrl + product.getImageId());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

        try{
            thread.join();

            binding.specImage.setImageBitmap(bitmap);
            binding.specName.setText(product.getName());
            binding.specPrice.setText(myFormatter.format(product.getPrice()) + " 원 / 개");
            binding.specTotal.setText("총 " + myFormatter.format(product.getPrice() * binding.numberPicker.getValue()) + " 원");

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 장바구니 Sqlite DB 연결
        DBHelper helper;
        SQLiteDatabase db;

        helper = new DBHelper(this, "testdb", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        // 장바구니 버튼
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int totalPrice = product.getPrice() * binding.numberPicker.getValue();
                    String sql = "INSERT INTO myCart (imageId, item, price, cnt) VALUES ('"
                            + product.getImageId() + "','"
                            + product.getName() + "',"
                            + totalPrice  + ","
                            + binding.numberPicker.getValue() + ");";
                    db.execSQL(sql);

                    Toast.makeText(getApplicationContext(), "장바구니에 담았습니다", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"실패", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    // AppBar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_home) {
            Intent intent = new Intent(ItemActivity.this, ListActivity.class);
            startActivity(intent);
            finish();
        } else if(item.getItemId() == R.id.menu_bag){
            Intent intent = new Intent(ItemActivity.this, BagActivity.class);
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