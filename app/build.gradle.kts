plugins {
    id("com.android.application")
}

android {
    namespace = "com.anpha.android_huflit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.anpha.android_huflit"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation  ("com.squareup.picasso:picasso:2.71828")
    implementation ("androidx.drawerlayout:drawerlayout:1.1.1")
    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    implementation ("com.makeramen:roundedimageview:2.3.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.mediarouter:mediarouter:1.6.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    
    //thư viện chỉnh độ tròn của ảnh
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.google.android.material:material:<version>")
    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))

    // define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("com.google.code.gson:gson:2.8.8")

    implementation ("com.squareup.okhttp3:okhttp:4.9.1")
    // render image from link
    implementation ("com.squareup.picasso:picasso:2.8")
}