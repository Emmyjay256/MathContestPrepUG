package com.mathy.contestprep

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class pdfDisplayer : AppCompatActivity() {

    private var linear1: LinearLayout? = null
    private var pdfView: PDFView? = null
    private var pageCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.p2022_layout)

        // Edge-to-edge inset handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // AdMob
        MobileAds.initialize(this) {}
        val adView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        linear1 = findViewById(R.id.linear1)

        // Create PDFView programmatically
        pdfView = PDFView(this, null)
        linear1!!.addView(
            pdfView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )

        // Get PDF name from intent extras (asset file name)
        val extras = intent.extras ?: return showMessage("No PDF specified")
        val pdfName = extras.getString("pdfName") ?: return showMessage("No PDF specified")

        // Load PDF directly from assets (no manual copy)
        try {
            pdfView?.fromAsset(pdfName)
                ?.defaultPage(0)
                ?.enableSwipe(true)                      // allows scrolling
                ?.swipeHorizontal(false)                 // vertical scrolling
                ?.enableDoubletap(true)                  // zoom with double-tap
                ?.enableAnnotationRendering(true)        // render annotations if present
                ?.autoSpacing(true)                      // add dynamic spacing to fit each page
                ?.spacing(4)                             // spacing between pages in dp
                ?.pageFitPolicy(FitPolicy.WIDTH)         // fit pages to width
                ?.fitEachPage(false)                     // don't fit each page individually
                ?.pageSnap(false)                        // don't snap pages (smooth scroll)
                ?.pageFling(false)                       // fling won't change full pages
                ?.scrollHandle(DefaultScrollHandle(this))// show a scroll handle on the side
                // ?.nightMode(true)                     // uncomment to enable night mode
                ?.onLoad(object : OnLoadCompleteListener {
                    override fun loadComplete(nbPages: Int) {
                        pageCount = nbPages
                        // you can show a page indicator / update UI here if required
                    }
                })
                ?.load()

        } catch (e: Exception) {
            showMessage("Load error: ${e.message}")
            Log.e("pdfDisplayer", "PDF load error", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            pdfView?.recycle()
        } catch (e: Exception) {
            // ignore
        }
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
