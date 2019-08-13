package com.nimontoy.android.controller.activity.login

import android.content.Intent
import android.content.pm.PackageInstaller
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.facebook.*
import com.nimontoy.android.R
import com.nimontoy.android.controller.activity.BaseActivity
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nimontoy.android.controller.activity.main.MainActivity
import com.nimontoy.android.helper.RedirectHelper.goToActivity

import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException
import com.kakao.util.helper.log.Logger

class LoginActivity : BaseActivity() {
    //facebook
    private val TAG = "LoginActivity"
    private lateinit var callbackManager: CallbackManager
    private lateinit var auth : FirebaseAuth
    //kakao
    //private var callback : SessionCallback? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //status bar color
        if (Build.VERSION.SDK_INT >= 21) { // 21 버전 이상일 때
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorDark));
        }


        //kakao
        //callback = SessionCallback();
        //Session.getCurrentSession().addCallback(callback)
        //Session.getCurrentSession().checkAndImplicitOpen()
        //facebook 로그인
        var facebook_login = findViewById<LoginButton>(R.id.facebook_login);
        callbackManager = CallbackManager.Factory.create();
        auth = FirebaseAuth.getInstance()


        //로그인 했을 경우 바로 메인으로
        if(auth.currentUser != null)
        {
            // intent = Intent(this, MainActivity::class.java)
            //goToActivity(this,intent)
        }

        facebook_login.setReadPermissions("email", "public_profile");
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    // App code
                    Log.d(TAG, "onSucces LoginResult=$loginResult")
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    // App code
                    Log.d(TAG, "onCancel")
                }

                override fun onError(exception: FacebookException) {
                    // App code
                    Log.d(TAG, "onError")
                }
            })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) { return }
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            /*
            status.text = getString(R.string.facebook_status_fmt, user.displayName)
            detail.text = getString(R.string.firebase_status_fmt, user.uid)

            buttonFacebookLogin.visibility = View.GONE
            buttonFacebookSignout.visibility = View.VISIBLE
            */
        } else {
            /*
            status.setText(R.string.signed_out)
            detail.text = null

            buttonFacebookLogin.visibility = View.VISIBLE
            buttonFacebookSignout.visibility = View.GONE
            */
        }
    }

    //kakao
/*
    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }

    private inner class SessionCallback : ISessionCallback {

        override fun onSessionOpened() {
            //val intent = Intent(this, MainActivity::class.java)
            //startActivity(intent)
        }

        override fun onSessionOpenFailed(exception: KakaoException?) {
            if (exception != null) {
                Logger.e(exception)
            }
        }
    }
*/
}