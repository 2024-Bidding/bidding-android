import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.org.jetbrains.kotlin.serialization)
    alias(libs.plugins.ktlint.gradle)
}

val properties = Properties()
properties.load(rootProject.file("./local.properties").inputStream())

android {
    namespace = "com.seunghoon.bidding_android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.seunghoon.bidding_android"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = properties.getProperty("BASE_URL", "\"\""),
            )
            buildConfigField(
                type = "String",
                name = "FILE_BASE_URL",
                value = properties.getProperty("FILE_BASE_URL", "\"\""),
            )
        }
        debug {
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = properties.getProperty("BASE_URL", "\"\""),
            )
            buildConfigField(
                type = "String",
                name = "FILE_BASE_URL",
                value = properties.getProperty("FILE_BASE_URL", "\"\""),
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
        dataBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Navigation
    api(libs.androidx.navigation.fragment.ktx)
    api(libs.androidx.navigation.ui.ktx)

    // Koin
    api(libs.io.insert.koin)

    // Ktor
    api(libs.io.ktor.android)
    api(libs.io.ktor.serialization)
    api(libs.io.ktor.serialization.json)
    api(libs.io.ktor.logging)
    api(libs.io.ktor.content.negotiation)

    // Glide
    api(libs.com.github.glide)
}
