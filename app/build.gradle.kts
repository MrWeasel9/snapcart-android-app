// app/build.gradle.kts
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.snapcart_android_app"
    compileSdk = 34

    defaultConfig {

        applicationId = "com.example.snapcart_android_app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "META-INF/AL2.0"
            excludes += "META-INF/LGPL2.1"
        }
    }
}


dependencies {

    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.coil.compose)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.androidx.lifecycle.runtime.ktx.v287)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.firebase.appcheck.ktx)
    implementation(libs.firebase.appcheck.playintegrity)
    testImplementation(libs.junit.junit)
    releaseImplementation(libs.glide)
    implementation(libs.gson)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.foundation)
    implementation(libs.ui.tooling)
    implementation(libs.material3) // Adjust version as needed
    implementation(libs.androidx.material)
    implementation(libs.coil.compose.v240)


    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.koin.android)
    implementation(libs.koin.android.compose)
    implementation(libs.compose.navigation)

    implementation(libs.firebase.auth)
    implementation (libs.firebase.appcheck.playintegrity.v1601)
    implementation(libs.firebase.database)
    implementation(libs.material.v161)
    implementation (platform(libs.firebase.bom))
    implementation (libs.play.services.auth)
    implementation (libs.google.firebase.firestore.ktx)
    implementation (libs.androidx.material.icons.extended)
    implementation (libs.androidx.animation)
    implementation (libs.firebase.messaging.ktx)

    testImplementation (libs.mockito.kotlin)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation (libs.turbine )// For Flow testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    testImplementation (libs.mockito.core)
    testImplementation (libs.kotlinx.coroutines.test.v152)

    testImplementation (libs.mockk.mockk)
    testImplementation (libs.turbine.v100)
    testImplementation(libs.slf4j.simple)
    testImplementation(libs.byte.buddy)
}

apply(plugin = "com.google.gms.google-services")

// In app/build.gradle
tasks.withType<Test> {
    jvmArgs = listOf(
        "-XX:+EnableDynamicAgentLoading",
        "-Djdk.instrument.traceUsage=false"
    )
}