package com.example.krishinetworkassignment.networking

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KrishiClient {

    private val gson: Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://thekrishi.com")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val api = retrofit.create(KrishiService::class.java)
}