package com.mathy.contestprep

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnTouchListener
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.VideoController.VideoLifecycleCallbacks
import com.google.android.gms.ads.VideoOptions
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.android.ump.FormError
import com.mathy.contestprep.termsOfUse
import java.util.Locale
import java.util.concurrent.atomic.AtomicBoolean

@SuppressLint("SetTextI18n")
class appInfo : AppCompatActivity() {
    private val isMobileAdsInitializeCalled = AtomicBoolean(false)
    private var googleMobileAdsConsentManager: GoogleMobileAdsConsentManager? = null

    // private Button refresh;
    private val startVideoAdsMuted: CheckBox? = null
    private val videoStatus: TextView? = null
    private var nativeAd: NativeAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_app_info)
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


        val privacyPolicy2 = findViewById<TextView>(R.id.privacyPolicy)
        val ii = Intent(this, privacyPolicy::class.java)
        privacyPolicy2.setOnTouchListener(object : OnTouchListener {
            var scaleUp: Animation = AnimationUtils.loadAnimation(this@appInfo, R.anim.boozup)
            var scaleDown: Animation = AnimationUtils.loadAnimation(this@appInfo, R.anim.boozdown)
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_UP) {
                    v.startAnimation(scaleUp)
                    privacyPolicy2.performClick()
                   // startActivity(ii)
                } else if (event.action == MotionEvent.ACTION_DOWN) {
                    v.startAnimation(scaleDown)
                }
                return true
            }
        })
            privacyPolicy2.setOnClickListener(View.OnClickListener {
                startActivity(ii)
            })










        val testing = findViewById<TextView>(R.id.testingTeam)
        val iit = Intent(this, termsOfUse::class.java)

        testing.setOnTouchListener(object : OnTouchListener {
            var scaleUp: Animation = AnimationUtils.loadAnimation(this@appInfo, R.anim.boozup)
            var scaleDown: Animation = AnimationUtils.loadAnimation(this@appInfo, R.anim.boozdown)
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_UP) {
                    testing.startAnimation(scaleUp)
                    testing.performClick()
                 //   startActivity(iit)
                } else if (event.action == MotionEvent.ACTION_DOWN) {
                    testing.startAnimation(scaleDown)
                }
                return true
            }
        })
            testing.setOnClickListener(View.OnClickListener {
                startActivity(iit)
            })










        val donate2 = findViewById<TextView>(R.id.donate)
        val iid = Intent(this, donate::class.java)
        donate2.setOnTouchListener(object : OnTouchListener {

            var scaleUp: Animation = AnimationUtils.loadAnimation(this@appInfo, R.anim.boozup)
            var scaleDown: Animation = AnimationUtils.loadAnimation(this@appInfo, R.anim.boozdown)
            override fun onTouch(v: View, event: MotionEvent): Boolean {


                if (event.action == MotionEvent.ACTION_UP) {
                    donate2.startAnimation(scaleUp)
                    donate2.performClick()
                  //  startActivity(iid)
                } else if (event.action == MotionEvent.ACTION_DOWN) {
                    donate2.startAnimation(scaleDown)
                   // startActivity(iid)
                }
                return true
            }
        })
        donate2.setOnClickListener(View.OnClickListener {
            startActivity(iid)
        })










        val aboutUs2 = findViewById<TextView>(R.id.aboutUs)
        val iia = Intent(this, aboutUs::class.java)
        aboutUs2.setOnTouchListener(object : OnTouchListener {
            var scaleUp: Animation = AnimationUtils.loadAnimation(this@appInfo, R.anim.boozup)
            var scaleDown: Animation = AnimationUtils.loadAnimation(this@appInfo, R.anim.boozdown)
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_UP) {
                    aboutUs2.startAnimation(scaleUp)
                    aboutUs2.performClick()
                   // startActivity(iia)
                } else if (event.action == MotionEvent.ACTION_DOWN) {
                    aboutUs2.startAnimation(scaleDown)
                }
                return true
            }
        })
        aboutUs2.setOnClickListener(View.OnClickListener {
            startActivity(iia)
        })






    }


    //THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS
