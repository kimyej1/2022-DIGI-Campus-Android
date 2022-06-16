package com.kbstar.i02volley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

// 전통적인 방식은 I01인데, I02로 하면 스레드 처리 등 내부적인 처리 알아서 해줌
public class MainActivity extends AppCompatActivity {

    private EditText input;
    private Button button;
    private TextView textView;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.input);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process();
            }
        });

        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }

    public void printLog(String msg) {
        textView.append(msg + "\n");
    }

    public void process() {
        String url = input.getText().toString();
        textView.setText("");

        StringRequest request = new StringRequest(
                Request.Method.GET,                                 // 1. 메소드
                url,                                                // 2. URL
                new Response.Listener<String>() {                   // 3. 성공했을 때
                    @Override
                    public void onResponse(String response) {
                        printLog(response);
                    }
                }, new Response.ErrorListener() {                   // 4. 실패했을 때
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        printLog("Error Occurred : " + error.getMessage());
                    }
                }
        );

        // RequestQueue에 넣어주기만 하면 끝!
        // 받은 응답이 있어도, 무시하고 새로 요청하고, 결과 표시할 것인가?
        request.setShouldCache(false);
        requestQueue.add(request);
    }
}