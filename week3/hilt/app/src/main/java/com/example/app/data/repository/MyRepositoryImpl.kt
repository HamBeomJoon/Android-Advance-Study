package com.example.app.data.repository

import com.example.app.data.remote.MyApi
import com.example.app.domain.repository.MyRepository

class MyRepositoryImpl(private val api: MyApi) : MyRepository {

    override suspend fun doNetworkCall() {

    }

}