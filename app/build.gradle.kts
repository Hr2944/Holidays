import com.google.protobuf.gradle.*

plugins {
    idea
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.protobuf") version "0.8.19"
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.hrb.holidays"
        minSdk = 28
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {

            isMinifyEnabled = true
            isShrinkResources = true
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${rootProject.extra["google_protobuf_version"]}"
    }
    generateProtoTasks {
        all().forEach {
            it.builtins {
                id("kotlin") {
                }
                id("java") {
                    option("lite")
                }
            }
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
}

dependencies {
    // Android core features and Jetpack Compose
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.compose.ui:ui:1.1.1")
    implementation("androidx.compose.material:material:1.1.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.1.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.1.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.1.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.0")
    implementation("androidx.activity:activity-compose:1.5.0")
    // Testing
    testImplementation("io.mockk:mockk:1.12.4")
    testImplementation("io.kotest:kotest-assertions-core:5.3.2")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.1.1")
    // Koin dependency injection
    implementation("io.insert-koin:koin-core:3.2.0")
    testImplementation("io.insert-koin:koin-test:3.2.0")
    implementation("io.insert-koin:koin-android:3.2.0")
    implementation("io.insert-koin:koin-android-compat:3.2.0")
    implementation("io.insert-koin:koin-androidx-workmanager:3.2.0")
    implementation("io.insert-koin:koin-androidx-navigation:3.2.0")
    implementation("io.insert-koin:koin-androidx-compose:3.2.0")
    // Extended icons pack
    implementation("androidx.compose.material:material-icons-extended:1.1.1")
    // Jetpack Compose Material Dialogs
    implementation("io.github.vanpra.compose-material-dialogs:core:${rootProject.extra["material_dialogs_version"]}")
    implementation("io.github.vanpra.compose-material-dialogs:datetime:${rootProject.extra["material_dialogs_version"]}")
    // Jetpack Datastore
    implementation("androidx.datastore:datastore:1.0.0")
    implementation("com.google.protobuf:protobuf-kotlin-lite:3.21.2")
}
