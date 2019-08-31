package com.nimontoy.android

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import com.kakao.auth.KakaoSDK
import com.nimontoy.android.helper.session.KakaoSDKAdapter

/**
 * Created by leekijung on 2019. 7. 9..
 */

class AttoApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        Fabric.with(this, Crashlytics())
        KakaoSDK.init(KakaoSDKAdapter())
    }

    companion object {
        val TAG = "Atto App"
        lateinit var appContext: Context
            private set
    }

}
