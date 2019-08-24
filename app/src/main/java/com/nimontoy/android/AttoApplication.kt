package com.nimontoy.android

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import com.kakao.auth.KakaoSDK
import com.kakao.auth.IApplicationConfig
import com.kakao.auth.ApprovalType
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionConfig
import com.kakao.auth.KakaoAdapter

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

    private class KakaoSDKAdapter : KakaoAdapter() {
        /**
         * Session Config에 대해서는 default값들이 존재한다.
         * 필요한 상황에서만 override해서 사용하면 됨.
         * @return Session의 설정값.
         */
        override fun getSessionConfig(): ISessionConfig {
            return object : ISessionConfig {
                override fun getAuthTypes(): Array<AuthType> {
                    return arrayOf(AuthType.KAKAO_TALK)
                }

                override fun isUsingWebviewTimer(): Boolean {
                    return false
                }

                override fun isSecureMode(): Boolean {
                    return false
                }

                override fun getApprovalType(): ApprovalType? {
                    return ApprovalType.INDIVIDUAL
                }

                override fun isSaveFormData(): Boolean {
                    return true
                }
            }
        }

        override fun getApplicationConfig(): IApplicationConfig {
            return IApplicationConfig { appContext }
        }
    }


}
