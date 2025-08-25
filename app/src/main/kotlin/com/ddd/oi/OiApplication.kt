package com.ddd.oi

import android.app.Application
import android.util.Log
import com.ddd.oi.data.BuildConfig
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.naver.maps.map.NaverMapSdk
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OiApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        var keyHash = Utility.getKeyHash(this)
        Log.d("KeyHash", keyHash)
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NcpKeyClient(BuildConfig.NAVER_MAP_CLIENT_ID)
        NaverIdLoginSDK.initialize(this, BuildConfig.NAVER_OAUTH_CLIENT_ID, BuildConfig.NAVER_OAUTH_CLIENT_SECRET, "오늘의이동-오이")
    }
}