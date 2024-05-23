package com.example.news.repositories

import com.example.news.api.RetrofitClient
import com.example.news.models.NewsAPIResponse
import retrofit2.Response

class NewsRepositories {

    private val api = RetrofitClient.api

    suspend fun getApi(query: String): Response<NewsAPIResponse> {
        return api.getNews(query)
    }

}