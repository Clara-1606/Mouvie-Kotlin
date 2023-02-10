package com.example.mouvie.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.mouvie.config.interceptor.AuthorizationInterceptor
import com.example.mouvie.model.movie.MovieResponse
import com.example.mouvie.config.adapter.NumberAdapter
import com.example.mouvie.service.movie.IMovieService
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingScreen(
    navController: NavHostController
) {
    Column {
        Text(text = "Trending Screen")
        FilledIconButton(onClick = {
            val okHttpClient = OkHttpClient()
                .newBuilder()
                .addInterceptor(AuthorizationInterceptor)
                .build()

            val moshi = Moshi.Builder().add(NumberAdapter()).build()

            val retro = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

            val service = retro.create(IMovieService::class.java)
            val request = service.popularMovies(1,"fr")

            request.enqueue(object : Callback<MovieResponse>{
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    val allMovies = response.body()
                    for (m in allMovies!!.results)
                        Log.i(
                            "TrendingScreen",
                                "TITLE: ${m.title} \n" +
                                    "DATE: ${m.release_date} \n " +
                                    "LANGUAGE: ${m.original_language} "
                        )
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.i("TrendingScreen", "on FAILURE!!!!")
                }
            })
        }) {
            Text(text = "Send request")
        }
    }
}