package com.example.news.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.api.ApiUtils
import com.example.news.models.Article
import com.example.news.models.NewsAPIResponse
import com.example.news.repositories.NewsRepositories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class HomeViewModel : ViewModel() {

    private val api = ApiUtils.getApi()

    private val _items = MutableLiveData<List<Article>>()
    val items: LiveData<List<Article>> = _items

    private val _categories = MutableLiveData<Array<String>>()
    val categories: LiveData<Array<String>> = _categories

    private val _random = MutableLiveData<String>()
    val random: LiveData<String> = _random

    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    val repo = NewsRepositories()

    init {
        _categories.value = arrayOf(
            "Azerbaijan", "Turkey", "Kazakhstan", "Turkmenistan", "Uzbekistan", "Kyrgyzstan",
            "Northern Cyprus", "Tatarstan Republic", "Bashkortostan Republic", "Tuva Republic",
            "Yakutia Republic", "Xinjiang Uyghur Autonomous Regio", "Xinjiang Uyghur Region",
            "Gansu Province", "Political", "Economic", "Information Technology", "Culture and Arts",
            "Sports", "Nature and Conservation", "Health and Medical"
        )




    }

    fun updateRandom() : String {

        val randomIndex = (0 until (_categories.value?.size ?: 0)).random()
        val randomValue = _categories.value?.get(randomIndex) ?: ""
        _random.value = randomValue


        return randomValue
    }



    fun getdata(query: String) {
        isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.getApi(query)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        response.body()?.articles?.let {
                            if (it.isNotEmpty()) {
                                _items.value = it
                            } else {
                                error.value = "No found"

                                _items.value= emptyList()
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    error.value = e.localizedMessage ?: ""
                }
            } finally {
                withContext(Dispatchers.Main) {
                    loading.value = false
                }
            }
        }
    }
}
