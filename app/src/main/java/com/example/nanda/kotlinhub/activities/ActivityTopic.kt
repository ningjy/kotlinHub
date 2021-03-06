package com.example.nanda.kotlinhub.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import com.example.nanda.kotlinhub.R
import com.example.nanda.kotlinhub.R.id.topic_layout
import com.example.nanda.kotlinhub.helper.JSONHelper
import com.example.nanda.kotlinhub.helper.Section
import com.example.nanda.kotlinhub.helper.UserDBHelper
import kotlinx.android.synthetic.main.activity_topic.*

class ActivityTopic : AppCompatActivity() {
    lateinit var userDBHelper: UserDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        var topicFile = intent.getStringExtra("topicFile")
        var topicNum = intent.getIntExtra("topicNum", 1)


        var jsonHelper = JSONHelper(topicFile, this)
        var topic: ArrayList<Section> = jsonHelper.parse()
        var section1 = topic[0]

        var sectionProgress = findViewById<ProgressBar>(R.id.pb_section)
        sectionProgress.max = topic.size
        sectionProgress.progress = 1

        displaySection(section1)

        userDBHelper = UserDBHelper(this)
        var i = 1
        val btnNext = findViewById<Button>(R.id.btn_next)

        var hasQuiz = true
        val topicWithoutQuiz = intArrayOf(4, 6, 12, 22)
        if(topicNum in topicWithoutQuiz){
            hasQuiz = false
        }

        val username = userDBHelper.getUsername()
        val progress = userDBHelper.getProgress()

        //Toast.makeText(this, "Topic: " + topicNum + " Progress: " + progress, Toast.LENGTH_LONG).show()

        btnNext.setOnClickListener {
            if(i<topic.size) {
                sectionProgress.progress = (i+1)
                displaySection(topic[i])
                i++
            }else{
                if(topicNum>progress){
                    userDBHelper.updateProgress(topicNum, username)
                }
                if(hasQuiz == true) {
                    showPopupModalQuiz(topicNum)
                } else {
                    showPopupModalNoQuiz()
                }
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true

    }

    private fun displaySection(section: Section){

        val content = section.getContent()
        val code = section.getCode()
        val title = section.getSectionTitle()
        val highlight: String = section.getHighlight()

        var contentString = ""
        for(i in 0 until content.size-1){
            contentString = contentString + content[i] + "\n" + "\n"
        }
        contentString = contentString + content[content.size-1]

        val tvTitle = findViewById<TextView>(R.id.tv_title)
        val tvContent = findViewById<TextView>(R.id.tv_content)
        val tvHighlight = findViewById<TextView>(R.id.tv_highlight)
        tvHighlight.text = ""
        tvHighlight.setBackgroundResource(0)

        tvTitle.text = title
        tvContent.text = contentString

        if(code.size>0) {
            var codeString = ""
            for (j in 0 until code.size-1) {
                codeString += "   "
                codeString = codeString + code[j] + "\n"
            }
            codeString = codeString + "   " + code[code.size-1]
            val tvCode = findViewById<TextView>(R.id.tv_code)
            tvCode.text = codeString
            tvCode.setBackgroundResource(R.drawable.code_border)
        }

        if(highlight.length>0){
            tvHighlight.text = "!!Note: " + highlight
            tvHighlight.setBackgroundResource(R.drawable.highlight_bg)
        }

    }

    fun showPopupModalQuiz(topicNum: Int) {
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popup_layout_with_quiz, null)
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
        popupWindow.height = 500

        val tv = view.findViewById<TextView>(R.id.tv_poopup_text)
        tv.setText("Congratulation! You have completed this topic. Do you want to take the quiz now?")
        val btnLater = view.findViewById<Button>(R.id.btn_later)
        val btnYes = view.findViewById<Button>(R.id.btn_yes)

        btnYes.setOnClickListener {
            // Go to Quiz page (remove the line below)
            val myIntent = Intent(this, ActivityQuiz::class.java)
            myIntent.putExtra("quizFile", "quiz_"+topicNum)
            startActivity(myIntent)
            popupWindow.dismiss()
        }


        btnLater.setOnClickListener{
            popupWindow.dismiss()
        }

        popupWindow.setOnDismissListener {
            Toast.makeText(applicationContext,"Good Job", Toast.LENGTH_SHORT).show()
            finish()
        }

        TransitionManager.beginDelayedTransition(topic_layout)
        popupWindow.showAtLocation(
                topic_layout,
                Gravity.CENTER,
                0,0
        )
    }

    fun showPopupModalNoQuiz() {
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popup_layout, null)
        val popupWindow = PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val progress = userDBHelper.getProgress()

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
        popupWindow.height = 500

        val tv = view.findViewById<TextView>(R.id.tv_poopup_text)
        tv.setText("Well done! You have completed this topic!")
        val btnPopup = view.findViewById<Button>(R.id.btn_yay)


        // Set a click listener for popup's button widget
        btnPopup.setOnClickListener{
            // Dismiss the popup window
            popupWindow.dismiss()
        }

        // Set a dismiss listener for popup window
        popupWindow.setOnDismissListener {
            Toast.makeText(applicationContext,"Good Job", Toast.LENGTH_SHORT).show()
//            val basicConceptIntent = Intent(this, ActivityBasicConcepts::class.java)
//            val flowControlIntent = Intent(this, ActivityFlowControl::class.java)
//            val functionsIntent = Intent(this, ActivityFunction::class.java)
//            val oopIntent = Intent(this, ActivityOOP::class.java)
            val myIntent = Intent()
            myIntent.putExtra("refresh", true)
            setResult(Activity.RESULT_OK, myIntent)
            finish()
//            if(progress<7){ startActivity(basicConceptIntent)}
//            if(progress<13){ startActivity(flowControlIntent)}
//            if(progress<16){ startActivity(functionsIntent)}
//            if(progress<24){ startActivity(oopIntent)}
        }

        TransitionManager.beginDelayedTransition(topic_layout)
        popupWindow.showAtLocation(
                topic_layout,
                Gravity.CENTER,
                0,0
        )
    }
}
