package com.example.nanda.kotlinhub.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.example.nanda.kotlinhub.R
import com.example.nanda.kotlinhub.helper.UserDBHelper

class MainActivity : AppCompatActivity() {

    lateinit var userDBHelper: UserDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val btnLogIn = findViewById<Button>(R.id.btn_login)
//        btnLogIn.setOnClickListener {
//            addUser()
//            val myIntent = Intent(this, MainPage::class.java)
//            startActivity(myIntent)
//        }

        val goToRegisterPage = findViewById<TextView>(R.id.registerNewAccount)
        goToRegisterPage.setOnClickListener {
            val myIntent = Intent(this, register::class.java)
            startActivity(myIntent)
        }

        val btnLogIn = findViewById<Button>(R.id.btn_login)
        btnLogIn.setOnClickListener {
            val myIntent = Intent(this, user_home::class.java)
            startActivity(myIntent)
        }

        userDBHelper = UserDBHelper(this)
    }

//    fun addUser() {
//        var username = this.et_username.text.toString()
//        var email = this.et_email.text.toString()
//        var password = this.et_password.text.toString()
//        println("THIS IS IN ADDUSER")
//        if(::userDBHelper.isInitialized) {
//            println("INITIALIZED")
//        }else {
//            println("NOT INITIALIZED")
//        }
//        var result = userDBHelper.loginUser(UserDataRecord(username, 0, email, password, true))
//    }
}