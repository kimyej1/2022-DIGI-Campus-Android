package com.kbstar.myapplication.user;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kbstar.myapplication.helper.AppApplication;
import com.kbstar.myapplication.product.ListActivity;
import com.kbstar.myapplication.TutorialActivity;
import com.kbstar.myapplication.apiservice.ApiService;
import com.kbstar.myapplication.databinding.ActivityMainBinding;
import com.kbstar.myapplication.vo.UserVO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ApiService apiService;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("로그인");

        apiService = AppApplication.apiService;

        // 처음 실행 시 튜토리얼 보여주기
        SharedPreferences pref = getSharedPreferences("checkFirst", Activity.MODE_PRIVATE);
        boolean checkFirst = pref.getBoolean("checkFirst", false);

        if (!checkFirst) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("checkFirst", true);
            editor.apply();
            finish();

            Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
            startActivity(intent);
        }

        // 튜토리얼 실행(debug)
        binding.moveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ActivityResultLauncher<Intent> registerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent resultIntent = result.getData();
                        UserVO resultVO = resultIntent.getParcelableExtra("result");
                        binding.loginId.setText(resultVO.getUserId());
                        binding.loginPwd.setText(resultVO.getUserPwd());
                    }
                }
        );

        // 회원가입 버튼
        binding.moveRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                registerLauncher.launch(intent);
            }
        });

        // 로그인 버튼
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    public void login() {
        String id = binding.loginId.getText().toString();
        String pwd = binding.loginPwd.getText().toString();
        Log.d("MainActivity Login", "Login Attempt : (id, pwd) = (" + id + ", " + pwd + ")");

        UserVO loginUser = new UserVO(id, pwd);
        Call<UserVO> call = apiService.login(loginUser);

        call.enqueue(new Callback<UserVO>() {
            @Override
            public void onResponse(Call<UserVO> call, Response<UserVO> response) {

                Log.d("MainActivity Login", "Success! " + response.body());

                // 서버에서 있는 사용자라고 하면,,
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                Log.d("yeji", response.body().getUserName());
                intent.putExtra("loginUser", response.body());
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<UserVO> call, Throwable t) {
                Log.d("MainActivity Login", "Fail.. " + t.getLocalizedMessage());
                Toast.makeText(getApplicationContext(), "로그인 정보를 확인하세요.", Toast.LENGTH_SHORT).show();

                binding.loginId.setText("");
                binding.loginPwd.setText("");
            }
        });
    }
}