package com.example.projectsample.api

import com.example.projectsample.Utils.Constants.Companion.BASE_URL

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{

        private val Retrofit by lazy {

            val logging=HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client =OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        }

        val api by lazy {
            Retrofit.create(NewsApi::class.java)
        }
    }
}