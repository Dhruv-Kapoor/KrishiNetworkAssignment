package com.example.krishinetworkassignment.networking

import com.example.krishinetworkassignment.dataClasses.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//https://thekrishi.com/test/mandi?lat=28.44108136&lon=77.0526054&ver=89&lang=hi&crop_id=10
interface KrishiService {

    @GET("test/mandi")
    fun getOtherMandi(
        @Query("lat") lat: Double = 28.44108136,
        @Query("lon") lon: Double = 77.0526054,
        @Query("ver") ver: Int = 89,
        @Query("lang") lang: String = "hi",
        @Query("crop_id") cropId: Int = 10
    ): Call<Response>

}