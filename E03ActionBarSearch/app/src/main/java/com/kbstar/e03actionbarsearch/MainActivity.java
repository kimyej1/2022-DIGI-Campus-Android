package com.kbstar.e03actionbarsearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;
    ActionBar actionBar;
    EditText keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        actionBar = getSupportActionBar();

//        actionBar.hide();  // 상단 액션바 가릴 때

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionBar.setLogo(R.drawable.home);
                actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME|ActionBar.DISPLAY_USE_LOGO);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  // 옵션 안에 아이템이 선택되면 발생하는 이벤트

        int itemId = item.getItemId();
        switch(itemId) {
            case R.id.menu_refresh:
                showToast("Refresh");
                textView.setText("Refresh");
                break;
            case R.id.menu_search:
                showToast("Search");
                textView.setText("Search");
                break;
            case R.id.menu_settings:
                showToast("Settings");
                textView.setText("Settings");
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
        View view = menu.findItem(R.id.menu_search).getActionView();

        // menu_main 이 inflate 되어있어야만 editText keyword를 찾아올 수 있음!
        keyword = view.findViewById(R.id.keyword);
        showToast(keyword.getText().toString());                                    // 처음에 옵션메뉴 만들어질때만 발생

        keyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {   // 액션 일어날때마다 추가 발생
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                showToast("Editor Event ! " + keyword.getText().toString());
                return false;
            }
        });

        return true;
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}