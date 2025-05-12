plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.osum.axedroid"
    compileSdk = 34
    buildFeatures.dataBinding = true
    defaultConfig {
        applicationId = "com.osum.axedroid"
        minSdk = 24
        targetSdk = 34
        versionCode = 2
        versionName = "v0."

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        versionNameSuffix = "2"
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
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation (libs.retrofit)
    implementation (libs.converter.jackson)
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)
    implementation(libs.legacy.support.v4)
    implementation (libs.dagger.hilt.android)
    annotationProcessor (libs.dagger.hilt.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}