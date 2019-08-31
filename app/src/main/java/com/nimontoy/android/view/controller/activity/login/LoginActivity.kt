package com.nimontoy.android.view.controller.activity.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.facebook.*
import com.nimontoy.android.R
import com.nimontoy.android.view.controller.activity.BaseActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.android.gms.common.api.ApiException
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.nimontoy.android.Variable
import com.nimontoy.android.basic.Code
import com.nimontoy.android.helper.RedirectHelper.goToMain
import com.nimontoy.android.helper.login.FacebookLoginHelper

import com.nimontoy.android.helper.login.GoogleLoginHelper
import com.nimontoy.android.helper.login.KakaoLoginHelper
import com.nimontoy.android.helper.login.KakaoUser
import com.nimontoy.android.helper.session.SessionHelper
import kotlinx.android.synthetic.main.activity_login.*
import android.content.pm.PackageManager
import android.content.Context
import android.util.Base64
import com.kakao.util.helper.Utility.getPackageInfo
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

typealias FacebookCallback = CallbackManager

class LoginActivity : BaseActivity() {
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

        Log.e("hash key", getKeyHash(this))
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
                it?.let { doAfterCheckUser(kakaoUser = it) }
            }.add()
        }
    }

    private fun doAfterCheckUser(firebaseUser: FirebaseUser? = null, kakaoUser: KakaoUser? = null) {
        // TODO 유저 정보 등록 및 후 기능...
        if(firebaseUser != null || kakaoUser != null) {
            firebaseUser?.let { user ->
                with(user) {
                    SessionHelper.userEmail = email.toString()
                    SessionHelper.userName = displayName.toString()
                    SessionHelper.userPhone = phoneNumber.toString()
                    getIdToken(true).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            task.result?.token?.let {
                                SessionHelper.storeAccessToken(it)
                                checkAuth()
                            }
                        } else {
                            // Handle error -> task.getException();
                        }
                    }
                }
            }
            kakaoUser?.let { user ->
                with(user) {
                    SessionHelper.userId = id.toInt()
                    SessionHelper.userEmail = email.toString()
                    SessionHelper.userName = nickname.toString()
                }
            }
            SessionHelper.setLogined()
        }
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
        if (requestCode == Code.GOOGLE_SIGN_IN.code) {
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

    fun getKeyHash(context: Context): String? {
        val packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES) ?: return null

        for (signature in packageInfo!!.signatures) {
            try {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP)
            } catch (e: NoSuchAlgorithmException) {
                Log.w(TAG, "Unable to get MessageDigest. signature=$signature", e)
            }

        }
        return null
    }
}

