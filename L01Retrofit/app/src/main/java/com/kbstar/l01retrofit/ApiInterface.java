package com.kbstar.l01retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    // https://repres.in/api/login?id=text&pass=1111 - POST 통신
    // *** 인터페이스이므로, 이걸 implement 하는 클래스들은 아래 메소드들을 필수로 만들어야 한다!

    @POST("api/login")      // POST 방식으로 api/login이 날라가면..(https://repres.in : base URL)
    Call<ResLoginData> requestPostLogin(@Body ReqLoginData reqLoginData);

    @GET("api/users")
    Call<ResUserData> requestGetUsers(@Query(value="page", encoded = true) String page);
}
