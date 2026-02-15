package com.mathy.contestprep

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.mathy.contestprep.R
import com.mathy.contestprep.databinding.ActivityMain2Binding
import java.util.Locale

class MainActivity2 : AppCompatActivity() {
    private var mLevel = 0
    private var mNextLevelButton: Button? = null
    private var mInterstitialAd: InterstitialAd? = null
    private var mLevelTextView: TextView? = null
    private var binding: ActivityMain2Binding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding!!.root)

        MobileAds.initialize(this) { }

        // Load the InterstitialAd and set the adUnitId (defined in values/strings.xml).
        loadInterstitialAd()

        // Create the next level button, which tries to show an interstitial when clicked.
        mNextLevelButton = binding!!.nextLevelButton
        mNextLevelButton!!.isEnabled = false
        mNextLevelButton!!.setOnClickListener { showInterstitial() }

        // Create the text view to show the level number.
        mLevelTextView = binding!!.level
        mLevel = MainActivity2.Companion.START_LEVEL

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
        Toast.makeText(this, MainActivity2.Companion.TOAST_TEXT, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this, getString(R.string.interstitial_ad_unit_id), adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd
                    mNextLevelButton!!.isEnabled = true

                    Toast.makeText(this@MainActivity2, "onAdLoaded()", Toast.LENGTH_SHORT).show()
                    interstitialAd.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                // Called when fullscreen content is dismissed.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                mInterstitialAd = null
                                Log.d(MainActivity2.Companion.TAG, "The ad was dismissed.")
                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                // Called when fullscreen content failed to show.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                mInterstitialAd = null
                                Log.d(MainActivity2.Companion.TAG, "The ad failed to show.")
                            }

                            override fun onAdShowedFullScreenContent() {
                                // Called when fullscreen content is shown.
                                Log.d(MainActivity2.Companion.TAG, "The ad was shown.")
                            }
                        }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    Log.i(MainActivity2.Companion.TAG, loadAdError.message)
                    mInterstitialAd = null
                    mNextLevelButton!!.isEnabled = true

                    val error = String.format(
                        Locale.ENGLISH,
                        "domain: %s, code: %d, message: %s",
                        loadAdError.domain,
                        loadAdError.code,
                        loadAdError.message
                    )
                    Toast.makeText(
                        this@MainActivity2,
                        "onAdFailedToLoad() with error: $error", Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })
    }

    private fun showInterstitial() {
        // Show the ad if it"s ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null) {
            mInterstitialAd!!.show(this)
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show()
            goToNextLevel()
        }
    }

    private fun goToNextLevel() {
        // Show the next level and reload the ad to prepare for the level after.
        mLevelTextView!!.text = "Level " + (++mLevel)
        if (mInterstitialAd == null) {
            loadInterstitialAd()
        }
    }

    companion object {
        // Remove the below line after defining your own ad unit ID.
        private const val TOAST_TEXT = ("Test ads are being shown. "
                + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID.")
        private const val TAG = "MainActivity2"

        private const val START_LEVEL = 1
    }
}