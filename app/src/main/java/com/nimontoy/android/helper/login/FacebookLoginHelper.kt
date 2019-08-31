package com.nimontoy.android.helper.login

import android.util.Log
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nimontoy.android.Variable
import com.nimontoy.android.controller.activity.login.LoginActivity

/**
 * Created by leesujung on 2019. 8. 14..
 */
class FacebookLoginHelper (val context : LoginActivity,
                           var auth : FirebaseAuth,
                           val userVariable: Variable<FirebaseUser>) : FacebookCallback<LoginResult>{
    val TAG = "FacebookLoginHelper"

    override fun onSuccess(loginResult: LoginResult) {
        // App code
        Log.d(TAG, "onSucces LoginResult=$loginResult")
        handleFacebookAccessToken(loginResult.accessToken, auth)
    }

    override fun onCancel() {
        // App code
        Log.d(TAG, "onCancel")
    }

    override fun onError(exception: FacebookException) {
        // App code
        Log.d(TAG, "onError")
    }


    private fun handleFacebookAccessToken(token: AccessToken, auth: FirebaseAuth) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(context) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    auth.currentUser?.let { userVariable.set(it) }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    userVariable.set()
                }

            }
    }
}