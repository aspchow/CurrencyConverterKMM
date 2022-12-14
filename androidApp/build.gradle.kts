import org.jetbrains.kotlin.config.JvmAnalysisFlags.useIR

plugins {
    id("com.android.application")
    kotlin("android")
}


android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.avinash.currencyconverterkmm.android"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }


    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }


}

dependencies {

    implementation(project(":shared"))

    implementation("androidx.compose.ui:ui:1.2.1")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:1.2.1")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation( "androidx.compose.foundation:foundation:1.2.1")
    // Material Design
    implementation ("androidx.compose.material:material:1.2.1")

    implementation ("androidx.activity:activity-compose:1.5.1")

    implementation ("androidx.navigation:navigation-compose:2.5.1")


    //koin
    val koinVersion = "3.2.0"
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")



    // Integration with observables
    implementation ("androidx.compose.runtime:runtime-livedata:1.2.1")

    implementation("androidx.activity:activity-ktx:1.5.1")
    implementation("com.google.android.material:material:1.4.0")

}