plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(29)
    buildToolsVersion = "29.0.2"

    defaultConfig {
        applicationId = "com.mahmoudalyudeen.plutus"
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    androidExtensions {
        isExperimental = true
    }

    dataBinding {
        isEnabled = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    defaultConfig {
        testInstrumentationRunner = "com.mahmoudalyudeen.plutus.TestAppJUnitRunner"
    }

    /** To run Robolectric tests with android resources */
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Kotlin
    implementation(Libs.kotlin_stdlib_jdk7)

    // AndroidX ktx Core
    implementation(Libs.androidx_core_core_ktx)

    // Dagger

    // Dagger
    implementation("com.google.dagger:dagger:2.24")
    kapt("com.google.dagger:dagger-compiler:2.24")
    implementation("com.google.dagger:dagger-android-support:2.24")
    kapt("com.google.dagger:dagger-android-processor:2.24")

    // Material
    implementation(Libs.material)

    // AndroidX AppCompat
    implementation(Libs.appcompat)

    // AndroidX Activity
    implementation(Libs.activity_ktx)

    // AndroidX Fragment
    implementation(Libs.fragment_ktx)

    // Koin
    implementation(Libs.koin_android_viewmodel)

    // Constraint Layout
    implementation(Libs.constraintlayout)

    // ViewModel and LiveData
    implementation(Libs.lifecycle_extensions)

    // MPAndroidChart
    implementation(Libs.mpandroidchart)

    // Navigation
    implementation(Libs.navigation_fragment_ktx)
    implementation(Libs.navigation_ui_ktx)

    // Room
    implementation(Libs.room_runtime)
    implementation(Libs.room_ktx)
    kapt(Libs.room_compiler)

    // Moshi
    implementation(Libs.moshi)
    implementation(Libs.moshi_kotlin)

    // Retrofit with Moshi Converter and OkHtp Interceptor
    implementation(Libs.retrofit)
    implementation(Libs.converter_moshi)
    implementation(Libs.logging_interceptor)

    // Coroutines
    implementation(Libs.kotlinx_coroutines_core)
    implementation(Libs.kotlinx_coroutines_android)

    // Retrofit Coroutines Support
    implementation(Libs.retrofit2_kotlin_coroutines_adapter)

    // RecyclerView
    implementation(Libs.recyclerview)

    // Unit testing
    testImplementation(Libs.mockk)
    testImplementation(Libs.junit)
    testImplementation(Libs.androidx_arch_core_core_testing)
    testImplementation(Libs.kotlinx_coroutines_android)
    testImplementation(Libs.kotlinx_coroutines_test)
    testImplementation(Libs.robolectric)
    testImplementation(Libs.espresso_core)
    testImplementation(Libs.espresso_contrib)
    testImplementation(Libs.espresso_intents)
    testImplementation(Libs.truth)
    testImplementation(Libs.koin_test)
    testImplementation(Libs.androidx_test_core_ktx)
    testImplementation(Libs.androidx_test_rules)
    testImplementation(Libs.junit_ktx)

    /**
     * For fragment scenario testing.
     * issue: https://issuetracker.google.com/127986458
     * When fixed, replace [debugImplementation] with [testImplementation]
     */
    debugImplementation(Libs.fragment_testing)
    debugImplementation(Libs.androidx_test_core)

    // Instrumentation testing
    androidTestImplementation(Libs.koin_test)
    androidTestImplementation(Libs.junit)
    androidTestImplementation(Libs.kotlinx_coroutines_test)
    androidTestImplementation(Libs.truth)
    androidTestImplementation(Libs.androidx_test_core_ktx)
    androidTestImplementation(Libs.androidx_test_rules)
    androidTestImplementation(Libs.androidx_arch_core_core_testing)
    androidTestImplementation(Libs.org_robolectric_annotations)
    androidTestImplementation(Libs.espresso_core)
    androidTestImplementation(Libs.espresso_contrib)
    androidTestImplementation(Libs.mockk_android) {
        // kotlin-reflect is a transitive dependency of moshi-kotlin with a different version
        exclude("org.jetbrains.kotlin", "kotlin-reflect")
    }
}
