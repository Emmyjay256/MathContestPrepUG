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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.Button
import android.view.ViewGroup

class olevelQuester : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_olevel_quester)

        // Setup Insets & Ads
        setupSystemUI()

        // 1. Define the mapping with Clean Display Names
        val paperList = listOf(
            0 to PaperInfo("2025_p1_Olevel_selected.pdf", "2025 Paper 1 O-Level"),
            0 to PaperInfo("2025_p2_Olevel_selected.pdf", "2025 Paper 2 O-Level"),
            0 to PaperInfo("2024_p1_Olevel_selected.pdf", "2024 Paper 1 O-Level"),
            0 to PaperInfo("2024_p2_Olevel_selected.pdf", "2024 Paper 2 O-Level"),
            0 to PaperInfo("2023_p1_Olevel_selected.pdf", "2023 Paper 1 O-Level"),
            0 to PaperInfo("2023_p2_Olevel_selected.pdf", "2023 Paper 2 O-Level"),
            0 to PaperInfo("2022_solo_Olevel_selected.pdf", "2022 Solo Paper O-Level"),
            0 to PaperInfo("2019_p1_Olevel_selected.pdf", "2019 Paper 1 O-Level"),
            0 to PaperInfo("2019_p2_Olevel_selected.pdf", "2019 Paper 2 O-Level"),
            0 to PaperInfo("2018_p1_Olevel_selected.pdf", "2018 Paper 1 O-Level"),
            0 to PaperInfo("2018_p2_Olevel_selected.pdf", "2018 Paper 2 O-Level"),
            0 to PaperInfo("2017_p1_Olevel_selected.pdf", "2017 Paper 1 O-Level"),
            0 to PaperInfo("2017_p2_Olevel_selected.pdf", "2017 Paper 2 O-Level"),
            0 to PaperInfo("2016_p1_Olevel_selected.pdf", "2016 Paper 1 O-Level"),
            0 to PaperInfo("2016_p2_Olevel_selected.pdf", "2016 Paper 2 O-Level"),
            0 to PaperInfo("2015_p1_Olevel_selected.pdf", "2015 Paper 1 O-Level"),
            0 to PaperInfo("2015_p2_Olevel_selected.pdf", "2015 Paper 2 O-Level"),
            0 to PaperInfo("2014_p1_Olevel_selected.pdf", "2014 Paper 1 O-Level"),
            0 to PaperInfo("2014_p2_Olevel_selected.pdf", "2014 Paper 2 O-Level"),
            0 to PaperInfo("2013_p1_Olevel_selected.pdf", "2013 Paper 1 O-Level"),
            0 to PaperInfo("2013_p2_Olevel_selected.pdf", "2013 Paper 2 O-Level"),
            0 to PaperInfo("2012_p1_Olevel_selected.pdf", "2012 Paper 1 O-Level"),
            0 to PaperInfo("2012_p2_Olevel_selected.pdf", "2012 Paper 2 O-Level"),
            0 to PaperInfo("2011_p1_Olevel_selected.pdf", "2011 Paper 1 O-Level"),
            0 to PaperInfo("2011_p2_Olevel_selected.pdf", "2011 Paper 2 O-Level"),
            0 to PaperInfo("2010_p1_Olevel_selected.pdf", "2010 Paper 1 O-Level"),
            0 to PaperInfo("2010_p2_Olevel_selected.pdf", "2010 Paper 2 O-Level")
        )



        // 2. Setup the RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.papersRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PaperAdapter(paperList) { info ->
            // This handles the click for EVERY button in the list
            val intent = Intent(this, pdfDisplayer::class.java).apply {
                putExtra("pdfName", info.fileName)
                putExtra("displayName", info.displayName)
            }
            startActivity(intent)
        }


        // Backer logic
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
