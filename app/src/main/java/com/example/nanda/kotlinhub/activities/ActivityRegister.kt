package com.example.nanda.kotlinhub.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.nanda.kotlinhub.R
import com.example.nanda.kotlinhub.helper.UserDBHelper
import com.example.nanda.kotlinhub.model.UserDataRecord
import kotlinx.android.synthetic.main.activity_register.*

class register : AppCompatActivity() {

    lateinit var userDBHelper: UserDBHelper
    var count: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val btnRegister = findViewById<Button>(R.id.btn_register)
        btnRegister.setOnClickListener {
            val result = registerUser()
            if(result == true) {
                finish()
            }
        }

        val textView = findViewById<TextView>(R.id.tv_display_users)
        val imageViewLogo = findViewById<ImageView>(R.id.imageView_logo)
        imageViewLogo.setOnClickListener {

            if(count == 2) {
                val allUsers = userDBHelper.getAllUsers()
                var str: String? = null
                str = "Fetched " + allUsers.size + " users:  \n"
                textView.setText("All Users: " + str)

                var usersList: String? = null
                allUsers.forEach{
                    usersList = it.email.toString() + " - " + it.username.toString() +" - " + it.progress.toString() + "\n"
                    textView.append(usersList)
                    usersList = ""
                }
            } else if(count == 5) {
                textView.setText("")
            }

            if(count < 6) {
                count++
            } else {
                count = 0
            }
        }

//        val btnGetAllUsers = findViewById<Button>(R.id.btn_get_users)
//        btnGetAllUsers.setOnClickListener {
//            val allUsers = userDBHelper.getAllUsers()
//
//            val textView = findViewById<TextView>(R.id.tv_display_users)
//            var str: String? = null
//            str = "Fetched " + allUsers.size + " users:  \n"
//            textView.setText("All Users: " + str)
//
//            var usersList: String? = null
//            allUsers.forEach{
//                usersList = it.email.toString() + " - " + it.username.toString() +" - " + it.progress.toString() + "\n"
//                textView.append(usersList)
//                usersList = ""
//            }
//        }

        userDBHelper = UserDBHelper(this)
    }

    fun registerUser(): Boolean {
        var username = this.et_username.text.toString()
        var email = this.et_email.text.toString()
        var password = this.et_password.text.toString()
        var confirmPassword = this.et_confirm_password.text.toString()

        if(password == confirmPassword) {
            val result = userDBHelper.registerUser(UserDataRecord(username,0,email, password, false, 0))
            if(result == true) {
                Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_LONG).show()
                this.et_username.setText("")
                this.et_email.setText("")
                this.et_password.setText("")
                this.et_confirm_password.setText("")
                return true
            }else {
                Toast.makeText(this, "Error! This email already exists. Please choose another one.", Toast.LENGTH_LONG).show()
                return false
            }
        } else {
            Toast.makeText(this, "Password Mismatch Error !", Toast.LENGTH_LONG).show()
            return false
        }
    }
}
