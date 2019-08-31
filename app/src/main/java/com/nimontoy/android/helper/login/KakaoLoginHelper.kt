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
import com.nimontoy.android.view.controller.activity.login.LoginActivity

/**
 * Created by leesujung on 2019. 8. 14..
 */
typealias KakaoUser = UserProfile
open class KakaoLoginHelper(val context: LoginActivity, var userVariable: Variable<KakaoUser>) : ISessionCallback {

    val TAG = "KakaoLoginHelper"

    override fun onSessionOpened() {
        UserManagement.getInstance().requestMe(object : MeResponseCallback() {
            override fun onFailure(errorResult: ErrorResult) {
                var message = "failed to get user info. msg=$errorResult"
                Log.e("ErrorResult", message)
                var result: ErrorCode = ErrorCode.valueOf(errorResult.errorCode)
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    //에러로 인한 로그인 실패
                    //finish()
                } else {
                    //TODO 서버 체크해서 이미 회원가입 되어 있다면 300 code로 메인화면, 회원가입 안되어 있다면 200으로 goToSignUp
                }
            }

            override fun onSessionClosed(errorResult: ErrorResult) {
                Log.e("ErrorResult", errorResult.errorMessage)
            }

            override fun onNotSignedUp() {

            }

            override fun onSuccess(KakaoUser: KakaoUser) {
                //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.
                Log.e("KakaoUser", KakaoUser.toString())
                Log.e("KakaoUser", KakaoUser.id.toString())
                var number = KakaoUser.getId()
                userVariable.set(KakaoUser)
            }
        })

    }

    override fun onSessionOpenFailed(exception: KakaoException) {
        // 세션 연결 실패
        Log.e("onSessionOpenFailed", exception.message)
    }


}