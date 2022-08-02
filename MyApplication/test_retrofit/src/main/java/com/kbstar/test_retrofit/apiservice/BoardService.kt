package com.kbstar.test_retrofit.apiservice

import com.kbstar.test_retrofit.dto.Board
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BoardService {
    @POST("addBoard.kbstar")
    fun addBoard(@Body board:Board): Call<String>   // @메소드(URL)    함수이름(매개변수) : 리턴값

    @GET("listBoard.kbstar")
    fun listBoard(): Call<MutableList<Board>>

    @POST("updateBoard.do")
    fun updateBoard(@Body board: Board): Call<String>

    @POST("deleteBoard.do")
    fun deleteBoard(@Body board: Board): Call<String>

    @GET("getBoard.do")
    fun getBoard(@Body board: Board): Call<Board>
}