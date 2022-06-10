package com.kbstar.e02actionbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kbstar.e02actionbar.R;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  // 옵션 안에 아이템이 선택되면 발생하는 이벤트

        // 아이템 한두개면 이렇게 해도 노상관
        if(item.getItemId() == R.id.menu_refresh) {
            showToast("Refresh");
        }

        // 아이템이 많아지면 이런식으로~
        int itemId = item.getItemId();
        switch(itemId) {
            case R.id.menu_refresh:
                doRefresh();    // 길어지고 여러번쓰면 함수, 아니면 그냥 써도됨
                break;
            case R.id.menu_search:
                showToast("Search");
                writeText("Search");
                break;
            case R.id.menu_settings:
                showToast("Settings");
                writeText("Settings");
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void doRefresh() {
        showToast("Refresh");
        writeText("Refresh");
    }

    private void writeText(String msg) {
        textView.setText(msg);
    }
}