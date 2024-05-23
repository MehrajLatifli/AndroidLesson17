package com.example.news.api

import com.example.news.models.NewsAPIResponse
import com.example.news.utilities.Constants.API_Key
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface IApiManager {

    @Headers("x-api-key: ${API_Key}")
    @GET("/v2/everything")
    suspend fun getNews(
        @Query("q") query: String?
    ): Response<NewsAPIResponse>
}
