import java.io.FileInputStream
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.gms.google.services)
    //id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
}

val localProperties = Properties()
localProperties.load(FileInputStream(rootProject.file("local.properties")))

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "com.test.test"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.test.test"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        buildConfigField(
            "String",
            "SIGNALING_SERVER_IP_ADDRESS",
            localProperties["SIGNALING_SERVER_IP_ADDRESS"].toString()
        )

        val notificationEmulatorServer: String = localProperties["NOTIFICATION_SERVER_EMULATOR"].toString()
        val notificationRealDeviceServer: String = localProperties["NOTIFICATION_SERVER_REAL_DEVICE"].toString()
        val notificationServerPort: String = localProperties["NOTIFICATION_SERVER_PORT"].toString()

        val notificationEmulatorServerUrl = """"${notificationEmulatorServer.removeSurrounding("\"")}:${notificationServerPort.removeSurrounding("\"")}""""
        val notificationRealDeviceServerUrl = """"${notificationRealDeviceServer.removeSurrounding("\"")}:${notificationServerPort.removeSurrounding("\"")}""""

        buildConfigField(
            "String",
            "NOTIFICATION_SERVER_EMULATOR",
            notificationEmulatorServerUrl,
        )

        buildConfigField(
            "String",
            "NOTIFICATION_SERVER_REAL_DEVICE",
            notificationRealDeviceServerUrl,
        )


    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
//    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.camera.extensions)
    implementation(libs.profileinstaller)

    implementation(libs.glance.appwidget)
    implementation(libs.glance.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    baselineProfile(project(":baselineprofile"))

    implementation(libs.glance.appwidget)
    implementation(libs.glance.material)

    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.text.google.fonts)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.material3.adaptive)
    implementation(libs.compose.material.icons)
    androidTestImplementation(libs.compose.ui.test)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manisfest)

    implementation(libs.activity.compose)
    implementation(libs.navigation.compose)

    implementation(libs.accompanist.painter)
    implementation(libs.accompanist.permissions)

    implementation(libs.graphics.shapes)

    implementation(libs.lifecycle.ktx)
    implementation(libs.lifecycle.compose)
    implementation(libs.lifecycle.runtime.compose)

    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    androidTestImplementation(libs.room.testing)

   // implementation(libs.splashscreen)
    implementation(libs.concurrent.kts)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.camera.core)
    implementation(libs.camera2)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)
    implementation(libs.camera.viewfinder.compose)

    implementation(libs.media3.common)
    implementation(libs.media3.effect)
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.transformer)
    implementation(libs.media3.ui)

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    implementation(libs.window)

    androidTestImplementation(libs.turbine)

    // For photopicker feature
    implementation(libs.activity)

    implementation(libs.coil)
    implementation(libs.coil.compose)

    implementation(libs.webrtc)
    implementation(libs.retrofit)


    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-perf-ktx")
    implementation("com.google.firebase:firebase-config-ktx")

    implementation("com.google.firebase:firebase-auth-ktx")

    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.android.ump:user-messaging-platform:2.1.0")
    implementation("androidx.compose.material3:material3-window-size-class:1.2.1")


    //implementation("com.squareup.moshi:moshi:1.12.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")


    implementation("com.google.android.play:review:2.0.1")
}
