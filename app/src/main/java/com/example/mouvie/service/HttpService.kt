package com.example.mouvie.service

import com.example.mouvie.config.adapter.NumberAdapter
import com.example.mouvie.config.interceptor.AuthorizationInterceptor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

open class HttpService {

    private val okHttpClient by lazy {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(AuthorizationInterceptor)
            .build()
    }

    private val moshi by lazy {
        Moshi.Builder().add(NumberAdapter()).build()
    }

    val retro: Retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
}