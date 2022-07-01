package com.kbstar.m01weblist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class InfoActivity extends AppCompatActivity {

    private TextView tv_id, tv_idx, tv_name, tv_level, tv_memo, display;
    private Button btnClose;

    private String extraIdx, jsonString;
    private String SERVER_URL = "http://10.10.223.89/android/info.php";
    private String TAG = "InfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        tv_id = findViewById(R.id.tv_id);
        tv_idx = findViewById(R.id.tv_idx);
        tv_name = findViewById(R.id.tv_name);
        tv_level = findViewById(R.id.tv_level);
        tv_memo = findViewById(R.id.tv_memo);

        display = findViewById(R.id.display);
        btnClose = findViewById(R.id.btnClose);

        Intent intent = getIntent();
        if(intent !=null)
        {
            Bundle bundle= intent.getExtras();
            String idx = bundle.getString("idx");
            extraIdx = idx;

            Log.d("InfoActivity","idx= "+ idx);
            display.setText("Key idx = " + idx + "\n");
        }

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        InfoActivity.GetData task = new InfoActivity.GetData();
        task.execute(SERVER_URL, extraIdx);
    }

    private class GetData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "Param 1 : " + params[1]);   // param[1] = extraIdx
            String serverURL = params[0];
            String postParam = "idx="+params[1];

            try {
                URL url = new URL(serverURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setConnectTimeout(5000); // 타임아웃은 꼭 걸어줘야한다 + 메소드도 꼭 정해야됨
                connection.setRequestMethod("POST");
                connection.connect();

                // output으로 글씨 쓸때는 charset을 정해주는게 좋다..!
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(postParam.getBytes("UTF-8"));

                outputStream.flush();
                outputStream.close();

                int resCode = connection.getResponseCode(); // resCode는 꼭 로그에 남겨두자!!
                Log.d(TAG, "Response Code : " + resCode);

                InputStream inputStream;

                if(resCode == HttpURLConnection.HTTP_OK) {  // 200(OK)일 때
                    inputStream = connection.getInputStream();
                } else {
                    inputStream = connection.getErrorStream();
                }

                InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader br = new BufferedReader(isr);

                String rcvString = "";
                String line = "";

                while((line = br.readLine()) != null) {
                    rcvString = rcvString + line;
                    Log.d(TAG, line);
                }

                br.close();
                return rcvString;

            } catch(Exception e) {
                Log.d(TAG, "Exception : " + e.getMessage());
                return null;
            }
        }


        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            jsonString = response;
            showData();
        }
    }

    public void showData() {
        // jsonString, kbUsers
        String JSON_TAG = "kbusers";
        String JSON_TAG_IDX = "idx";
        String JSON_TAG_ID = "id";
        String JSON_TAG_NAME = "name";
        String JSON_TAG_LEVEL = "level";
        String JSON_TAG_MEMO = "memo";

        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            tv_idx.setText( jsonObject.getString(JSON_TAG_IDX) );
            tv_id.setText( jsonObject.getString(JSON_TAG_ID) );
            tv_name.setText( jsonObject.getString(JSON_TAG_NAME) );
            tv_level.setText( jsonObject.getString(JSON_TAG_LEVEL) );
            tv_memo.setText( jsonObject.getString(JSON_TAG_MEMO) );

        } catch (Exception e) {
            Log.d(TAG, "Exception : " + e.getMessage());
        }
    }
}