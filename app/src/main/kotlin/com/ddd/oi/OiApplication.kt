package com.ddd.oi

import android.app.Application
import com.ddd.oi.data.BuildConfig
import com.naver.maps.map.NaverMapSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OiApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NcpKeyClient(BuildConfig.NAIVER_CLIENT_ID)
    }
}