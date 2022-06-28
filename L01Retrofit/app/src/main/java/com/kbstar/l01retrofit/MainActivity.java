package com.kbstar.l01retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnGet, btnPost;
    private TextView display;
    private String TAG = "Retrofit";

    ApiInterface api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGet = findViewById(R.id.btnGet);
        btnPost = findViewById(R.id.btnPost);
        display = findViewById(R.id.display);

        api = HttpClient.getRetrofit().create(ApiInterface.class);

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.setText("");
                display.append("call request Get List\n");

                requestGet();
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.setText("");
                display.append("call request post\n");

                requestPost();
            }
        });
    }

    public void requestPost() {  // 로그인
        ReqLoginData reqLoginData = new ReqLoginData("eve.holt@reqres.in", "pistol");
        Call<ResLoginData> call = api.requestPostLogin(reqLoginData);

        // 비동기로 백그라운드 쓰레드
        call.enqueue(new Callback<ResLoginData>(){
            @Override
            public void onResponse(Call<ResLoginData> call, Response<ResLoginData> response) {
                Log.d(TAG, "POST OK Response");
                Log.d(TAG, response.body().toString());

                display.append("token = " + response.body().getToken() + "\n");
                display.append("End of onResponse()");
            }

            @Override
            public void onFailure(Call<ResLoginData> call, Throwable t) {
                Log.d(TAG, "onFailure while POST");
            }
        });
    }

    public void requestGet() {
        Call<ResUserData> call = api.requestGetUsers("2");

        call.enqueue(new Callback<ResUserData>() {
            @Override
            public void onResponse(Call<ResUserData> call, Response<ResUserData> response) {
                Log.d(TAG, "Get List OK Response");

                display.append(response.body().toString());
            }

            @Override
            public void onFailure(Call<ResUserData> call, Throwable t) {
                Log.d(TAG, "onFailure while Get List");
            }
        });
    }
}