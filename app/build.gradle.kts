// C:/Users/Emma/AndroidStudioProjects/MathContestUgPrep/app/build.gradle.kts
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")

    // Add this line with Kotlin version 2.0.0
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
}

android {
    namespace = "com.mathy.contestprep"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mathy.contestprep"
        minSdk = 24
        targetSdk = 34
        versionCode = 5
        versionName = "1.1.25"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }
}

dependencies {
// Firebase BoM - This manages versions for *Firebase* components!
    implementation(platform("com.google.firebase:firebase-bom:33.13.0"))

    // Declare Firebase Analytics (version managed by BOM)
    implementation("com.google.firebase:firebase-analytics")

    // play-services-ads is NOT covered by the Firebase BOM, so it needs an explicit version.
    implementation("com.google.android.gms:play-services-ads:23.0.0") // <-- ADD THE VERSION BACK HERE

    // The measurement components *are* pulled in by firebase-analytics and are generally covered by the BOM.
    // Explicitly listing them here (without versions) is good practice to ensure BOM control,
    // especially if you had previous version conflicts.
    implementation("com.google.android.gms:play-services-measurement-api")
    implementation("com.google.android.gms:play-services-measurement")
    implementation("com.google.android.gms:play-services-measurement-base")
    implementation("com.google.android.gms:play-services-measurement-impl")
    implementation("com.google.android.gms:play-services-measurement-sdk-api")


// AndroidX Core & UI
    implementation("androidx.core:core:1.13.1")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation("androidx.activity:activity-ktx:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")

// PDF Viewer - Already has exclusion rules
    implementation("com.github.mhiew:android-pdf-viewer:3.2.0-beta.1") {
        exclude(group = "com.android.support")
        exclude(group = "androidx.legacy")
    }
    implementation(libs.activity)

// Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

// Compose Dependencies
    implementation("androidx.compose.ui:ui:1.5.2")
    implementation("androidx.compose.material:material:1.5.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.2")
    implementation("androidx.activity:activity-compose:1.9.0")
    // Firebase Auth
    implementation("com.google.firebase:firebase-auth-ktx")

    // Google Sign-In library
    implementation("com.google.android.gms:play-services-auth:21.2.0")
// Add the library for the Cloud Firestore database
    implementation("com.google.firebase:firebase-firestore-ktx")
}