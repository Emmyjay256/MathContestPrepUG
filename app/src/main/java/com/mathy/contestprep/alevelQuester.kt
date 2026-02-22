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

// A simple data container for our paper details
data class PaperInfo(val fileName: String, val displayName: String)

class alevelQuester : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_alevel_quester)

        setupSystemUI()

        // 1. Upgraded Mapping (File Name + Clean Name)
        val paperMap = mapOf(
            R.id.p12024toggle to PaperInfo("2024_p1_Alevel_selected.pdf", "2024 Paper 1 A-Level"),
            R.id.p22024toggle to PaperInfo("2024_p2_Alevel_selected.pdf", "2024 Paper 2 A-Level"),
            R.id.p12023toggle to PaperInfo("2023_p1_Alevel_selected.pdf", "2023 Paper 1 A-Level"),
            R.id.p22023toggle to PaperInfo("2023_p2_Alevel_selected.pdf", "2023 Paper 2 A-Level"),
            R.id.p2022toggle to PaperInfo("2022_solo_Alevel_selected.pdf", "2022 Solo Paper A-Level"),
            R.id.p12019toggle to PaperInfo("2019_p1_Alevel_selected.pdf", "2019 Paper 1 A-Level"),
            R.id.p22019toggle to PaperInfo("2019_p2_Alevel_selected.pdf", "2019 Paper 2 A-Level"),
            R.id.p12018toggle to PaperInfo("2018_p1_Alevel_selected.pdf", "2018 Paper 1 A-Level"),
            R.id.p22018toggle to PaperInfo("2018_p2_Alevel_selected.pdf", "2018 Paper 2 A-Level"),
            R.id.p12017toggle to PaperInfo("2017_p1_Alevel_selected.pdf", "2017 Paper 1 A-Level"),
            R.id.p22017toggle to PaperInfo("2017_p2_Alevel_selected.pdf", "2017 Paper 2 A-Level"),
            R.id.p12016toggle to PaperInfo("2016_p1_Alevel_selected.pdf", "2016 Paper 1 A-Level"),
            R.id.p22016toggle to PaperInfo("2016_p2_Alevel_selected.pdf", "2016 Paper 2 A-Level"),
            R.id.p12015toggle to PaperInfo("2015_p1_Alevel_selected.pdf", "2015 Paper 1 A-Level"),
            R.id.p22015toggle to PaperInfo("2015_p2_Alevel_selected.pdf", "2015 Paper 2 A-Level"),
            R.id.p12014toggle to PaperInfo("2014_p1_Alevel_selected.pdf", "2014 Paper 1 A-Level"),
            R.id.p12013toggle to PaperInfo("2013_p1_Alevel_selected.pdf", "2013 Paper 1 A-Level"),
            R.id.p22013toggle to PaperInfo("2013_p2_Alevel_selected.pdf", "2013 Paper 2 A-Level"),
            R.id.p12012toggle to PaperInfo("2012_p1_Alevel_selected.pdf", "2012 Paper 1 A-Level"),
            R.id.p22012toggle to PaperInfo("2012_p2_Alevel_selected.pdf", "2012 Paper 2 A-Level"),
            R.id.p12011toggle to PaperInfo("2011_p1_Alevel_selected.pdf", "2011 Paper 1 A-Level"),
            R.id.p12010toggle to PaperInfo("2010_p1_Alevel_selected.pdf", "2010 Paper 1 A-Level"),
            R.id.p22010toggle to PaperInfo("2010_p2_Alevel_selected.pdf", "2010 Paper 2 A-Level")
        )

        paperMap.forEach { (viewId, info) ->
            findViewById<TextView>(viewId)?.let { textView ->
                attachPaperListener(textView, info)
            }
        }

        findViewById<ImageView>(R.id.backer).setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> v.background = resources.getDrawable(R.drawable.backerbg, theme)
                MotionEvent.ACTION_UP -> {
                    v.background = resources.getDrawable(R.drawable.baup, theme)
                    finish()
                }
            }
            true
        }
    }

    private fun attachPaperListener(view: TextView, info: PaperInfo) {
        val scaleUp = AnimationUtils.loadAnimation(this, R.anim.boozup2)
        val scaleDown = AnimationUtils.loadAnimation(this, R.anim.boozdown2)

        view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> v.startAnimation(scaleDown)
                MotionEvent.ACTION_UP -> {
                    v.startAnimation(scaleUp)
                    val intent = Intent(this, pdfDisplayer::class.java).apply {
                        putExtra("pdfName", info.fileName)
                        putExtra("displayName", info.displayName) // Passing the clean name
                    }
                    startActivity(intent)
                }
            }
            true
        }
    }

    private fun setupSystemUI() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.statusBarColor = resources.getColor(R.color.action, theme)
        }
        MobileAds.initialize(this) {}
        findViewById<AdView>(R.id.adView).loadAd(AdRequest.Builder().build())
    }
}