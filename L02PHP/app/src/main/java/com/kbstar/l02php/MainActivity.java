package com.kbstar.l02php;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    Button btnInsert;
    EditText inputId, inputName, inputPass;
    TextView display;
    private String TAG = "APP2PHP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnInsert);
        inputId = findViewById(R.id.inputId);
        inputName = findViewById(R.id.inputName);
        inputPass = findViewById(R.id.inputPass);
        display = findViewById(R.id.display);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textId = inputId.getText().toString();
                String textName = inputName.getText().toString();
                String textPass = inputPass.getText().toString();

                InsertUser task = new InsertUser();
                task.execute("http://10.10.223.89/android/insert.php", textId, textName, textPass);

                inputId.setText("");
                inputName.setText("");
                inputPass.setText("");
            }
        });
    }

    class InsertUser extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            Log.d(TAG, "onPostExcute : " + response);
            display.setText(response + "\n");
        }
        // (주소, id, name, pass)

        @Override
        protected String doInBackground(String... params) {
            String serverUrl = (String) params[0];
            String id = (String) params[1];
            String name = (String) params[2];
            String pass = (String) params[3];

            // ... insert.php
            String postParams = "name="+name+"&id="+id+"&pass="+pass;

            try {
                URL url = new URL(serverUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParams.getBytes("UTF-8"));

                outputStream.flush();   // 버퍼가 비워질때까지(나간게 확인될때까지) 다음꺼 하지 말고 기다리기
                outputStream.close();


                InputStream inputStream;
                int resCode = httpURLConnection.getResponseCode();    // Response Code.. 200 오면 성공

                if(resCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }
                Log.d(TAG, "Response Code : " + resCode);

                InputStreamReader inputStreamReader = new InputStreamReader(
                        inputStream, "UTF-8"
                );
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String rcvData = "";
                String line = "";

                while((line = bufferedReader.readLine()) != null) {
                    rcvData = rcvData + line;
                }
                bufferedReader.close();
                Log.d(TAG, "RcvData = " + rcvData);

                return rcvData;

            } catch (Exception e) {
                Log.d(TAG, "[EXCEPTION] : " + e.getMessage());
                return "Error : " + e.getMessage();
            }
        }
    }
}