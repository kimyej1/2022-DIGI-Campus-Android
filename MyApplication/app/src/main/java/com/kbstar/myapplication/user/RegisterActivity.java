package com.kbstar.myapplication.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kbstar.myapplication.helper.AppApplication;
import com.kbstar.myapplication.apiservice.ApiService;
import com.kbstar.myapplication.databinding.ActivityRegisterBinding;
import com.kbstar.myapplication.vo.UserVO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ApiService apiService;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("회원가입");

        apiService = AppApplication.apiService;

        binding.registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    public void register() {
        String id = binding.registId.getText().toString();
        String pwd = binding.registPwd.getText().toString();
        String name = binding.registName.getText().toString();
        Log.d("RegisterActivity", id + ", " + pwd + ", " + name);

        UserVO registerUser = new UserVO(id, pwd, name);
        Call<Integer> call = apiService.register(registerUser);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.d("RegisterActivity", "Success! " + response.body());

                if(response.body() >= 1) {
                    Log.d("", "정상 아이디!");
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
                    getIntent().putExtra("result", registerUser);
                    setResult(RESULT_OK, getIntent());
                    finish();

                } else {
                    Log.d("", "중복 등 무효 아이디..");
                    Toast.makeText(getApplicationContext(), "입력한 정보를 확인하세요.", Toast.LENGTH_SHORT).show();

                    binding.registId.setText("");
                    binding.registPwd.setText("");
                    binding.registName.setText("");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("RegisterActivity", "Fail.. " + t.getLocalizedMessage());
            }
        });
    }
}