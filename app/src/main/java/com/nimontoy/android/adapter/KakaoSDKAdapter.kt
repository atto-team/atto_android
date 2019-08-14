package com.nimontoy.android.adapter

import android.app.Activity
import android.content.Context
import com.kakao.auth.*
//import com.kakao.auth.KakaoSDK.getCurrentActivity
import com.nimontoy.android.adapter.GlobalApplication.getCurrentActivity
import com.nimontoy.android.adapter.GlobalApplication.getGlobalApplicationContext


class KakaoSDKAdapter : KakaoAdapter() {
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
        return object : IApplicationConfig {
            /*
            override fun getTopActivity(): Activity? {
                return getCurrentActivity()
            }
            */

            override fun getApplicationContext(): Context? {
                return getGlobalApplicationContext()
            }

        }
    }
}