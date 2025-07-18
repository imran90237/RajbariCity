plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.rajbaricity"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.rajbaricity"
        minSdk = 24
        targetSdk = 35
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
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation ("com.google.android.exoplayer:exoplayer:2.18.5")

    // Compose BOM
    implementation(platform(libs.androidx.compose.bom))

    // Core libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose UI
    implementation("androidx.compose.foundation:foundation:1.4.0")
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation(libs.androidx.recyclerview)

    implementation ("androidx.compose.material3:material3:1.1.2" )// or latest
    implementation( "androidx.compose.material:material-icons-extended:1.6.0")
    implementation(libs.androidx.navigation.runtime.android) // for full icon support

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug tools
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

implementation ("androidx.media3:media3-exoplayer:1.3.0")
implementation ("androidx.media3:media3-ui:1.3.0")

    implementation("io.coil-kt:coil-compose:2.4.0")


}