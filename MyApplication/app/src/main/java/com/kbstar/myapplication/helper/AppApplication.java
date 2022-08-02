package com.kbstar.myapplication.helper;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kbstar.myapplication.apiservice.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppApplication extends Application {

    private static Retrofit retrofit;
    private static Gson gson;

    public static ApiService apiService;

    @Override
    public void onCreate() {
        super.onCreate();

        //Retrofit 초기 설정.......
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = (new OkHttpClient()).newBuilder()
                .addInterceptor(interceptor)
                .connectTimeout(500, TimeUnit.SECONDS)
                .readTimeout(500, TimeUnit.SECONDS)
                .writeTimeout(500, TimeUnit.SECONDS)
                .build();

        gson = new GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.10.223.89:7777/mobile/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        //network 시에 호출할 함수를 등록한 인터페이스를 retrofit 에 알려줘야..
        apiService = retrofit.create(ApiService.class);
    }


}

