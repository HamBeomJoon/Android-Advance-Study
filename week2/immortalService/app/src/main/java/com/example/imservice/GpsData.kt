package com.example.imservice

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData

object GpsData {
    private val tag = "GPS_DATA"

    private lateinit var gpsIntent: Intent

    // 서비스의 실행 성태를 나타내는 LiveData이다.
    val isServiceRunning = MutableLiveData<Boolean>(false)

    // 위치 데이터를 위한 변수들
    var lat = 0.0
    var lon = 0.0
    var accuracy = 0.0
    var lastUpdatedTime: Long = 0

    fun loggingGpsData(){
        Log.i(tag, "==================================")
        Log.i(tag, "lat: $lat")
        Log.i(tag, "lon: $lon")
        Log.i(tag, "accuracy: $accuracy")
        Log.i(tag, "lastUpdatedTime: $lastUpdatedTime")
        Log.i(tag, "==================================")
    }

    // GpsService를 시작하는 메서드
    fun startGpsService(context: Context) {
        // 서비스가 실행 중이지 않은 경우에만 서비스를 시작한다.
        // 중복 동작과 ANR 방지 목적이다.
        if(!isServiceRun()) {
            gpsIntent = Intent(context, GpsService::class.java)
            ContextCompat.startForegroundService(context, gpsIntent)
            Toast.makeText(context, "Service Start", Toast.LENGTH_SHORT).show()
        }
    }

    // GpsService를 중지하는 메서드
    fun stopGpsService(context: Context) {
        context.stopService(gpsIntent)
    }

    // 서비스의 실행 상태를 반환하는 메서드
    private fun isServiceRun(): Boolean {
        return isServiceRunning.value == true
    }
}