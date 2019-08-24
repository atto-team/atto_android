package com.nimontoy.android.controller.activity.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import com.facebook.*
import com.nimontoy.android.R
import com.nimontoy.android.controller.activity.BaseActivity
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.android.gms.common.api.ApiException
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException
import com.kakao.util.helper.log.Logger
import com.nimontoy.android.controller.activity.main.MainActivity
import com.nimontoy.android.helper.RedirectHelper.goToActivity
import com.nimontoy.android.helper.login.FacebookLoginHelper

import com.nimontoy.android.helper.login.GoogleLoginHelper
import kotlinx.android.synthetic.main.activity_login.*

open class LoginActivity : BaseActivity() {
    //facebook
    private val TAG = "LoginActivity"
    private lateinit var auth : FirebaseAuth
    private lateinit var facebookLoginHelper: FacebookLoginHelper
    private lateinit var callbackManager: CallbackManager
    private val googleLoginHelper = GoogleLoginHelper(this)
    private var callbackKakao : KakaoSessionCallback? = null  //kakao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            window.statusBarColor = resources.getColor(R.color.colorDark);
        }

        googleLogin()   // google login configure / Sing in
        kakaoLogin()   //kakao
        facebookLogin()    //facebook
        auth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()
        facebookLoginHelper = FacebookLoginHelper(this, auth)

        var facebook_login = findViewById<LoginButton>(R.id.facebook_login)
        facebook_login.setReadPermissions("email", "public_profile")
        facebook_login.registerCallback(callbackManager, facebookLoginHelper)


        //로그인 했을 경우 바로 메인으로
        if(auth.currentUser != null) {
            intent = Intent(this, MainActivity::class.java)
            goToActivity(this,intent)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) { return }

        callbackManager.onActivityResult(requestCode, resultCode, data)

        // [google Sign in Event] Start
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                googleLoginHelper.firebaseAuthWithGoogle(account!!, auth)
                println (account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
        // [google Sign in Event] End
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callbackKakao)
    }

    fun updateUI(user: FirebaseUser?) {
        if (user != null) {

            //status.text = getString(R.string.facebook_status_fmt, user.displayName)
            //detail.text = getString(R.string.firebase_status_fmt, user.uid)

            //facebook_login_custom.visibility = View.GONE
            //facebook_login_custom_signout.visibility = View.VISIBLE

        } else {

            //status.setText(R.string.signed_out)
            //detail.text = null

            //facebook_login_custom.visibility = View.VISIBLE
            //facebook_login_custom_signout.visibility = View.GONE

        }
    }


    companion object {
        const val RC_SIGN_IN = 9001
    }

    private fun facebookLogin () {
        facebook_login_custom.setOnClickListener( object: View.OnClickListener {
            override fun onClick(view: View){
                facebook_login.performClick()
            }
        })
    }

    private fun googleLogin () {
        google_login.setOnClickListener {
            googleLoginHelper.login()
        }
    }

    private fun kakaoLogin () {
        callbackKakao = KakaoSessionCallback(this)
        Session.getCurrentSession().addCallback(callbackKakao)
        Session.getCurrentSession().checkAndImplicitOpen()

        kakao_login_custom.setOnClickListener( object: View.OnClickListener {
            override fun onClick(view: View){
                com_kakao_login.performClick()
            }
        })
    }



    //KaKao Login
//    private class KakaoSessionCallback() : ISessionCallback {
//        override fun onSessionOpened(){
//            UserManagement.getInstance().requestMe( object: MeResponseCallback() {
//                override fun onFailure(errorResult: ErrorResult) {
//                    var message = "failed to get user info. msg=" + errorResult
//                    var result: ErrorCode = ErrorCode.valueOf(errorResult.getErrorCode())
//                    println (result)
//                    if (result == ErrorCode.CLIENT_ERROR_CODE) {
//                        //에러로 인한 로그인 실패
//                        //finish()
//                    } else {
//                        //redirectMainActivity();
//                    }
//                }
//
//                override fun onSessionClosed(errorResult: ErrorResult) {
//                }
//
//                override fun onNotSignedUp() {
//
//                }
//                override fun onSuccess(userProfile: UserProfile) {
//                    //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
//                    //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.
//                    Log.e("UserProfile", userProfile.toString())
//                    Log.e("UserProfile", userProfile.getId().toString())
//                    var number = userProfile.getId()
//                }
//            });
//
//        }
//        override fun onSessionOpenFailed(exception: KakaoException) {
//            // 세션 연결 실패
//        }
//    }

    private class KakaoSessionCallback(val context : LoginActivity) : ISessionCallback {
        override fun onSessionOpened() {
            println ("FUCK")
//            redirectSignupActivity()
        }

        override fun onSessionOpenFailed(exception : KakaoException) {
            if (exception != null) {
                Logger.e(exception)
            }
        }

        fun redirectSignupActivity () {
            val intent = Intent (context, LoginActivity::class.java)
            context.startActivity(intent)
            context.finish()
        }

    }

}

