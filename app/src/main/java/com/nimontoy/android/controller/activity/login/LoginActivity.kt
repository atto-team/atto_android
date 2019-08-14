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
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.android.gms.common.api.ApiException
import com.kakao.auth.ErrorCode
import com.kakao.auth.ISessionCallback
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeResponseCallback
import com.kakao.usermgmt.response.model.UserProfile
import com.kakao.util.exception.KakaoException

import com.nimontoy.android.helper.login.GoogleLoginHelper
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    //facebook
    private val TAG = "LoginActivity"
    private lateinit var callbackManager: CallbackManager
    private lateinit var auth : FirebaseAuth

    private val googleLoginHelper = GoogleLoginHelper(this)
    private var callback : SessionCallback? = null  //kakao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //status bar color
        if (Build.VERSION.SDK_INT >= 21) { // 21 버전 이상일 때
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorDark));
        }


        //kakao
        callback = SessionCallback();
        //Session.getCurrentSession().addCallback(callback)
        //Session.getCurrentSession().checkAndImplicitOpen()
        //facebook 로그인
        var facebook_login = findViewById<LoginButton>(R.id.facebook_login)
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


        googleLogin() // google login configure / Sing in
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) { return }
        super.onActivityResult(requestCode, resultCode, data);
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

    fun updateUI(user: FirebaseUser?) {
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


    companion object {
        const val RC_SIGN_IN = 9001
    }

    private fun googleLogin () {
        google_login.setOnClickListener {
            googleLoginHelper.login()
        }
    }
}

//KaKao Login
private class SessionCallback() : ISessionCallback {
    override fun onSessionOpened(){
        UserManagement.getInstance().requestMe( object: MeResponseCallback() {
            override fun onFailure(errorResult: ErrorResult) {
                var message = "failed to get user info. msg=" + errorResult
                var result: ErrorCode = ErrorCode.valueOf(errorResult.getErrorCode())
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    //에러로 인한 로그인 실패
                    //finish()
                } else {
                    //redirectMainActivity();
                }
            }

            override fun onSessionClosed(errorResult: ErrorResult) {
            }

            override fun onNotSignedUp() {

            }
            override fun onSuccess(userProfile: UserProfile) {
                //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.
                Log.e("UserProfile", userProfile.toString())
                Log.e("UserProfile", userProfile.getId().toString())
                var number = userProfile.getId()
            }
        });

    }
    override fun onSessionOpenFailed(exception: KakaoException) {
        // 세션 연결 실패
    }
}