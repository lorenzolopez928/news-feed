package com.reign.mobilenews.repository.services

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebServicesBuilder constructor(var okHttpClient: OkHttpClient) {
    companion object {
        const val HOST = "https://hn.algolia.com/"
    }

    lateinit var webServices: WebServices

    fun init() {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("$HOST/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        webServices = retrofit.create(WebServices::class.java)
    }
}
