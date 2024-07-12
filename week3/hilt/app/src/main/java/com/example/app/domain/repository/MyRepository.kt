package com.example.app.domain.repository

interface MyRepository {
    suspend fun doNetworkCall()
}