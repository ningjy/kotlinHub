package com.example.nanda.kotlinhub.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.example.nanda.kotlinhub.R
import com.example.nanda.kotlinhub.helper.UserDBHelper
import kotlinx.android.synthetic.main.activity_home_page.*

class ActivityFlowControl : AppCompatActivity() {

    lateinit var userDBHelper: UserDBHelper
    val REQUEST_CODE : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow_control)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        userDBHelper = UserDBHelper(this)
        var progress = userDBHelper.getProgress()

        val btnIf = findViewById<Button>(R.id.btn_topic7)
        val btnWhen = findViewById<Button>(R.id.btn_topic8)
        val btnWhile = findViewById<Button>(R.id.btn_topic9)
        val btnFor = findViewById<Button>(R.id.btn_topic10)
        val btnBreak = findViewById<Button>(R.id.btn_topic11)
        val btnContinue = findViewById<Button>(R.id.btn_topic12)

        if(progress>6){btnIf.setBackgroundResource(R.drawable.checked_icon)}
        if(progress>7){btnWhen.setBackgroundResource(R.drawable.checked_icon)}
        if(progress>8){btnWhile.setBackgroundResource(R.drawable.checked_icon)}
        if(progress>9){btnFor.setBackgroundResource(R.drawable.checked_icon)}
        if(progress>10){btnBreak.setBackgroundResource(R.drawable.checked_icon)}
        if(progress>11){btnContinue.setBackgroundResource(R.drawable.checked_icon)}

        btnIf.setOnClickListener {

            progress = userDBHelper.getProgress()

            if(progress<6) {
                showPopupModal()
            }else {
                val myIntent = Intent(this, ActivityTopic()::class.java)
                myIntent.putExtra("topicFile", "topic_7")
                myIntent.putExtra("topicNum", 7)
                startActivityForResult(myIntent, REQUEST_CODE)
            }
        }

        btnWhen.setOnClickListener {

            progress = userDBHelper.getProgress()

            if(progress<7) {
                showPopupModal()
            }else {
                val myIntent = Intent(this, ActivityTopic()::class.java)
                myIntent.putExtra("topicFile", "topic_8")
                myIntent.putExtra("topicNum", 8)
                startActivityForResult(myIntent, REQUEST_CODE)
            }
        }

        btnWhile.setOnClickListener {

            progress = userDBHelper.getProgress()

            if(progress<8) {
                showPopupModal()
            }else {
                val myIntent = Intent(this, ActivityTopic()::class.java)
                myIntent.putExtra("topicFile", "topic_9")
                myIntent.putExtra("topicNum", 9)
                startActivityForResult(myIntent, REQUEST_CODE)
            }
        }

        btnFor.setOnClickListener {

            progress = userDBHelper.getProgress()

            if(progress<9) {
                showPopupModal()
            }else {
                val myIntent = Intent(this, ActivityTopic()::class.java)
                myIntent.putExtra("topicFile", "topic_10")
                myIntent.putExtra("topicNum", 10)
                startActivityForResult(myIntent, REQUEST_CODE)
            }
        }

        btnBreak.setOnClickListener {

            progress = userDBHelper.getProgress()

            if(progress<10) {
                showPopupModal()
            }else {
                val myIntent = Intent(this, ActivityTopic()::class.java)
                myIntent.putExtra("topicFile", "topic_11")
                myIntent.putExtra("topicNum", 11)
                startActivityForResult(myIntent, REQUEST_CODE)
            }
        }

        btnContinue.setOnClickListener {

            progress = userDBHelper.getProgress()

            if(progress<11) {
                showPopupModal()
            }else {
                val myIntent = Intent(this, ActivityTopic()::class.java)
                myIntent.putExtra("topicFile", "topic_12")
                myIntent.putExtra("topicNum", 12)
                startActivityForResult(myIntent, REQUEST_CODE)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        val myIntent = Intent(this, ActivityHomePage::class.java)
        startActivity(myIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        this.checkCompleted()
    }

    fun checkCompleted() {
        userDBHelper = UserDBHelper(this)
        var progress = userDBHelper.getProgress()

        val btnIf = findViewById<Button>(R.id.btn_topic7)
        val btnWhen = findViewById<Button>(R.id.btn_topic8)
        val btnWhile = findViewById<Button>(R.id.btn_topic9)
        val btnFor = findViewById<Button>(R.id.btn_topic10)
        val btnBreak = findViewById<Button>(R.id.btn_topic11)
        val btnContinue = findViewById<Button>(R.id.btn_topic12)

        if(progress>6){btnIf.setBackgroundResource(R.drawable.checked_icon)}
        if(progress>7){btnWhen.setBackgroundResource(R.drawable.checked_icon)}
        if(progress>8){btnWhile.setBackgroundResource(R.drawable.checked_icon)}
        if(progress>9){btnFor.setBackgroundResource(R.drawable.checked_icon)}
        if(progress>10){btnBreak.setBackgroundResource(R.drawable.checked_icon)}
        if(progress>11){btnContinue.setBackgroundResource(R.drawable.checked_icon)}
    }

    fun showPopupModal() {
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popup_layout_locked, null)
        val popupWindow = PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10.0F
        }

        // If API level 23 or higher then execute the code
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // Create a new slide animation for popup window enter transition
            val slideIn = Slide()
            slideIn.slideEdge = Gravity.TOP
            popupWindow.enterTransition = slideIn

            // Slide animation for popup window exit transition
            val slideOut = Slide()
            slideOut.slideEdge = Gravity.RIGHT
            popupWindow.exitTransition = slideOut

        }

        popupWindow.width = 750
        popupWindow.height = 400

        val tv = view.findViewById<TextView>(R.id.tv_poopup_text)
        tv.setText("Complete the previous topics to unlock this one!")
        val btnPopup = view.findViewById<Button>(R.id.btn_ok)

        // Set a click listener for popup's button widget
        btnPopup.setOnClickListener{
            // Dismiss the popup window
            popupWindow.dismiss()
        }

        // Set a dismiss listener for popup window
        /*
        popupWindow.setOnDismissListener {
            Toast.makeText(applicationContext,"Good Job",Toast.LENGTH_SHORT).show()
        }
        */

        TransitionManager.beginDelayedTransition(home_layout)
        popupWindow.showAtLocation(
                home_layout,
                Gravity.CENTER,
                0,0
        )
    }
}
