package com.example.testproject

import android.app.Application
import com.example.testproject.network.RetrofitClient

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        RetrofitClient.createInstance()
    }
}