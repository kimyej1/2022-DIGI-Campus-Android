package com.kbstar.myapplication.apiservice;

import com.kbstar.myapplication.vo.ProductVO;
import com.kbstar.myapplication.vo.UserVO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login.kbstar")
    Call<UserVO> login(@Body UserVO userVO);

    @POST("register.kbstar")
    Call<Integer> register(@Body UserVO userVO);

    @GET("listFruit.kbstar")
    Call<ArrayList<ProductVO>> listFruit();

    @GET("listMeat.kbstar")
    Call<ArrayList<ProductVO>> listMeat();

    @GET("listDairy.kbstar")
    Call<ArrayList<ProductVO>> listDairy();

    @POST("selectRow.kbstar")
    Call<ProductVO> selectRow(@Body ProductVO productVO);
}
