package com.atto.android.controller.activity.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.atto.android.R
import com.atto.android.controller.activity.BaseActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    //facebook
    private val TAG = "LoginActivity"
    private var callbackManager: CallbackManager? = null
    protected var facebook_login: LoginButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        facebook_login = findViewById(R.id.facebook_login);

        facebook_login?.setReadPermissions("email");
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    // App code
                    Log.d(TAG, "onSucces LoginResult=$loginResult")
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
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager?.onActivityResult(requestCode, resultCode, data);
    }

}