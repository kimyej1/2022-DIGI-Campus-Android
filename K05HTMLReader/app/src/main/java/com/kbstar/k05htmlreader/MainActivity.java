package com.kbstar.k05htmlreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "HtmlReader";
    private EditText editText;
    private Button button;
    private TextView textView;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputData = editText.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        requestData(inputData);
                    }
                }).start();
            }
        });
    }

    public void requestData(String urlData) {
        StringBuilder output = new StringBuilder();
        clearLog();

        try {
            URL url = new URL(urlData);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if(conn != null) {  // null 아니라는 건 = type casting 후 형식에 크게 문제가 없다
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);  // InputStream으로 서버로부터 응답을 받겠다

                int responseCode = conn.getResponseCode(); // 100번대 ~ 600번
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()
                        )
                );

                String line = null;     // null 해도 되고 ""(빈 문자) 해도 됨
                int counter = 0;

                while(true) {
                    line = reader.readLine();   // 한줄씩 읽어오기
                    if(line == null) {
                        break;
                    }
                    output.append(++counter + " : " + line + "\n");
                }

                reader.close();
                conn.disconnect();
            }

            printLog(output.toString()); //////////

        } catch (Exception e) {
            printLog("Exception : " + e.getMessage());
        }
    }

    private void printLog(String log) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.append(log + "\n");
            }
        });
    }

    private void clearLog() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("");
            }
        });
    }
}