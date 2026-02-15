package com.mathy.contestprep

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.util.Locale

class donate : AppCompatActivity() {
    private val mLevel = 0
   // private lateinit var mNextLevelButton: Button
    private var mInterstitialAd: InterstitialAd? = null
    private val mLevelTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_donate)
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

        // Load the InterstitialAd and set the adUnitId (defined in values/strings.xml).

              var mNextLevelButton:Button = findViewById(R.id.button2);
        // Create the next level button, which tries to show an interstitial when clicked.
             //mNextLevelButton = findViewById(R.id.button2)
            mNextLevelButton.setEnabled(false)
            mNextLevelButton.setOnClickListener(View.OnClickListener { showInterstitial() })
        loadInterstitialAd(mNextLevelButton)
        // Create the text view to show the level number.
        // mLevelTextView = binding.level;
        //mLevel = START_LEVEL;

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
      //  Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show()
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
        /*Button donator = findViewById(R.id.button2);
        donator.setOnClickListener(v ->{
            Intent i = new Intent(this, MainActivity2.class);
            startActivity(i);
        });*/
    }

    fun loadInterstitialAd(mNextLevelButton:Button) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this, getString(R.string.interstitial_ad_unit_id), adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd
                    mNextLevelButton!!.isEnabled = true

                    Toast.makeText(this@donate, "AdLoaded", Toast.LENGTH_SHORT).show()
                    interstitialAd.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                // Called when fullscreen content is dismissed.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                mInterstitialAd = null
                                Log.d(TAG, "The ad was dismissed.")
                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                // Called when fullscreen content failed to show.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                mInterstitialAd = null
                                Log.d(TAG, "The ad failed to show.")
                            }

                            override fun onAdShowedFullScreenContent() {
                                // Called when fullscreen content is shown.
                                Log.d(TAG, "The ad was shown.")
                            }
                        }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    Log.i(TAG, loadAdError.message)
                    mInterstitialAd = null
                    mNextLevelButton!!.isEnabled = true

                    val error = String.format(
                        Locale.ENGLISH,
                        "domain: %s, code: %d, message: %s",
                        loadAdError.domain,
                        loadAdError.code,
                        loadAdError.message
                    )
                  /*  Toast.makeText(
                        this@donate,
                        "AdFailedToLoad() with error ", Toast.LENGTH_SHORT
                    )
                        .show()*/
                }
            })
    }

    private fun showInterstitial() {
        // Show the ad if it"s ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null) {
            mInterstitialAd!!.show(this)
        } else {
            Toast.makeText(this, "Ad not loaded", Toast.LENGTH_SHORT).show()
            goToNextLevel()
        }
    }

    private fun goToNextLevel() {
        // Show the next level and reload the ad to prepare for the level after.
        //  mLevelTextView.setText("Level " + (++mLevel));
        if (mInterstitialAd == null) {
           // loadInterstitialAd()
        }
    }

    companion object {
        private const val TOAST_TEXT = ("Donation ad being loaded. "
                + "Wait a bit!!!")
        private const val TAG = "MainActivity2"

        private const val START_LEVEL = 1
    }
}