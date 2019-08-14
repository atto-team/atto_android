package com.nimontoy.android.controller.activity.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.nimontoy.android.R
import com.nimontoy.android.controller.activity.BaseActivity
import com.nimontoy.android.controller.activity.DataListActivity

class SignupAcitivty : DataListActivity() {

    private val btnBack by lazy { findViewById<ImageButton>(R.id.btn_back) }

    private val editName by lazy { findViewById<EditText>(R.id.edit_name) }
    private val editMail by lazy { findViewById<EditText>(R.id.edit_mail) }
    private val editPassword by lazy { findViewById<EditText>(R.id.edit_password) }
    private val editConfirmPassword by lazy { findViewById<EditText>(R.id.edit_confirm_password) }

    private val btnSignup by lazy { findViewById<EditText>(R.id.btn_signup) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_acitivty)
    }

    override fun bindViews () {
        super.bindViews()
        btnBack.setOnClickListener{ finish () }
        
        btnSignup.setOnClickListener {
            val name = editName.text.toString()
            val mail = editMail.text.toString()
            val password = editPassword.text.toString()
            val confirm = editConfirmPassword.text.toString()
            if (password != confirm) {
                Toast.makeText(this, "Confrim Your Password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
