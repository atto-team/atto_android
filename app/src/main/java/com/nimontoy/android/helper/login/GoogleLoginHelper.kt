package com.nimontoy.android.helper.login

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.nimontoy.android.R
import com.nimontoy.android.Variable
import com.nimontoy.android.basic.Code
import com.nimontoy.android.view.controller.activity.login.LoginActivity

class GoogleLoginHelper(val context: LoginActivity, val userVariable: Variable<FirebaseUser>) {
    private val TAG = "GoogleLoginHelper"

    private val gso by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    private val googleSignInClient: GoogleSignInClient by lazy { GoogleSignIn.getClient(context, gso) }

    fun login() {
        context.startActivityForResult(googleSignInClient.signInIntent, Code.GOOGLE_SIGN_IN.code)
    }

    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount, auth: FirebaseAuth) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(context) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    user?.let {
                        // Name, email address, and profile photo Url
                        val name = user.displayName
                        val email = user.email
                        val photoUrl = user.photoUrl

                        // Check if user's email is verified
                        val emailVerified = user.isEmailVerified

                        // The user's ID, unique to the Firebase project. Do NOT use this value to
                        // authenticate with your backend server, if you have one. Use
                        // FirebaseUser.getToken() instead.
                        val uid = user.uid
                        println(name)
                        println(email)
                        println(photoUrl)
                        println(emailVerified)
                        println(uid)

                        userVariable.set(user)
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    userVariable.set()
                }
            }
    }
}