package com.example.testproject.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {
    companion object {
        fun createInstance() {
            retrofitClient = retrofitClient ?: RetrofitClient()
        }

        fun getInstance(): RetrofitClient {
            return retrofitClient!!
        }

        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
        private var retrofitClient: RetrofitClient? = null
    }

    private var retrofit: Retrofit

    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun getApiService(): APIService = retrofit.create(APIService::class.java)
}