package com.example.testproject.data

import com.google.gson.annotations.SerializedName

data class PostData(
    @SerializedName("id")
    val id: String,

    @SerializedName("userId")
    val userId: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("body")
    val body: String
)