//THESE FUNCTIONS BELOW ARE ANNOYED DON'T TOUCH THEM. THEY WANNA SHOW EVERYONE ADS AND WE HATE ADS


    /**
     * Populates a [NativeAdView] object with data from a given [NativeAd].
     *
     * @param nativeAd the object containing the ad's assets
     * @param adView the view to be populated
     */
    private fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
        // Set the media view.
        adView.mediaView = adView.findViewById<View>(R.id.ad_media) as MediaView

        // Set other ad assets.
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)
        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

        // The headline and mediaContent are guaranteed to be in every NativeAd.
        (adView.headlineView as TextView?)!!.text = nativeAd.headline
        adView.mediaView!!.mediaContent = nativeAd.mediaContent

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            adView.bodyView!!.visibility = View.INVISIBLE
        } else {
            adView.bodyView!!.visibility = View.VISIBLE
            (adView.bodyView as TextView?)!!.text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            adView.callToActionView!!.visibility = View.INVISIBLE
        } else {
            adView.callToActionView!!.visibility = View.VISIBLE
            (adView.callToActionView as Button?)!!.text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            adView.iconView!!.visibility = View.GONE
        } else {
            (adView.iconView as ImageView?)!!.setImageDrawable(
                nativeAd.icon!!.drawable
            )
            adView.iconView!!.visibility = View.VISIBLE
        }

        if (nativeAd.price == null) {
            adView.priceView!!.visibility = View.INVISIBLE
        } else {
            adView.priceView!!.visibility = View.VISIBLE
            (adView.priceView as TextView?)!!.text = nativeAd.price
        }

        if (nativeAd.store == null) {
            adView.storeView!!.visibility = View.INVISIBLE
        } else {
            adView.storeView!!.visibility = View.VISIBLE
            (adView.storeView as TextView?)!!.text = nativeAd.store
        }

        if (nativeAd.starRating == null) {
            adView.starRatingView!!.visibility = View.INVISIBLE
        } else {
            (adView.starRatingView as RatingBar?)?.setRating(nativeAd.starRating!!.toFloat())
            adView.starRatingView!!.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            adView.advertiserView!!.visibility = View.INVISIBLE
        } else {
            (adView.advertiserView as TextView?)!!.text = nativeAd.advertiser
            adView.advertiserView!!.visibility = View.VISIBLE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd)

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        val vc = nativeAd.mediaContent!!.videoController

        // Updates the UI to say whether or not this ad has a video asset.
        if (nativeAd.mediaContent != null && nativeAd.mediaContent!!.hasVideoContent()) {
            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.

            vc.videoLifecycleCallbacks = object : VideoLifecycleCallbacks() {
                override fun onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    //refresh.setEnabled(true);
                    // videoStatus.setText("Video status: Video playback has ended.");
                    super.onVideoEnd()
                }
            }
        } else {
            //  videoStatus.setText("Video status: Ad does not contain a video asset.");
//refresh.setEnabled(true);
        }
    }

    /**
     * Creates a request for a new native ad based on the boolean parameters and calls the
     * corresponding "populate" method when one is successfully returned.
     *
     */
    private fun refreshAd() {
        //  refresh.setEnabled(false);

        val builder = AdLoader.Builder(this, ADMOB_AD_UNIT_ID)

        builder.forNativeAd(
            OnNativeAdLoadedListener { nativeAd ->
                // OnLoadedListener implementation.
                // If this callback occurs after the activity is destroyed, you must call
                // destroy and return or you may get a memory leak.
                var isDestroyed = false


                // refresh.setEnabled(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    isDestroyed = isDestroyed()
                }
                if (isDestroyed || isFinishing || isChangingConfigurations) {
                    nativeAd.destroy()
                    return@OnNativeAdLoadedListener
                }
                // You must call destroy on old ads when you are done with them,
                // otherwise you will have a memory leak.
                if (this@appInfo.nativeAd != null) {
                    this@appInfo.nativeAd!!.destroy()
                }
                this@appInfo.nativeAd = nativeAd
                val frameLayout = findViewById<FrameLayout>(R.id.fl_adplaceholder)
                val adView =
                    layoutInflater.inflate(R.layout.ad_unified, frameLayout, false) as NativeAdView
                populateNativeAdView(nativeAd, adView)
                frameLayout.removeAllViews()
                frameLayout.addView(adView)
            })

        val videoOptions =
            VideoOptions.Builder().setStartMuted(true).build()

        val adOptions =
            NativeAdOptions.Builder().setVideoOptions(videoOptions).build()

        builder.withNativeAdOptions(adOptions)

        val adLoader =
            builder
                .withAdListener(
                    object : AdListener() {
                        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                            //refresh.setEnabled(true);
                            val error = String.format(
                                Locale.getDefault(),
                                "domain: %s, code: %d, message: %s",
                                loadAdError.domain,
                                loadAdError.code,
                                loadAdError.message
                            )
                          /*  Toast.makeText(
                                this@appInfo,
                                "Failed to load native ad with error $error",
                                Toast.LENGTH_SHORT
                            )
                                .show()*/
                        }
                    })
                .build()

        adLoader.loadAd(AdRequest.Builder().build())

        // videoStatus.setText("");
    }

    private fun initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.getAndSet(true)) {
            return
        }

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(
            this
        ) { // Load an ad.
            refreshAd()
        }
    }

    override fun onDestroy() {
        if (nativeAd != null) {
            nativeAd!!.destroy()
        }
        super.onDestroy()
    }

    companion object {
        private const val ADMOB_AD_UNIT_ID = "ca-app-pub-2478490471190854/6636334496"

        // ca-app-pub-3940256099942544/2247696110
        private const val TAG = "MainActivity"
    }
}