package com.example.news.api

class ApiUtils {

    companion object{

        fun getApi(): IApiManager {

            return RetrofitClient.retrofit.create(IApiManager::class.java)

        }
    }

}