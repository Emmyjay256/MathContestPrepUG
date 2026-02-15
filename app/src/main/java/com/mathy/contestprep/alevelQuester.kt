package com.mathy.contestprep

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.mathy.contestprep.pdfDisplayer

class alevelQuester : AppCompatActivity() {
    // NAMING ALGO
    // FOR TEXTVIEWS
    //       p12023toggle shows the paper no ie 'p1' and the year '2023'
    //       intents have i20231  'i' for intent followed by '2023' year and paper '1'
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_alevel_quester)
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
        MobileAds.initialize(this) { }

        val adView = findViewById<AdView>(R.id.adView)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        val scaleUp22 = AnimationUtils.loadAnimation(this, R.anim.boozup2)
        val scaleDown22 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
        val backer = findViewById<ImageView>(R.id.backer)

        // backer.setOnClickListener(v-> finish());
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



        val i20241 = Intent(this, pdfDisplayer::class.java)
        val p12024toggle = findViewById<TextView>(R.id.p12024toggle)
        p12024toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                    this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p12024toggle.startAnimation(scaleUp2)
                val myString = "2024_p1_Alevel_selected.pdf"
                i20241.putExtra("pdfName", myString)
                startActivity(i20241)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p12024toggle.startAnimation(scaleDown2)
            }
            true
        }


        val i20242 = Intent(this, pdfDisplayer::class.java)
        val p22024toggle = findViewById<TextView>(R.id.p22024toggle)
        p22024toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                    this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p22024toggle.startAnimation(scaleUp2)
                val myString = "2024_p2_Alevel_selected.pdf"
                i20242.putExtra("pdfName", myString)
                startActivity(i20242)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p22024toggle.startAnimation(scaleDown2)
            }
            true
        }





        val p12023toggle = findViewById<TextView>(R.id.p12023toggle)
       // val i20231 = new Intent(this, pdfDisplayer::class)
        val i20231 = Intent(this, pdfDisplayer::class.java)
        // Intent i20231 = new Intent(this, p12023.class);
        p12023toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p12023toggle.startAnimation(scaleUp2)
                val myString = "2023_p1_Alevel_selected.pdf"
                i20231.putExtra("pdfName", myString)
                startActivity(i20231)


                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p12023toggle.startAnimation(scaleDown2)
            }
            true
        }
        val i20232 = Intent(this, pdfDisplayer::class.java)
        val p22023toggle = findViewById<TextView>(R.id.p22023toggle)
        p22023toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p22023toggle.startAnimation(scaleUp2)
                val myString = "2023_p2_Alevel_selected.pdf"
                i20232.putExtra("pdfName", myString)
                startActivity(i20232)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p22023toggle.startAnimation(scaleDown2)
            }
            true
        }
        val p2022toggle = findViewById<TextView>(R.id.p2022toggle)
        val i2022 = Intent(this, pdfDisplayer::class.java)
        p2022toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p2022toggle.startAnimation(scaleUp2)
                val myString = "2022_solo_Alevel_selected.pdf"
                i2022.putExtra("pdfName", myString)
                startActivity(i2022)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p2022toggle.startAnimation(scaleDown2)
            }
            true
        }

        val p12019toggle = findViewById<TextView>(R.id.p12019toggle)
        val i20191 = Intent(this, pdfDisplayer::class.java)
        p12019toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p12019toggle.startAnimation(scaleUp2)
                val myString = "2019_p1_Alevel_selected.pdf"
                i20191.putExtra("pdfName", myString)
                startActivity(i20191)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p12019toggle.startAnimation(scaleDown2)
            }
            true
        }
        val p22019toggle = findViewById<TextView>(R.id.p22019toggle)
        val i20192 = Intent(this, pdfDisplayer::class.java)
        p22019toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p22019toggle.startAnimation(scaleUp2)
                val myString = "2019_p2_Alevel_selected.pdf"
                i20192.putExtra("pdfName", myString)
                startActivity(i20192)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p22019toggle.startAnimation(scaleDown2)
            }
            true
        }
        val p12018toggle = findViewById<TextView>(R.id.p12018toggle)
        val i20181 = Intent(this, pdfDisplayer::class.java)
        p12018toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p12018toggle.startAnimation(scaleUp2)
                val myString = "2018_p1_Alevel_selected.pdf"
                i20181.putExtra("pdfName", myString)
                startActivity(i20181)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p12018toggle.startAnimation(scaleDown2)
            }
            true
        }
        val p22018toggle = findViewById<TextView>(R.id.p22018toggle)
        val i20182 = Intent(this, pdfDisplayer::class.java)
        p22018toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p22018toggle.startAnimation(scaleUp2)
                val myString = "2018_p2_Alevel_selected.pdf"
                i20182.putExtra("pdfName", myString)
                startActivity(i20182)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p22018toggle.startAnimation(scaleDown2)
            }
            true
        }
        val p12017toggle = findViewById<TextView>(R.id.p12017toggle)
        val i20171 = Intent(this, pdfDisplayer::class.java)
        p12017toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p12017toggle.startAnimation(scaleUp2)
                val myString = "2017_p1_Alevel_selected.pdf"
                i20171.putExtra("pdfName", myString)
                startActivity(i20171)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p12017toggle.startAnimation(scaleDown2)
            }
            true
        }

        val p22017toggle = findViewById<TextView>(R.id.p22017toggle)
        val i20172 = Intent(this, pdfDisplayer::class.java)
        p22017toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p22017toggle.startAnimation(scaleUp2)
                val myString = "2017_p2_Alevel_selected.pdf"
                i20172.putExtra("pdfName", myString)
                startActivity(i20172)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p22017toggle.startAnimation(scaleDown2)
            }
            true
        }

        val p12016toggle = findViewById<TextView>(R.id.p12016toggle)
        val i20161 = Intent(this, pdfDisplayer::class.java)
        p12016toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p12016toggle.startAnimation(scaleUp2)
                val myString = "2016_p1_Alevel_selected.pdf"
                i20161.putExtra("pdfName", myString)
                startActivity(i20161)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p12016toggle.startAnimation(scaleDown2)
            }
            true
        }

        val p22016toggle = findViewById<TextView>(R.id.p22016toggle)
        val i20162 = Intent(this, pdfDisplayer::class.java)
        p22016toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p22016toggle.startAnimation(scaleUp2)
                val myString = "2016_p2_Alevel_selected.pdf"
                i20162.putExtra("pdfName", myString)
                startActivity(i20162)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p22016toggle.startAnimation(scaleDown2)
            }
            true
        }

        val p12015toggle = findViewById<TextView>(R.id.p12015toggle)
        val i20151 = Intent(this, pdfDisplayer::class.java)
        p12015toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p12015toggle.startAnimation(scaleUp2)
                val myString = "2015_p1_Alevel_selected.pdf"
                i20151.putExtra("pdfName", myString)
                startActivity(i20151)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p12015toggle.startAnimation(scaleDown2)
            }
            true
        }

        val p22015toggle = findViewById<TextView>(R.id.p22015toggle)
        val i20152 = Intent(this, pdfDisplayer::class.java)
        p22015toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p22015toggle.startAnimation(scaleUp2)
                val myString = "2015_p2_Alevel_selected.pdf"
                i20152.putExtra("pdfName", myString)
                startActivity(i20152)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p22015toggle.startAnimation(scaleDown2)
            }
            true
        }

        val p12014toggle = findViewById<TextView>(R.id.p12014toggle)
        val i20141 = Intent(this, pdfDisplayer::class.java)
        p12014toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p12014toggle.startAnimation(scaleUp2)
                val myString = "2014_p1_Alevel_selected.pdf"
                i20141.putExtra("pdfName", myString)
                startActivity(i20141)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p12014toggle.startAnimation(scaleDown2)
            }
            true
        }

        val p12013toggle = findViewById<TextView>(R.id.p12013toggle)
        val i20131 = Intent(this, pdfDisplayer::class.java)
        p12013toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_UP) {
                p12013toggle.startAnimation(scaleUp22)
                val myString = "2013_p1_Alevel_selected.pdf"
                i20131.putExtra("pdfName", myString)
                startActivity(i20131)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p12013toggle.startAnimation(scaleDown22)
            }
            true
        }

        val p22013toggle = findViewById<TextView>(R.id.p22013toggle)
        val i20132 = Intent(this, pdfDisplayer::class.java)
        p22013toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p22013toggle.startAnimation(scaleUp2)
                val myString = "2013_p2_Alevel_selected.pdf"
                i20132.putExtra("pdfName", myString)
                startActivity(i20132)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p22013toggle.startAnimation(scaleDown2)
            }
            true
        }

        val p12012toggle = findViewById<TextView>(R.id.p12012toggle)
        val i20121 = Intent(this, pdfDisplayer::class.java)
        p12012toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p12012toggle.startAnimation(scaleUp2)
                val myString = "2012_p1_Alevel_selected.pdf"
                i20121.putExtra("pdfName", myString)
                startActivity(i20121)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p12012toggle.startAnimation(scaleDown2)
            }
            true
        }

        val p22012toggle = findViewById<TextView>(R.id.p22012toggle)
        val i20122 = Intent(this, pdfDisplayer::class.java)
        p22012toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p22012toggle.startAnimation(scaleUp2)
                val myString = "2012_p2_Alevel_selected.pdf"
                i20122.putExtra("pdfName", myString)
                startActivity(i20122)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p22012toggle.startAnimation(scaleDown2)
            }
            true
        }

        val p12011toggle = findViewById<TextView>(R.id.p12011toggle)
        val i20111 = Intent(this, pdfDisplayer::class.java)
        p12011toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p12011toggle.startAnimation(scaleUp2)
                val myString = "2011_p1_Alevel_selected.pdf"
                i20111.putExtra("pdfName", myString)
                startActivity(i20111)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p12011toggle.startAnimation(scaleDown2)
            }
            true
        }

        val p12010toggle = findViewById<TextView>(R.id.p12010toggle)
        val i20101 = Intent(this, pdfDisplayer::class.java)
        p12010toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p12010toggle.startAnimation(scaleUp2)
                val myString = "2010_p1_Alevel_selected.pdf"
                i20101.putExtra("pdfName", myString)
                startActivity(i20101)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p12010toggle.startAnimation(scaleDown2)
            }
            true
        }

        val p22010toggle = findViewById<TextView>(R.id.p22010toggle)
        val i20102 = Intent(this, pdfDisplayer::class.java)
        p22010toggle.setOnTouchListener { v: View?, event: MotionEvent ->
            val scaleUp2 = AnimationUtils.loadAnimation(
                this, R.anim.boozup2
            )
            val scaleDown2 = AnimationUtils.loadAnimation(this, R.anim.boozdown2)
            if (event.action == MotionEvent.ACTION_UP) {
                p22010toggle.startAnimation(scaleUp2)
                val myString = "2010_p2_Alevel_selected.pdf"
                i20102.putExtra("pdfName", myString)
                startActivity(i20102)

                //  startActivity(i);
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                p22010toggle.startAnimation(scaleDown2)
            }
            true
        }
    }
}