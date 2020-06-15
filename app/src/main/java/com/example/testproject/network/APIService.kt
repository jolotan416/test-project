package com.example.testproject.network

import com.example.testproject.data.PostData
import retrofit2.http.GET

interface APIService {
    @GET("posts")
    suspend fun getPosts(): List<PostData>
}