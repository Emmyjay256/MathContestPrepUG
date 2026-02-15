package com.mathy.contestprep

import android.app.Activity
import android.content.Context
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentForm.OnConsentFormDismissedListener
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentInformation.PrivacyOptionsRequirementStatus
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.FormError
import com.google.android.ump.UserMessagingPlatform

/**
 * The Google Mobile Ads SDK provides the User Messaging Platform (Google's
 * IAB Certified consent management platform) as one solution to capture
 * consent for users in GDPR impacted countries. This is an example and
 * you can choose another consent management platform to capture consent.
 */
class GoogleMobileAdsConsentManager private constructor(context: Context) {
    private val consentInformation: ConsentInformation =
        UserMessagingPlatform.getConsentInformation(context)

    /** Interface definition for a callback to be invoked when consent gathering is complete.  */
    interface OnConsentGatheringCompleteListener {
        fun consentGatheringComplete(error: FormError?)
    }

    /** Helper variable to determine if the app can request ads.  */
    fun canRequestAds(): Boolean {
        return consentInformation.canRequestAds()
    }

    val isPrivacyOptionsRequired: Boolean
        /** Helper variable to determine if the privacy options form is required.  */
        get() = (consentInformation.privacyOptionsRequirementStatus
                == PrivacyOptionsRequirementStatus.REQUIRED)

    /**
     * Helper method to call the UMP SDK methods to request consent information and load/present a
     * consent form if necessary.
     */
    fun gatherConsent(
        activity: Activity?, onConsentGatheringCompleteListener: (FormError?) -> Unit
    ) {
        // For testing purposes, you can force a DebugGeography of EEA or NOT_EEA.
        val debugSettings =
            ConsentDebugSettings.Builder(activity) // .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
                // Check your logcat output for the hashed device ID e.g.
                // "Use new ConsentDebugSettings.Builder().addTestDeviceHashedId("ABCDEF012345")" to use
                // the debug functionality.
                .addTestDeviceHashedId("TEST-DEVICE-HASHED-ID")
                .build()

        val params = ConsentRequestParameters.Builder()
            .setConsentDebugSettings(debugSettings)
            .build()

        // Requesting an update to consent information should be called on every app launch.
        consentInformation.requestConsentInfoUpdate(
            activity,
            params,
            {
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                    activity
                ) { formError: FormError? ->
                    // Consent has been gathered.
                    onConsentGatheringCompleteListener.invoke(formError)
                }
            },
            { requestConsentError: FormError? ->
                onConsentGatheringCompleteListener.invoke(
                    requestConsentError
                )
            }
        )
    }

    /** Helper method to call the UMP SDK method to present the privacy options form.  */
    fun showPrivacyOptionsForm(
        activity: Activity?,
        onConsentFormDismissedListener: OnConsentFormDismissedListener?
    ) {
        UserMessagingPlatform.showPrivacyOptionsForm(activity, onConsentFormDismissedListener)
    }

    companion object {
        private var instance: GoogleMobileAdsConsentManager? = null

        /** Public constructor.  */
        fun getInstance(context: Context): GoogleMobileAdsConsentManager? {
            if (instance == null) {
                instance = GoogleMobileAdsConsentManager(context)
            }

            return instance
        }
    }
}