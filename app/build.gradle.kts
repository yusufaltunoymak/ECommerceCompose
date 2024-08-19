plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    alias(libs.plugins.navigationSafeArgs)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.hoy.ecommercecompose"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hoy.ecommercecompose"
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
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
kapt {
    correctErrorTypes = true
}
dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    implementation(libs.engage.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    implementation(platform(libs.firebase.bom))
    androidTestImplementation(libs.androidx.navigation.testing)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.navigation.compose)
    annotationProcessor(libs.androidx.room.compiler)
    debugImplementation(libs.androidx.ui.tooling)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.play.services.auth)
    implementation(libs.androidx.hilt.navigation.compose)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.coil.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    //bottom navigation compose
    implementation(libs.androidx.compose.material3)
    implementation(libs.ui)
    implementation(libs.androidx.material)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.material.icons.extended)

    //detekt
    detektPlugins(libs.detekt)
}

detekt {
    config.setFrom(file("$rootDir/detekt/detektConfig.yml"))
    source.from(files("src/main/kotlin", "src/test/kotlin"))
    parallel = true
    autoCorrect = true
    buildUponDefaultConfig = true
}