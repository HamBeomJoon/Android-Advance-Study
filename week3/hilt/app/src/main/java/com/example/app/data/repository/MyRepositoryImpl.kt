package com.example.app.data.repository

import android.app.Application
import com.example.app.R
import com.example.app.data.remote.MyApi
import com.example.app.domain.repository.MyRepository
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val api: MyApi,
    private val appContext: Application
) : MyRepository {

    init {
        val appName = appContext.getString(R.string.app_name)
        println("Hello from repository. The app name is  $appName")
    }

    override suspend fun doNetworkCall() {

    }
}