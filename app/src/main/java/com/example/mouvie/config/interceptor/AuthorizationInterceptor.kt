package com.example.mouvie.config.interceptor

import okhttp3.Interceptor
import okhttp3.Response

object AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithHeader = chain.request()
            .newBuilder()
            .header(
                "Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2MWUzNWZhZmNjZDFmNDkyMzk2MjNhMzc2ODA0MzQxYiIsInN1YiI6IjYxYTVkNWUzMTgwZGVhMDA0NTVhOTg1ZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.fFFtJc_C_nNPl5_fFBK_51G5Jspth8On7TD-8zO32jo"
            ).build()
        return chain.proceed(requestWithHeader)
    }
}