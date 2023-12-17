import java.util.Properties
plugins {
    id("com.android.application")
}
fun Project.getIpAddress(): String {
    val properties = Properties()
    rootProject.file("local.properties").inputStream().use {
        properties.load(it)
    }
    return properties.getProperty("ip_addr") ?: ""
}


android {
    namespace = "com.example.booking_team22"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.booking_team22"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        //buildConfigField="String", "IP_ADDR", "\""+getIpAddress()+"\""
        buildConfigField("String", "IP_ADDR", "\"${getIpAddress()}\"")
        testInstrumentationRunner="androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }
    buildFeatures{
        viewBinding=true
        buildConfig=true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.5")
    implementation("androidx.navigation:navigation-ui:2.7.5")
    implementation("com.google.android.material:material:1.10.0")
    implementation ("com.squareup.retrofit2:retrofit:2.3.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.3.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:3.12.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
}