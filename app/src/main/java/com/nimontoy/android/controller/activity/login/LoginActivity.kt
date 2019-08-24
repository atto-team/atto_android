package com.nimontoy.android.controller.activity.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.facebook.*
import com.nimontoy.android.R
import com.nimontoy.android.controller.activity.BaseActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.android.gms.common.api.ApiException
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.usermgmt.response.model.UserProfile
import com.nimontoy.android.Variable
import com.nimontoy.android.basic.StatusCode
import com.nimontoy.android.helper.RedirectHelper.goToMain
import com.nimontoy.android.helper.login.FacebookLoginHelper

import com.nimontoy.android.helper.login.GoogleLoginHelper
import com.nimontoy.android.helper.login.KakaoLoginHelper
import kotlinx.android.synthetic.main.activity_login.*

typealias FacebookCallback = CallbackManager

class LoginActivity : BaseActivity() {
    //facebook
    private val TAG = "LoginActivity"

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val facebookLoginHelper by lazy { FacebookLoginHelper(this, auth, Variable()) }
    private val googleLoginHelper by lazy { GoogleLoginHelper(this, Variable()) }
    private val kakaoLoginHelper by lazy { KakaoLoginHelper(this, Variable()) }

    private val facebookCallback: FacebookCallback by lazy { CallbackManager.Factory.create() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()

        checkAuth()
    }

    private fun initViews() {
        setGoogleLogin() // google
        setKakaoLogin() //kakao
        setFacebookLogin() //facebook
    }

    private fun checkAuth() = auth.currentUser?.let { goToMain(this) }

    private fun setFacebookLogin() {
        facebook_login.setReadPermissions("email", "public_profile")
        facebook_login.registerCallback(facebookCallback, facebookLoginHelper)

        facebook_login_button.setOnClickListener {
            facebook_login.performClick()
            facebookLoginHelper.userVariable.asObservable().subscribe {
                it?.let { doAfterCheckUser(it) }
            }.add()
        }
    }

    private fun setGoogleLogin() {
        google_login_button.setOnClickListener {
            with(googleLoginHelper) {
                login()
                userVariable.asObservable().subscribe {
                    it?.let { doAfterCheckUser(it) }
                }.add()
            }
        }
    }

    private fun setKakaoLogin() {
        Session.getCurrentSession().addCallback(kakaoLoginHelper)
        Session.getCurrentSession().checkAndImplicitOpen()

        kakao_login_button.setOnClickListener {
            kakao_login.performClick()
            kakaoLoginHelper.userVariable.asObservable().subscribe {
                it?.let { doAfterCheckUser(userProfile = it) }
            }.add()
        }
    }

    private fun doAfterCheckUser(firebaseUser: FirebaseUser? = null, userProfile: UserProfile? = null) {
        // TODO 유저 정보 등록 및 후 기능...
        firebaseUser?.let {  }
        userProfile?.let {  }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (handleKakaoResult(requestCode, resultCode, data)) return
        handleGoogleResult(requestCode, data)
        handleFacebookResult(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleKakaoResult(requestCode: Int, resultCode: Int, data: Intent?) =
        Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)

    private fun handleGoogleResult(requestCode: Int, data: Intent?) {
        if (requestCode == StatusCode.RC_SIGN_IN.code) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                googleLoginHelper.firebaseAuthWithGoogle(account!!, auth)
                println(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }

    private fun handleFacebookResult(requestCode: Int, resultCode: Int, data: Intent?) =
        facebookCallback.onActivityResult(requestCode, resultCode, data)

    override fun onDestroy() {
        super.onDestroy()
        removeKakaoCallback(kakaoLoginHelper)
    }

    private fun removeKakaoCallback(kakaoCallback: ISessionCallback) =
        Session.getCurrentSession().removeCallback(kakaoCallback)
}

