package com.kbstar.test_retrofit

import android.app.Application
import com.google.gson.GsonBuilder
import com.kbstar.test_retrofit.apiservice.BoardService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application() {
    var boardService: BoardService

    val retrofit: Retrofit
        get() {
            // retrofit을 사용하면서 필수는 아니지만,
            // 이용하면 request, response 로그를 자동으로 뿌려줘서 디버깅 하기 쉽다!
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient().newBuilder().addInterceptor(interceptor).build()

            // 날짜 데이터 -> 날짜를 문자열 타입으로 취급하면 문제가 없겠지만, java의 Date 타입으로 취급한다면..
            // 안드로이드에서 Date 타입의 포맷과, 스프링에서 해석하는 Date타입의 포맷이 맞지 않아서 에러 발생!
            val gson = GsonBuilder()
                .setLenient()   // gson으로 json데이터를 파싱할건데, 서버에서 단순 문자열(success 등)을 리턴하는 경우도 있어서
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") // 서버와 Date 포맷을 맞추기 위해서..!
                .create()

            return Retrofit.Builder()
                .baseUrl("http://10.10.223.89:7777/mobile/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson)) // create 매개변수 없어도되는데 포맷때문에..
                .build()
        }

    init {
        boardService = retrofit.create(BoardService::class.java)
    }
}