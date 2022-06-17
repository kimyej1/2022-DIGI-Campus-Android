package com.kbstar.i04movie;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText input;
    private Button button;

    private RequestQueue requestQueue;
    RecyclerView recyclerView;
    MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.input);
        button = findViewById(R.id.button);
        recyclerView = findViewById(R.id.recyclerView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process();
            }
        });


        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        );

        recyclerView.setLayoutManager(layoutManager);
        adapter = new MovieAdapter();
        recyclerView.setAdapter(adapter);
    }

    public void process() {
        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=304eba0921b36f45a1ff72c27591765f&targetDt="
                + input.getText().toString();

        adapter.removeAllItems();

        StringRequest request = new StringRequest(
                Request.Method.GET,                                 // 1. 메소드
                url,                                                // 2. URL
                new Response.Listener<String>() {                   // 3. 성공했을 때
                    @Override
                    public void onResponse(String response) {
                        processJson(response);
                    }
                }, new Response.ErrorListener() {                   // 4. 실패했을 때
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MOVIE", "++++++++++++++++++++ ERROR Responce : " + error.getMessage());
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void processJson(String jsonData) {
        Gson gson = new Gson();
        MovieList movieList = gson.fromJson(jsonData, MovieList.class);

        for(int i=0; i<movieList.boxOfficeResult.dailyBoxOfficeList.size(); i++) {
            Movie movie = movieList.boxOfficeResult.dailyBoxOfficeList.get(i);
            adapter.addItem(movie);
        }

        adapter.notifyDataSetChanged();
    }

}