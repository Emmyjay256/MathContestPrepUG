package com.mathy.contestprep

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class pdfDisplayer : AppCompatActivity() {

    private var pdfView: PDFView? = null
    private lateinit var dbHandler: DBhandler
    private var currentPdfName: String = ""
    private lateinit var btnBookmark: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.p2022_layout)

        dbHandler = DBhandler(this)
        btnBookmark = findViewById(R.id.btnBookmark)

        val topBar = findViewById<LinearLayout>(R.id.top_bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            topBar.setPadding(topBar.paddingLeft, systemBars.top, topBar.paddingRight, topBar.paddingBottom)
            v.setPadding(0, 0, 0, systemBars.bottom)
            insets
        }

        MobileAds.initialize(this) {}
        val adView = findViewById<AdView>(R.id.adView)
        adView.loadAd(AdRequest.Builder().build())

        val linear1 = findViewById<LinearLayout>(R.id.linear1)
        pdfView = PDFView(this, null)
        linear1.addView(pdfView, LinearLayout.LayoutParams(-1, -1))

        currentPdfName = intent.getStringExtra("pdfName") ?: "Unknown"
        findViewById<TextView>(R.id.pdfTitleText).text = currentPdfName

        loadPdf(currentPdfName)

        btnBookmark.setOnClickListener {
            val currentPage = pdfView?.currentPage ?: 0
            if (!dbHandler.isBookmarked(currentPdfName, currentPage)) {
                dbHandler.addBookmark(currentPdfName, currentPage, "Saved: $currentPdfName P.${currentPage + 1}")
                updateBookmarkIcon(true)
                showMessage("Bookmark saved!")
            } else {
                showMessage("Already bookmarked!")
            }
        }
    }

    private fun loadPdf(pdfName: String) {
        try {
            pdfView?.fromAsset(pdfName)
                ?.defaultPage(0)
                ?.enableSwipe(true)
                ?.swipeHorizontal(false)
                ?.onPageChange { page, _ ->
                    // Every time user scrolls, check the DB
                    val alreadySaved = dbHandler.isBookmarked(currentPdfName, page)
                    updateBookmarkIcon(alreadySaved)
                }
                ?.autoSpacing(true)
                ?.pageFitPolicy(FitPolicy.WIDTH)
                ?.scrollHandle(DefaultScrollHandle(this))
                ?.load()
        } catch (e: Exception) {
            showMessage("Error loading PDF")
        }
    }

    private fun updateBookmarkIcon(isBookmarked: Boolean) {
        if (isBookmarked) {
            btnBookmark.setColorFilter(Color.RED)
            btnBookmark.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            btnBookmark.setColorFilter(Color.WHITE)
            btnBookmark.setImageResource(android.R.drawable.btn_star_big_off)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        pdfView?.recycle()
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}