package com.kbstar.m01weblist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.util.Output;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String SERVER_URL = "http://10.10.223.89/android/list.php";
    private String TAG = "WEB_LIST";

    private Button btnList;
    private RecyclerView recyclerView;
    private TextView display;

    private ArrayList<UserData> arrayList;
    private UserAdapter adapter;
    private String jsonString;  // JSON을 일단 String으로 받아와서 -> parsing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        btnList = findViewById(R.id.btnList);
        display = findViewById(R.id.display);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        adapter = new UserAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.setText("Start Get List...\n");
                arrayList.clear();  // 한번 가져와서 뿌린 후 클리어
                adapter.notifyDataSetChanged(); // Refresh

                GetData task = new GetData();
                task.execute(SERVER_URL, "NoParam");    // 원래는 두번째자리도 파라미터자리
            }
        });
    }

    private class GetData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "Param 1 : " + params[1]);   // param[1] = "NoParam"(String)
            String serverURL = params[0];

            // postParam은 여기서는 필요없는데, 원래 이자리에 들어가야한다고 표시
            // 원래 param은 "name="+name+"&id="+id+"&pass="+pass 이런식..
            String postParam = "dummy="+params[1];

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
//                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

//                StringBuilder sb = new StringBuilder();   // 이거 만들어서 sb.append(line) 해도 됨(개취)
                String rcvString = ""; // 한줄씩 넣어서 한번에 출력 -> 한번에 써지는걸 보장하기 위해서!(하나씩 출력하면 쓰레드 중간에 뭐가 끼어들어서 들어올까봐)
                String line = "";

                while((line = br.readLine()) != null) {
                    rcvString = rcvString + line;
                    // sb.append(line);

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

            if(response != null) {
                display.setText(response);
                jsonString = response;

                showList();
            }
        }
    }

    public void showList() {
        // jsonString, kbUsers
        String JSON_TAG = "kbusers";
        String JSON_TAG_IDX = "idx";
        String JSON_TAG_ID = "id";
        String JSON_TAG_NAME = "name";
        String JSON_TAG_LEVEL = "level";
        String JSON_TAG_MEMO = "memo";

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(JSON_TAG);

            Log.d(TAG, "JSON List length = " + jsonArray.length());

            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                UserData userData = new UserData();

                userData.setUserIdx(item.getString(JSON_TAG_IDX));
                userData.setUserId(item.getString(JSON_TAG_ID));
                userData.setUserName(item.getString(JSON_TAG_NAME));
                userData.setUserLevel(item.getString(JSON_TAG_LEVEL));
                userData.setUserMemo(item.getString(JSON_TAG_MEMO));

                arrayList.add(userData);
                adapter.notifyDataSetChanged(); // Refresh
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception : " + e.getMessage());
        }
    }
}