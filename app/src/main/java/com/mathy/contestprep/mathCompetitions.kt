package com.mathy.contestprep

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mathy.contestprep.IMO
import com.mathy.contestprep.R
import com.mathy.contestprep.eamo
import com.mathy.contestprep.imoCamp


class mathCompetitions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_math_competitions)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
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
            window.statusBarColor = resources.getColor(R.color.action, theme)
        }
        val umspic = findViewById<ImageView>(R.id.umspic)
        val imospic = findViewById<ImageView>(R.id.imopic)
        val pamopic = findViewById<ImageView>(R.id.pamopic)
        val eamopic = findViewById<ImageView>(R.id.eamopic)
        val iums = Intent(this, imoCamp::class.java)
        umspic.setOnTouchListener(object : OnTouchListener {
            var scaleUp2: Animation =
                AnimationUtils.loadAnimation(this@mathCompetitions, R.anim.boozup)
            var scaleDown2: Animation =
                AnimationUtils.loadAnimation(this@mathCompetitions, R.anim.boozdown)

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_UP) {
                    umspic.startAnimation(scaleUp2)

                    startActivity(iums)
                } else if (event.action == MotionEvent.ACTION_DOWN) {
                    umspic.startAnimation(scaleDown2)
                }
                return true
            }
        })
        val ipamo = Intent(this, pamo::class.java)
        pamopic.setOnTouchListener(object : OnTouchListener {
            var scaleUp2: Animation =
                AnimationUtils.loadAnimation(this@mathCompetitions, R.anim.boozup)
            var scaleDown2: Animation =
                AnimationUtils.loadAnimation(this@mathCompetitions, R.anim.boozdown)

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_UP) {
                    pamopic.startAnimation(scaleUp2)

                    startActivity(ipamo)
                } else if (event.action == MotionEvent.ACTION_DOWN) {
                    pamopic.startAnimation(scaleDown2)
                }
                return true
            }
        })
        val iimo = Intent(this, IMO::class.java)
        imospic.setOnTouchListener(object : OnTouchListener {
            var scaleUp2: Animation =
                AnimationUtils.loadAnimation(this@mathCompetitions, R.anim.boozup)
            var scaleDown2: Animation =
                AnimationUtils.loadAnimation(this@mathCompetitions, R.anim.boozdown)

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_UP) {
                    imospic.startAnimation(scaleUp2)

                    startActivity(iimo)
                } else if (event.action == MotionEvent.ACTION_DOWN) {
                    imospic.startAnimation(scaleDown2)
                }
                return true
            }
        })
        val ieamo = Intent(this, eamo::class.java)
        eamopic.setOnTouchListener(object : OnTouchListener {
            var scaleUp2: Animation =
                AnimationUtils.loadAnimation(this@mathCompetitions, R.anim.boozup)
            var scaleDown2: Animation =
                AnimationUtils.loadAnimation(this@mathCompetitions, R.anim.boozdown)

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_UP) {
                    eamopic.startAnimation(scaleUp2)

                    startActivity(ieamo)
                } else if (event.action == MotionEvent.ACTION_DOWN) {
                    eamopic.startAnimation(scaleDown2)
                }
                return true
            }
        })
    }
}