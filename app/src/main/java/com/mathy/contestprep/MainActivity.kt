package com.mathy.contestprep

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mathy.contestprep.R
import com.mathy.contestprep.appInfo
import com.mathy.contestprep.ladder
import com.google.firebase.analytics.FirebaseAnalytics




//import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen;
class MainActivity : AppCompatActivity() {

    lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        installSplashScreen()
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR); getSupportActionBar()?.hide();
        // installSplashScreen(this);
        getActionBar()?.hide();
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.action, theme)
        }

        //FirebaseAnalytics firebaseAnalytics;
       firebaseAnalytics = FirebaseAnalytics.getInstance(this);


       // FirebaseAnalytics.getInstance(this);
        val bundle = Bundle()
        bundle.putString("Source","Oncreate_startup")
        firebaseAnalytics.logEvent("App_launch_",bundle)

       val notificationScheduler = NotificationScheduler(this)
        notificationScheduler.scheduleDailyNotification()

/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    101
                )
            }
        }


        //Toolbar my_toolbar = findViewById(R.id.my_toolbar);
        // setSupportActionBar(my_toolbar);
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.action, theme)
        }
        val CHANNEL_ID = "MathNotification"*/

        // showNoti();
        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle("Hello").setContentText("hello").setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.notify();*/
        val math_problems = findViewById<ConstraintLayout>(R.id.math_problems)
        val ii = Intent(this, mathProblems::class.java)
        math_problems.setOnTouchListener(object : OnTouchListener {
            var scaleUp: Animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.boozup)
            var scaleDown: Animation =
                AnimationUtils.loadAnimation(this@MainActivity, R.anim.boozdown)

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_UP) {
                    math_problems.startAnimation(scaleUp)

                    startActivity(ii)
                } else if (event.action == MotionEvent.ACTION_DOWN) {
                    math_problems.startAnimation(scaleDown)
                }
                return true
            }
        })



        val math_tips = findViewById<ConstraintLayout>(R.id.math_tips)
        val iitip = Intent(this, mathTips::class.java)
        math_tips.setOnTouchListener(object : OnTouchListener {
            var scaleUp8: Animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.boozup)
            var scaleDown8: Animation =
                    AnimationUtils.loadAnimation(this@MainActivity, R.anim.boozdown)

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_UP) {
                    math_tips.startAnimation(scaleUp8)

                    startActivity(iitip)
                } else if (event.action == MotionEvent.ACTION_DOWN) {
                    math_tips.startAnimation(scaleDown8)
                }
                return true
            }
        })





        val rand_qu = findViewById<ConstraintLayout>(R.id.rand_qu)
        val iiqu = Intent(this, randQus::class.java)
        rand_qu.setOnTouchListener(object : OnTouchListener {
            var scaleUp8: Animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.boozup)
            var scaleDown8: Animation =
                    AnimationUtils.loadAnimation(this@MainActivity, R.anim.boozdown)

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_UP) {
                    rand_qu.startAnimation(scaleUp8)

                    startActivity(iiqu)
                } else if (event.action == MotionEvent.ACTION_DOWN) {
                    rand_qu.startAnimation(scaleDown8)
                }
                return true
            }
        })

        val math_info = findViewById<ConstraintLayout>(R.id.info)
        val iinfo = Intent(this, appInfo::class.java)
        math_info.setOnTouchListener(object : OnTouchListener {
            var scaleUp2: Animation =
                AnimationUtils.loadAnimation(this@MainActivity, R.anim.boozup2)
            var scaleDown2: Animation =
                AnimationUtils.loadAnimation(this@MainActivity, R.anim.boozdown2)

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_UP) {
                    math_info.startAnimation(scaleUp2)

                    startActivity(iinfo)
                } else if (event.action == MotionEvent.ACTION_DOWN) {
                    math_info.startAnimation(scaleDown2)
                }
                return true
            }
        })
        val math_ladder = findViewById<ConstraintLayout>(R.id.ladder)
        val ilad = Intent(this, ladder::class.java)
        math_ladder.setOnTouchListener(object : OnTouchListener {
            var scaleUp2: Animation =
                AnimationUtils.loadAnimation(this@MainActivity, R.anim.boozup2)
            var scaleDown2: Animation =
                AnimationUtils.loadAnimation(this@MainActivity, R.anim.boozdown2)

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_UP) {
                    math_ladder.startAnimation(scaleUp2)
                    // showNoti();
                    startActivity(ilad)
                    val pp = findViewById<ProgressBar>(R.id.progressBar2)
                    pp.visibility = View.VISIBLE
                } else if (event.action == MotionEvent.ACTION_DOWN) {
                    math_ladder.startAnimation(scaleDown2)
                }
                return true
            }
        })
        val math_competitions = findViewById<ConstraintLayout>(R.id.competitions)
        val i = Intent(this, mathCompetitions::class.java)
        math_competitions.setOnTouchListener(object : OnTouchListener {
            var scaleUp2: Animation =
                AnimationUtils.loadAnimation(this@MainActivity, R.anim.boozup2)
            var scaleDown2: Animation =
                AnimationUtils.loadAnimation(this@MainActivity, R.anim.boozdown2)

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_UP) {
                    math_competitions.startAnimation(scaleUp2)

                    startActivity(i)
                } else if (event.action == MotionEvent.ACTION_DOWN) {
                    math_competitions.startAnimation(scaleDown2)
                }
                return true
            }
        })
    }


    override fun onResume() {
        val math_problems = findViewById<ConstraintLayout>(R.id.math_problems)

        super.onResume()
        val pp = findViewById<ProgressBar>(R.id.progressBar2)
        pp.visibility = View.INVISIBLE
    }

    private fun showNoti() {
        val CHANNEL_ID = "MathNotification"
        val mBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // notification icon
            .setContentTitle("Notification!") // title for notification
            .setContentText("Hello word") // clear notification after click
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("any", "nere is value")
        val pi =
            PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_MUTABLE)
        mBuilder.setContentIntent(pi)
        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var nc = mNotificationManager.getNotificationChannel(CHANNEL_ID)
            if (nc == null) {
                val importance = NotificationManager.IMPORTANCE_HIGH
                nc = NotificationChannel(CHANNEL_ID, "describe", importance)
                nc.setLightColor(R.color.black)
                nc.enableVibration(true)
                mNotificationManager.createNotificationChannel(nc)
            }
        }
        mNotificationManager.notify(0, mBuilder.build())
    }
}




