plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    id("com.google.devtools.ksp") version "1.9.21-1.0.15"
}

android {
    namespace = "com.example.daisyapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.daisyapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField ("String", "AUTH_URL", "\"https://asia-southeast2-inbound-decker-441613-s6.cloudfunctions.net/app/api/\"")
//        buildConfigField ("String", "AUTH_URL", "\"https://story-api.dicoding.dev/v1/\"")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
        mlModelBinding = true
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

    implementation("org.tensorflow:tensorflow-lite-task-vision:0.4.4")
    implementation("org.tensorflow:tensorflow-lite-metadata:0.1.0")

    // ucrop for cropping or rotate image
    implementation("com.github.yalantis:ucrop:2.2.8")

    // Coil - library that provide function extension for load image from internet
    implementation("io.coil-kt:coil:2.6.0")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("org.tensorflow:tensorflow-lite-gpu:2.3.0")

    // Fragment
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.activity:activity-ktx:1.7.2")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")

    // Room
    implementation("androidx.room:room-ktx:2.5.2")
    implementation("androidx.room:room-runtime:2.5.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.24")
    ksp("androidx.room:room-compiler:2.5.2")

    implementation("com.github.bumptech.glide:glide:4.15.1")
}