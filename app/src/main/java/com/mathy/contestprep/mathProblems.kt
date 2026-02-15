package com.mathy.contestprep

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mathy.contestprep.R

class mathProblems : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_math_problems)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val alevelQuester2 = findViewById<ConstraintLayout>(R.id.alevelQuester2)
        val Aquest = Intent(this, alevelQuester::class.java)
        alevelQuester2.setOnTouchListener { v, event ->
            val scaleUp2 = AnimationUtils.loadAnimation(this, R.anim.boozup2)
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)

            if (event.action == MotionEvent.ACTION_UP) {
                alevelQuester2.startAnimation(scaleUp2)
                startActivity(Aquest)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                alevelQuester2.startAnimation(scaleDown2)
            }
            true
        }

        val olevelQuester2 = findViewById<ConstraintLayout>(R.id.olevelQuester2)
        val Oquest = Intent(this, olevelQuester::class.java)
        olevelQuester2.setOnTouchListener { v, event ->
            val scaleUp2 = AnimationUtils.loadAnimation(this, R.anim.boozup2)
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)

            if (event.action == MotionEvent.ACTION_UP) {
                olevelQuester2.startAnimation(scaleUp2)
                startActivity(Oquest)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                olevelQuester2.startAnimation(scaleDown2)
            }
            true
        }
        val primaryQuester = findViewById<ConstraintLayout>(R.id.primaryQuester)
        // Intent Pquest = new Intent(this,com.mathy.contestprep.olevelQuester.class);
        primaryQuester.setOnTouchListener { v, event ->
            val scaleUp2 = AnimationUtils.loadAnimation(this, R.anim.boozup2)
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)

            if (event.action == MotionEvent.ACTION_UP) {
                primaryQuester.startAnimation(scaleUp2)
                // startActivity(Oquest);
                //  startActivity(i);
                Toast.makeText(applicationContext, R.string.toBe, Toast.LENGTH_SHORT).show()
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                primaryQuester.startAnimation(scaleDown2)
            }
            true
        }

        val olyQuester = findViewById<ConstraintLayout>(R.id.olyQuester)
        // Intent Pquest = new Intent(this,com.mathy.contestprep.olevelQuester.class);
        olyQuester.setOnTouchListener { v, event ->
            val scaleUp2 = AnimationUtils.loadAnimation(this, R.anim.boozup2)
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)

            if (event.action == MotionEvent.ACTION_UP) {
                olyQuester.startAnimation(scaleUp2)
                // startActivity(Oquest);
                //  startActivity(i);
                Toast.makeText(applicationContext, R.string.toBe, Toast.LENGTH_SHORT).show()
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                olyQuester.startAnimation(scaleDown2)
            }
            true
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
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.action, theme)
        }
    }
}