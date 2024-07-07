package com.example.myapplication

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor

@Suppress("DEPRECATION")
class PrettyJsonLogger : HttpLoggingInterceptor.Logger {
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val jsonParser = JsonParser()

    override fun log(message: String) {
        val trimMessage = message.trim()

        if ((trimMessage.startsWith("{") && trimMessage.endsWith("}"))
            || (trimMessage.startsWith("[") && trimMessage.endsWith("]"))
        ) {
            try {
                val prettyJson = gson.toJson(jsonParser.parse(message))
                Platform.get().log(prettyJson, Platform.INFO, null)
            } catch (e: Exception) {
                Platform.get().log(message, Platform.WARN, e)
            }
        } else {
            Platform.get().log(message, Platform.INFO, null)
        }
    }
}