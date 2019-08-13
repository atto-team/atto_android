package com.nimontoy.android.adapter

import android.app.Activity
import android.app.Application
import android.content.Context
import com.kakao.auth.*
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger

class GlobalApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
        KakaoSDK.init(KakaoSDKAdapter())

        // start koin
        //startKoin(this, appModule, logger = EmptyLogger())
    }

    companion object {
        var application: GlobalApplication? = null
    }

    internal inner class KakaoSDKAdapter : KakaoAdapter() {
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

                override fun getApprovalType(): ApprovalType {
                    return ApprovalType.INDIVIDUAL
                }

                override fun isSaveFormData(): Boolean {
                    return true
                }
            }
        }

        override fun getApplicationConfig(): IApplicationConfig {
            return IApplicationConfig { GlobalApplication.Companion.application }
        }
    }
}

