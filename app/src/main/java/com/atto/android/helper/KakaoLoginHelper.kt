package com.atto.android.helper

import android.content.Context
import androidx.annotation.Nullable
import com.kakao.auth.*


abstract class KakaoLoginHelper(): KakaoAdapter() {

    override fun getSessionConfig() : ISessionConfig {
        return object : ISessionConfig {
            override fun getAuthTypes(): Array<AuthType> = arrayOf(AuthType.KAKAO_TALK_ONLY)
            override fun isUsingWebviewTimer(): Boolean = false
            override fun isSecureMode(): Boolean = false
            override fun getApprovalType(): ApprovalType = ApprovalType.INDIVIDUAL
            override fun isSaveFormData(): Boolean = false
        }
    }

    override fun getApplicationConfig() : IApplicationConfig {
        return object : IApplicationConfig {
            override fun getApplicationContext() : Context = NewClassApplication.getLoginHelperContext();
        };
    }

    private fun  getLoginHelperContext() : Context = mContext;

    override public void onCreate() {
        super.onCreate(); mContext = this; KakaoSDK.init(new KakaoSDKAdapter());
    }
}


