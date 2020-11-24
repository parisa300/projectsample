package com.example.projectsample.Api

import com.example.projectsample.model.NewsResponce
import com.example.projectsample.Utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode :String ="us",
        @Query("page")
        pageNumber :Int=1,
        @Query("apiKey")
        apiKey :String =API_KEY
    ):Response<NewsResponce>


    @GET("v2/everyting")
    suspend fun searchForNews(
        @Query("q")
        searchQuery :String ,
        @Query("page")
        pageNumber :Int=1,
        @Query("apiKey")
        apiKey :String =API_KEY
    ):Response<NewsResponce>
}