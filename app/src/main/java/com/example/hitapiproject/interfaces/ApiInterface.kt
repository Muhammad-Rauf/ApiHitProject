package com.example.hitapiproject.interfaces

import com.example.hitapiproject.model.Data
import com.example.hitapiproject.model.Meme
import com.example.hitapiproject.model.Response
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
        @GET("/get_memes")
        fun getData() : Call<Response>

}