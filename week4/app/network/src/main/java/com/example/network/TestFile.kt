package com.example.network

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkService @Inject constructor() {
    @Composable
    fun TestFile() {
        Text(text = "Hello TestFile!")
    }
}