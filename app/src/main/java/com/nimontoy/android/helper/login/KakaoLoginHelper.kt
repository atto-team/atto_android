package com.nimontoy.android.helper.login

import android.util.Log
import com.kakao.auth.ErrorCode
import com.kakao.auth.ISessionCallback
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeResponseCallback
import com.kakao.usermgmt.response.model.UserProfile
import com.kakao.util.exception.KakaoException
import com.nimontoy.android.Variable
import com.nimontoy.android.controller.activity.login.LoginActivity

/**
 * Created by leesujung on 2019. 8. 14..
 */
open class KakaoLoginHelper(val context: LoginActivity, var userVariable: Variable<UserProfile>) : ISessionCallback {

    val TAG = "KakaoLoginHelper"

    override fun onSessionOpened() {
        UserManagement.getInstance().requestMe(object : MeResponseCallback() {
            override fun onFailure(errorResult: ErrorResult) {
                var message = "failed to get user info. msg=$errorResult"
                var result: ErrorCode = ErrorCode.valueOf(errorResult.errorCode)
                println(result)
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    //에러로 인한 로그인 실패
                    //finish()
                } else {
                    //redirectMainActivity();
                }
            }

            override fun onSessionClosed(errorResult: ErrorResult) { }

            override fun onNotSignedUp() {

            }

            override fun onSuccess(userProfile: UserProfile) {
                //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.
                Log.e("UserProfile", userProfile.toString())
                Log.e("UserProfile", userProfile.getId().toString())
                var number = userProfile.getId()
                userVariable.set(userProfile)
            }
        })

    }

    override fun onSessionOpenFailed(exception: KakaoException) {
        // 세션 연결 실패
    }


}