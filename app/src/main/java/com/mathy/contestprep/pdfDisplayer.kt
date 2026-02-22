package com.mathy.contestprep

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.io.File
import java.io.FileOutputStream

class pdfDisplayer : AppCompatActivity() {

    private var pdfView: PDFView? = null
    private lateinit var dbHandler: DBhandler
    private var currentPdfName: String = ""
    private lateinit var btnBookmark: ImageView
    private lateinit var btnShare: ImageView // Added this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.p2022_layout)

        // 1. Initialize Handlers and UI Buttons
        dbHandler = DBhandler(this)
        btnBookmark = findViewById(R.id.btnBookmark)
        btnShare = findViewById(R.id.btnShare)

        // 2. Handle System Insets (Edge-to-Edge)
        val topBar = findViewById<LinearLayout>(R.id.top_bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            topBar.setPadding(topBar.paddingLeft, systemBars.top, topBar.paddingRight, topBar.paddingBottom)
            v.setPadding(0, 0, 0, systemBars.bottom)
            insets
        }

        // 3. Initialize Ads
        MobileAds.initialize(this) {}
        val adView = findViewById<AdView>(R.id.adView)
        adView.loadAd(AdRequest.Builder().build())

        // 4. Setup PDF View Programmatically
        val linear1 = findViewById<LinearLayout>(R.id.linear1)
        pdfView = PDFView(this, null)
        linear1.addView(pdfView, LinearLayout.LayoutParams(-1, -1))

        // 5. Extract Intent Data (The "Nasty" name for logic, "Clean" name for UI)
        currentPdfName = intent.getStringExtra("pdfName") ?: "Unknown"
        val displayTitle = intent.getStringExtra("displayName") ?: currentPdfName

        // Update the Top Bar with the clean display name
        findViewById<TextView>(R.id.pdfTitleText).text = displayTitle

        // Load the actual file
        loadPdf(currentPdfName)

        // 6. Bookmark Click Listener (Uses Clean Name for the DB Description)
        btnBookmark.setOnClickListener {
            val currentPage = pdfView?.currentPage ?: 0
            if (!dbHandler.isBookmarked(currentPdfName, currentPage)) {
                // Saving with displayTitle makes the "Saved Bookmarks" list look much better later
                dbHandler.addBookmark(currentPdfName, currentPage, "$displayTitle - Page ${currentPage + 1}")
                updateBookmarkIcon(true)
                showMessage("Bookmark saved!")
            } else {
                showMessage("Already bookmarked!")
            }
        }

        // 7. Share Click Listener
        btnShare.setOnClickListener {
            shareCurrentView()
        }
    }

    private fun loadPdf(pdfName: String) {
        try {
            pdfView?.fromAsset(pdfName)
                ?.defaultPage(0)
                ?.enableSwipe(true)
                ?.swipeHorizontal(false)
                ?.onPageChange { page, _ ->
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

    private fun shareCurrentView() {
        val view = pdfView ?: return

        // Use a background thread or immediate capture
        try {
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            view.draw(canvas)

            val cachePath = File(cacheDir, "shared_images")
            cachePath.mkdirs()
            val file = File(cachePath, "math_problem.png")
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.close()

            shareImageFile(file)
        } catch (e: Exception) {
            showMessage("Failed to capture screenshot")
        }
    }

    private fun shareImageFile(file: File) {
        val contentUri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", file)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, contentUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(intent, "Share Problem with Peers"))
    }

    override fun onDestroy() {
        super.onDestroy()
        pdfView?.recycle()
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}