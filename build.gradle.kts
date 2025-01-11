import com.diffplug.gradle.spotless.SpotlessExtension

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidTest) apply false
    alias(libs.plugins.baselineprofile) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false

    id("com.google.firebase.crashlytics") version "2.9.9" apply false
    id("com.google.firebase.firebase-perf") version "1.4.2" apply false
}

subprojects {
    plugins.apply(rootProject.libs.plugins.spotless.get().pluginId)
    configure<SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("**/camera/viewfinder/**")
            ktlint(libs.ktlint.get().version)
        }
        kotlinGradle {
            ktlint(libs.ktlint.get().version)
        }
    }
}
