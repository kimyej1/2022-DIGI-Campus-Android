package com.kbstar.l01retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Retrofit 객체 만들기
public class HttpClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://reqres.in/";

    public static Retrofit getRetrofit() {
        // HTTP 통신을 위한 Retrofit 객체를 만들어서 반환
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BASE_URL);
        builder.addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.build();
        return retrofit;
    }
}