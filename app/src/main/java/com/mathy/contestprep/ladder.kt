package com.mathy.contestprep

import android.content.Context
import android.database.sqlite.SQLiteException
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ladder : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_ladder)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val context: Context = this
        val window = this.window
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.action, theme)
        }
        val database = DBhandler(this@ladder)


        val corsorStateCorector = database.readableDatabase.query(
            "ladderState",
            arrayOf("name", "state"),
            null,
            null,
            null,
            null,
            null
        )
        if (corsorStateCorector.moveToNext()) {
        } else {
            database.starter()
        }
        while (corsorStateCorector.moveToNext()) {
            if (corsorStateCorector.getString(1) == "true") {
                if (corsorStateCorector.getString(0) == "one") {
                    val but3 = findViewById<Button>(R.id.button3)
                    but3.text = "COMPLETED"
                    val answerView = findViewById<TextView>(R.id.one)
                    answerView.text = "ANS = yes"
                    but3.isEnabled = false
                }
                if (corsorStateCorector.getString(0) == "two") {
                    val but3 = findViewById<Button>(R.id.button4)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.two)
                    answerView.text = "ANS = 50"
                }
                if (corsorStateCorector.getString(0) == "three") {
                    val but3 = findViewById<Button>(R.id.button5)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.three)
                    answerView.text = "ANS = yes"
                }
                if (corsorStateCorector.getString(0) == "four") {
                    val but3 = findViewById<Button>(R.id.button6)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.four)
                    answerView.text = "ANS = 34"
                }
                if (corsorStateCorector.getString(0) == "five") {
                    val but3 = findViewById<Button>(R.id.button26)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.five)
                    answerView.text = "ANS = 5"
                }
                if (corsorStateCorector.getString(0) == "six") {
                    val but3 = findViewById<Button>(R.id.button7)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.six)
                    answerView.text = "ANS = 4"
                }
                if (corsorStateCorector.getString(0) == "seven") {
                    val but3 = findViewById<Button>(R.id.button8)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.seven)
                    answerView.text = "ANS 1:4:5"
                }
                if (corsorStateCorector.getString(0) == "eight") {
                    val but3 = findViewById<Button>(R.id.button9)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.eight)
                    answerView.text = "ANS = 3"
                }
                if (corsorStateCorector.getString(0) == "nine") {
                    val but3 = findViewById<Button>(R.id.button27)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.nine)
                    answerView.text = "ANS = 240"
                }
                if (corsorStateCorector.getString(0) == "ten") {
                    val but3 = findViewById<Button>(R.id.button10)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.ten)
                    answerView.text = "ANS = 8"
                }
                if (corsorStateCorector.getString(0) == "eleven") {
                    val but3 = findViewById<Button>(R.id.button28)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.eleven)
                    answerView.text = "ANS = 41.67"
                }
                if (corsorStateCorector.getString(0) == "twelve") {
                    val but3 = findViewById<Button>(R.id.button11)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.twelve)
                    answerView.text = "ANS = yes"
                }
                if (corsorStateCorector.getString(0) == "thirteen") {
                    val but3 = findViewById<Button>(R.id.button12)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.thirteen)
                    answerView.text = "ANS = 0.94"
                }
                if (corsorStateCorector.getString(0) == "fourteen") {
                    val but3 = findViewById<Button>(R.id.button13)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.fourteen)
                    answerView.text = "ANS = 0.06"
                }
                if (corsorStateCorector.getString(0) == "fifteen") {
                    val but3 = findViewById<Button>(R.id.button14)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.fifteen)
                    answerView.text = "ANS = 540"
                }
                if (corsorStateCorector.getString(0) == "sixteen") {
                    val but3 = findViewById<Button>(R.id.button25)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.sixteen)
                    answerView.text = "ANS = 7"
                }
                if (corsorStateCorector.getString(0) == "seventeen") {
                    val but3 = findViewById<Button>(R.id.button15)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.seventeen)
                    answerView.text = "ANS = 15"
                }
                if (corsorStateCorector.getString(0) == "eighteen") {
                    val but3 = findViewById<Button>(R.id.button16)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.eighteen)
                    answerView.text = "ANS = 188"
                }
                if (corsorStateCorector.getString(0) == "nineteen") {
                    val but3 = findViewById<Button>(R.id.button17)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.nineteen)
                    answerView.text = "ANS = 0.33"
                }
                if (corsorStateCorector.getString(0) == "twenty") {
                    val but3 = findViewById<Button>(R.id.button24)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.twenty)
                    answerView.text = "ANS = 28"
                }
                if (corsorStateCorector.getString(0) == "twentyone") {
                    val but3 = findViewById<Button>(R.id.button18)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.twentyone)
                    answerView.text = "ANS = 105"
                }
                if (corsorStateCorector.getString(0) == "twentytwo") {
                    val but3 = findViewById<Button>(R.id.button19)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.twentytwo)
                    answerView.text = "ANS = 22"
                }
                if (corsorStateCorector.getString(0) == "twentythree") {
                    val but3 = findViewById<Button>(R.id.button20)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.twentythree)
                    answerView.text = "ANS 784913526"
                }
                if (corsorStateCorector.getString(0) == "twentyfour") {
                    val but3 = findViewById<Button>(R.id.button23)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.twentyfour)
                    answerView.text = "ANS = 100"
                }
                if (corsorStateCorector.getString(0) == "twentyfive") {
                    val but3 = findViewById<Button>(R.id.button21)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.twentyfive)
                    answerView.text = "ANS = 144"
                }
                if (corsorStateCorector.getString(0) == "twentysix") {
                    val but3 = findViewById<Button>(R.id.button22)
                    but3.text = "COMPLETED"
                    but3.isEnabled = false
                    val answerView = findViewById<TextView>(R.id.twentysix)
                    answerView.text = "ANS = 3"
                }
            }
        }
        val backer = findViewById<ImageView>(R.id.backer)
        backer.setOnTouchListener { v: View?, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_UP) {
                //    p12023toggle.startAnimation(scaleUp2);
                backer.background = resources.getDrawable(R.drawable.baup, theme)

                finish()

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                backer.background = resources.getDrawable(R.drawable.backerbg, theme)
                //    p12023toggle.startAnimation(scaleDown2);
            }
            true
        }
        val scrollView = findViewById<ScrollView>(R.id.scrolly)
        val tvv = findViewById<TextView>(R.id.textView)
        scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val mn = scrollView.scrollY

            //  tvv.setText(String.valueOf(mn));
            if (mn < 1805) {
                if (Build.VERSION.SDK_INT >= 21) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.action, theme)
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)

                    cl.setBackgroundColor(context.resources.getColor(R.color.action, theme))

                }
            } else if (mn > 1805 && mn <= 3600) {
                val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                cl.setBackgroundColor(context.resources.getColor(R.color.two, theme))

                if (Build.VERSION.SDK_INT >= 21) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.two, theme)

                }
            } else if (mn > 3600 && mn <= 5400) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.three, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.three, theme)
                }
            } else if (mn > 5400 && mn <= 7200) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.four, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.four, theme)
                }
            } else if (mn > 7200 && mn <= 9300) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.five, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.five, theme)
                }
            } else if (mn > 9300 && mn <= 11400) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.six, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.six, theme)
                }
            } else if (mn > 11400 && mn <= 13500) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.seven, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.seven, theme)
                }
            } else if (mn > 13500 && mn <= 15600) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.eight, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.eight, theme)
                }
            } else if (mn > 15600 && mn <= 17700) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.nine, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.nine, theme)
                }
            } else if (mn > 17700 && mn <= 19800) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.ten, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.ten, theme)
                }
            } else if (mn > 19800 && mn <= 21900) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.eleven, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.eleven, theme)
                }
            } else if (mn > 21900 && mn <= 24000) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.twelve, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.twelve, theme)
                }
            } else if (mn > 24000 && mn <= 26100) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.thirteen, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.thirteen, theme)
                }
            } else if (mn > 26100 && mn <= 28200) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.fourteen, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.fourteen, theme)
                }
            } else if (mn > 28200 && mn <= 30300) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.fifteen, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.fifteen, theme)
                }
            } else if (mn > 30300 && mn <= 32400) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.sixteen, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.sixteen, theme)
                }
            } else if (mn > 32400 && mn <= 34500) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.seventeen, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.seventeen, theme)
                }
            } else if (mn > 34500 && mn <= 36600) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.eighteen, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.eighteen, theme)
                }
            } else if (mn > 36600 && mn <= 38700) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.nineteen, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.nineteen, theme)
                }
            } else if (mn > 38700 && mn <= 40800) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.twenty, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.twenty, theme)
                }
            } else if (mn > 40800 && mn <= 42900) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.twentyone, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.twentyone, theme)
                }
            } else if (mn > 42900 && mn <= 45000) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.twentythree, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.twentythree, theme)
                }
            } else if (mn > 45000 && mn <= 47100) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.twentyfour, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.twentyfour, theme)
                }
            } else if (mn > 47100 && mn <= 49200) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.twentyfive, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.twentyfive, theme)
                }
            } else if (mn > 49200 && mn <= 51300) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.twentysix, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.twentysix, theme)
                }
            } else if (mn > 51300) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val cl = findViewById<ConstraintLayout>(R.id.constraintLayout2)
                    cl.setBackgroundColor(context.resources.getColor(R.color.twentyseven, theme))


                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = context.resources.getColor(R.color.twentyseven, theme)
                }
            }
        }

        scrollView.setOnTouchListener { v, event -> //  TextView tvv = findViewById(R.id.yset);
            false
        }


        val button3 = findViewById<Button>(R.id.button3)
        button3.setOnClickListener { v: View? ->
            val checkBox = findViewById<CheckBox>(R.id.checkBox)
            val checkBox2 = findViewById<CheckBox>(R.id.checkBox2)
            if (checkBox.isChecked && checkBox2.isChecked) {
                Toast.makeText(applicationContext, "Select only one", Toast.LENGTH_SHORT).show()
            } else if (checkBox.isChecked && !checkBox2.isChecked) {
                button3.text = "COMPLETED"
                button3.isEnabled = false
                val answerView = findViewById<TextView>(R.id.one)
                answerView.text = "ANS = yes"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()


                try {
                    database.update("one", "true", "one")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
            } else if (!checkBox.isChecked && checkBox2.isChecked) {
                try {
                    val emmm = database.readVal("one")
                    Toast.makeText(applicationContext, emmm, Toast.LENGTH_SHORT).show()
                    // Toast.makeText(getApplicationContext(),"Try again", Toast.LENGTH_SHORT).show();
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(applicationContext, "Make a selection", Toast.LENGTH_SHORT).show()
            }
        }

        val button4 = findViewById<Button>(R.id.button4)
        button4.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            //  Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "50") {
                try {
                    database.update("two", "true", "two")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                val answerView = findViewById<TextView>(R.id.two)
                answerView.text = "ANS = 50"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                button4.text = "COMPLETED"
                button4.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
        val button5 = findViewById<Button>(R.id.button5)
        button5.setOnClickListener { v: View? ->
            val checkBox = findViewById<CheckBox>(R.id.checkBox3)
            val checkBox2 = findViewById<CheckBox>(R.id.checkBox4)
            if (checkBox.isChecked && checkBox2.isChecked) {
                Toast.makeText(applicationContext, "Select only one", Toast.LENGTH_SHORT).show()
            } else if (checkBox.isChecked && !checkBox2.isChecked) {
                val answerView = findViewById<TextView>(R.id.three)
                answerView.text = "ANS = yes"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                try {
                    database.update("three", "true", "three")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                button5.text = "COMPLETED"
                button5.isEnabled = false
            } else if (!checkBox.isChecked && checkBox2.isChecked) {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Make a selection", Toast.LENGTH_SHORT).show()
            }
        }
        val button6 = findViewById<Button>(R.id.button6)
        button6.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText2)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            //Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "34") {
                try {
                    database.update("four", "true", "four")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                val answerView = findViewById<TextView>(R.id.four)
                answerView.text = "ANS = 34"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                button6.text = "COMPLETED"
                button6.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
        val button26 = findViewById<Button>(R.id.button26)
        button26.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText23)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            //  Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "5") {
                try {
                    database.update("five", "true", "five")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                val answerView = findViewById<TextView>(R.id.five)
                answerView.text = "ANS = 5"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                button26.text = "COMPLETED"
                button26.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
        val button7 = findViewById<Button>(R.id.button7)
        button7.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText3)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            //  Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "4") {
                try {
                    database.update("six", "true", "six")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                val answerView = findViewById<TextView>(R.id.six)
                answerView.text = "ANS = 4"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                button7.text = "COMPLETED"
                button7.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
        val button8 = findViewById<Button>(R.id.button8)
        button8.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText4)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            val editer2 = findViewById<EditText>(R.id.editTextText5)
            val numberEnt2 = editer2.text
            val num2 = numberEnt2.toString()
            val editer3 = findViewById<EditText>(R.id.editTextText6)
            val numberEnt3 = editer3.text
            val num3 = numberEnt3.toString()
            val num5 = num + num2 + num3
            //   Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num5 == "415" || num5 == "451" || num5 == "145" || num5 == "154" || num5 == "514" || num5 == "541") {
                val answerView = findViewById<TextView>(R.id.seven)
                answerView.text = "ANS 1:4:5"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                try {
                    database.update("seven", "true", "seven")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                button8.text = "COMPLETED"
                button8.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()

                //  Toast.makeText(getApplicationContext(),num5, Toast.LENGTH_SHORT).show();
            }
        }
        val button9 = findViewById<Button>(R.id.button9)
        button9.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText7)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            //   Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "3") {
                val answerView = findViewById<TextView>(R.id.eight)
                answerView.text = "ANS = 3"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                try {
                    database.update("eight", "true", "eight")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                button9.text = "COMPLETED"
                button9.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
        val button27 = findViewById<Button>(R.id.button27)
        button27.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText24)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            //   Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "240") {
                val answerView = findViewById<TextView>(R.id.nine)
                answerView.text = "ANS = 240"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                try {
                    database.update("nine", "true", "nine")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                button27.text = "COMPLETED"
                button27.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
        val button10 = findViewById<Button>(R.id.button10)
        button10.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText8)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            // Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "8") {
                val answerView = findViewById<TextView>(R.id.ten)
                answerView.text = "ANS = 8"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                try {
                    database.update("ten", "true", "ten")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                button10.text = "COMPLETED"
                button10.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
        val button28 = findViewById<Button>(R.id.button28)
        button28.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText25)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            // Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "41.67") {
                val answerView = findViewById<TextView>(R.id.eleven)
                answerView.text = "ANS = 41.67"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                try {
                    database.update("eleven", "true", "eleven")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                button28.text = "COMPLETED"
                button28.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
        val button11 = findViewById<Button>(R.id.button11)
        button11.setOnClickListener { v: View? ->
            val checkBox = findViewById<CheckBox>(R.id.checkBox5)
            val checkBox2 = findViewById<CheckBox>(R.id.checkBox6)
            if (checkBox.isChecked && checkBox2.isChecked) {
                Toast.makeText(applicationContext, "Select only one", Toast.LENGTH_SHORT).show()
            } else if (checkBox.isChecked && !checkBox2.isChecked) {
                button11.text = "COMPLETED"
                button11.isEnabled = false
                val answerView = findViewById<TextView>(R.id.twelve)
                answerView.text = "ANS = yes"
                Toast.makeText(applicationContext, "Congratulations!!", Toast.LENGTH_SHORT).show()
                try {
                    database.update("twelve", "true", "twelve")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
            } else if (!checkBox.isChecked && checkBox2.isChecked) {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Make a selection", Toast.LENGTH_SHORT).show()
            }
        }
        val button12 = findViewById<Button>(R.id.button12)
        button12.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText9)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            // Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "0.94") {
                val answerView = findViewById<TextView>(R.id.thirteen)
                answerView.text = "ANS = 0.94"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                button12.text = "COMPLETED"
                button12.isEnabled = false
                try {
                    database.update("thirteen", "true", "thirteen")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
        val button13 = findViewById<Button>(R.id.button13)
        button13.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText10)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            // Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "0.06") {
                val answerView = findViewById<TextView>(R.id.fourteen)
                answerView.text = "ANS = 0.06"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                try {
                    database.update("fourteen", "true", "fourteen")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                button13.text = "COMPLETED"
                button13.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
        val button14 = findViewById<Button>(R.id.button14)
        button14.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText11)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            // Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "540") {
                val answerView = findViewById<TextView>(R.id.fifteen)
                answerView.text = "ANS = 540"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                try {
                    database.update("fifteen", "true", "fifteen")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                button14.text = "COMPLETED"
                button14.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
        val button25 = findViewById<Button>(R.id.button25)
        button25.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText22)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            // Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "7") {
                val answerView = findViewById<TextView>(R.id.sixteen)
                answerView.text = "ANS = 7"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                try {
                    database.update("sixteen", "true", "sixteen")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                button25.text = "COMPLETED"
                button25.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
        val button15 = findViewById<Button>(R.id.button15)
        button15.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText12)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            // Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "15") {
                val answerView = findViewById<TextView>(R.id.seventeen)
                answerView.text = "ANS = 15"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                try {
                    database.update("seventeen", "true", "seventeen")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                button15.text = "COMPLETED"
                button15.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
        val button16 = findViewById<Button>(R.id.button16)
        button16.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText13)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            // Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "188") {
                val answerView = findViewById<TextView>(R.id.eighteen)
                answerView.text = "ANS = 188"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                try {
                    database.update("eighteen", "true", "eighteen")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                button16.text = "COMPLETED"
                button16.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
        val button17 = findViewById<Button>(R.id.button17)
        button17.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText14)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            // Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "0.33") {
                val answerView = findViewById<TextView>(R.id.nineteen)
                answerView.text = "ANS = 0.33"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                try {
                    database.update("nineteen", "true", "nineteen")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                button17.text = "COMPLETED"
                button17.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
        val button24 = findViewById<Button>(R.id.button24)
        button24.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText21)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            // Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "28" || num == "24") {
                val answerView = findViewById<TextView>(R.id.twenty)
                answerView.text = "ANS = 28"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                try {
                    database.update("twenty", "true", "twenty")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                button24.text = "COMPLETED"
                button24.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
        val button18 = findViewById<Button>(R.id.button18)
        button18.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText15)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            // Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "105") {
                val answerView = findViewById<TextView>(R.id.twentyone)
                answerView.text = "ANS = 105"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                try {
                    database.update("twentyone", "true", "twentyone")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                button18.text = "COMPLETED"
                button18.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
        val button19 = findViewById<Button>(R.id.button19)
        button19.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText16)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            // Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "22") {
                val answerView = findViewById<TextView>(R.id.twentytwo)
                answerView.text = "ANS = 22"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                try {
                    database.update("twentytwo", "true", "twentytwo")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                button19.text = "COMPLETED"
                button19.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
        val button20 = findViewById<Button>(R.id.button20)
        button20.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText17)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            // Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "784913526") {
                val answerView = findViewById<TextView>(R.id.twentythree)
                answerView.text = "ANS 784913526"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                try {
                    database.update("twentythree", "true", "twentythree")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                button20.text = "COMPLETED"
                button20.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
        val button23 = findViewById<Button>(R.id.button23)
        button23.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText20)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            // Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "100") {
                val answerView = findViewById<TextView>(R.id.twentyfour)
                answerView.text = "ANS = 100"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                try {
                    database.update("twentyfour", "true", "twentyfour")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                button23.text = "COMPLETED"
                button23.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
        val button21 = findViewById<Button>(R.id.button21)
        button21.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText18)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            // Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "144") {
                val answerView = findViewById<TextView>(R.id.twentyfive)
                answerView.text = "ANS = 144"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                try {
                    database.update("twentyfive", "true", "twentyfive")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                button21.text = "COMPLETED"
                button21.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
        val button22 = findViewById<Button>(R.id.button22)
        button22.setOnClickListener { v: View? ->
            val editer = findViewById<EditText>(R.id.editTextText19)
            val numberEnt = editer.text
            val num = numberEnt.toString()
            //  Toast.makeText(getApplicationContext(),num, Toast.LENGTH_SHORT).show();
            if (num == "3") {
                val answerView = findViewById<TextView>(R.id.twentysix)
                answerView.text = "ANS = 3"
                Toast.makeText(applicationContext, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                try {
                    database.update("twentysix", "true", "twentysix")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                    Toast.makeText(this@ladder, "Rejected", Toast.LENGTH_SHORT)
                }
                button22.text = "COMPLETED"
                button22.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
    }
}