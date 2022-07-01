package com.kbstar.m02volley;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private String TAG = "Volley-RegisterActivity";
    private EditText inputId, inputPass;
    private Button btnLogin;
    Boolean isSuccess = false;
    private static String LOGIN_ID;  // static 안써도됨
    private static String LOGIN_NAME;
    private static String LOGIN_LEVEL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputId = findViewById(R.id.et_id);
        inputPass = findViewById(R.id.et_pass);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    public void login() {
        // call(id, name, pass, "POST", "htt...", lis, err)
        String id = inputId.getText().toString();
        String pass = inputPass.getText().toString();

        // Response 라는 객체 밑에, inner Class로 Listener가 있다.
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "OK Response = " + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String loginResult = jsonObject.getString("result");    // 이걸 보고 성공(1)/실패(-1) 확인

                    String printSuccessFail = "";

                    if(loginResult.equals("1")) {
                        printSuccessFail = "Succeed";
                        isSuccess = true;

                        // id = jsonObject.getString("id"); 이렇게 다른값들도 가져오기
                        LOGIN_ID = jsonObject.getString("id");
                        LOGIN_NAME = jsonObject.getString("name");
                        LOGIN_LEVEL = jsonObject.getString("level");

                    } else {
                        printSuccessFail = "Failed";
                        isSuccess = false;  // default false지만, 전역변수라 다른데서 가져다 썼을 수도 있으니까..
                    }

                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Result")
                            .setMessage("Login " + printSuccessFail)
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(isSuccess) {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra("LOGIN_ID", LOGIN_ID);
                                        intent.putExtra("LOGIN_NAME", LOGIN_NAME);
                                        intent.putExtra("LOGIN_LEVEL", LOGIN_LEVEL);

                                        startActivity(intent);
                                        finish();   // 가입 완료 후 메인으로 돌아가기
                                    } else {
                                        inputId.setText("");
                                        inputPass.setText("");
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();

                } catch (Exception e) {

                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse() : " + error.getMessage());
            }
        };

        // Volley로 회원 입력값을 전송
        LoginRequest loginRequest = new LoginRequest(
                id, pass, Request.Method.POST, responseListener, errorListener
        );

        loginRequest.setShouldCache(false);    // 옛날꺼 똑같은거 있다고 쓰지말고 매번 새로운값 사용해라
        RequestQueue q = Volley.newRequestQueue(getApplicationContext());
        q.add(loginRequest);
    }
}