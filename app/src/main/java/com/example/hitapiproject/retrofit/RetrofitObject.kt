package com.example.hitapiproject.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {
    private const val BASE_URL = "https://api.imgflip.com/"
    private val client=OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.imgflip.com/")
            .addConverterFactory(GsonConverterFactory.create()).client(client)
            .build()


   // val memeApi: InterfaceMemeApi = retrofit.create(InterfaceMemeApi::class.java)
}
