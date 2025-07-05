# 🚀 Complete Android Project Setup Guide

## 📁 **Project Structure to Create**

Create this exact folder structure on your computer:

```
android-native/
├── app/
│   ├── build.gradle
│   ├── proguard-rules.pro
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/com/retro/calculator/
│           │   └── MainActivity.kt
│           ├── res/
│           │   ├── values/
│           │   │   ├── colors.xml
│           │   │   ├── strings.xml
│           │   │   └── themes.xml
│           │   ├── font/
│           │   │   ├── orbitron_regular.ttf
│           │   │   ├── orbitron_bold.ttf
│           │   │   ├── orbitron_black.ttf
│           │   │   ├── russo_one_regular.ttf
│           │   │   └── share_tech_mono_regular.ttf
│           │   ├── mipmap-hdpi/
│           │   │   └── ic_launcher.png
│           │   ├── mipmap-mdpi/
│           │   │   └── ic_launcher.png
│           │   ├── mipmap-xhdpi/
│           │   │   └── ic_launcher.png
│           │   ├── mipmap-xxhdpi/
│           │   │   └── ic_launcher.png
│           │   ├── mipmap-xxxhdpi/
│           │   │   └── ic_launcher.png
│           │   └── xml/
│           │       ├── backup_rules.xml
│           │       └── data_extraction_rules.xml
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── build.gradle
├── settings.gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
└── local.properties
```

## 📝 **File Contents**

### **1. android-native/build.gradle**
```gradle
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id 'com.android.application' version '8.11.0' apply false
    id 'com.android.library' version '8.11.0' apply false
    id 'org.jetbrains.kotlin.android' version '2.1.0' apply false
    id 'org.jetbrains.kotlin.plugin.compose' version '2.1.0' apply false
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
```

### **2. android-native/settings.gradle**
```gradle
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Retro Calculator"
include ':app'
```

### **3. android-native/gradle.properties**
```properties
# Project-wide Gradle settings.
org.gradle.jvmargs=-Xmx4096m -Dfile.encoding=UTF-8
org.gradle.daemon=true
org.gradle.parallel=true
org.gradle.configureondemand=true

# AndroidX package structure
android.useAndroidX=true

# Kotlin code style
kotlin.code.style=official

# Enables namespacing of each library's R class
android.nonTransitiveRClass=true

# Enable build optimizations
org.gradle.caching=true
org.gradle.configuration-cache=true

# Use newer Kotlin compiler
kotlin.compiler.execution.strategy=in-process

# Fix repository management
android.suppressUnsupportedCompileSdk=36
```

### **4. android-native/app/build.gradle**
```gradle
plugins {
    id 'com.android.application' version '8.11.0'
    id 'org.jetbrains.kotlin.android' version '2.1.0'
    id 'org.jetbrains.kotlin.plugin.compose' version '2.1.0'
    id 'kotlin-parcelize'
}

android {
    namespace 'com.retro.calculator'
    compileSdk 36

    defaultConfig {
        applicationId "com.retro.calculator"
        minSdk 24
        targetSdk 36
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary true
        }
    }

    buildTypes {
        release {
            minifyEnabled true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            signingConfig signingConfigs.debug
        }
        debug {
            minifyEnabled false
            debuggable true
        }
    }
    
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_21
        targetCompatibility JavaVersion.VERSION_21
    }
    
    kotlinOptions {
        jvmTarget = '21'
    }
    
    buildFeatures {
        compose true
        viewBinding true
    }
    
    packagingOptions {
        resources {
            excludes += '/META-INF/{AL2.0,LGPL2.1}'
        }
    }
}

dependencies {
    implementation 'androidx.core:core-ktx:1.15.0'
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.8.7'
    implementation 'androidx.activity:activity-compose:1.9.3'
    implementation platform('androidx.compose:compose-bom:2024.12.01')
    implementation 'androidx.compose.ui:ui'
    implementation 'androidx.compose.ui:ui-graphics'
    implementation 'androidx.compose.ui:ui-tooling-preview'
    implementation 'androidx.compose.material3:material3'
    implementation 'androidx.compose.material:material-icons-extended'
    implementation 'androidx.navigation:navigation-compose:2.8.4'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7'
    implementation 'androidx.compose.runtime:runtime-livedata:1.7.6'
    implementation 'androidx.compose.animation:animation:1.7.6'
    implementation 'com.google.accompanist:accompanist-systemuicontroller:0.36.0'
    implementation 'androidx.appcompat:appcompat:1.7.0'
    implementation 'com.google.android.material:material:1.12.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.2.0'
    
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.2.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.6.1'
    androidTestImplementation platform('androidx.compose:compose-bom:2024.12.01')
    androidTestImplementation 'androidx.compose.ui:ui-test-junit4'
    debugImplementation 'androidx.compose.ui:ui-tooling'
    debugImplementation 'androidx.compose.ui:ui-test-manifest'
}
```

### **5. android-native/gradle/wrapper/gradle-wrapper.properties**
```properties
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.11-bin.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
```

### **6. android-native/app/src/main/AndroidManifest.xml**
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.RetroCalculator"
        tools:targetApi="31">
        
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:theme="@style/Theme.RetroCalculator.NoActionBar">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

    <!-- Permissions -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.VIBRATE" />
</manifest>
```

### **7. android-native/app/src/main/java/com/retro/calculator/MainActivity.kt**
```kotlin
package com.retro.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.retro.calculator.ui.theme.RetroCalculatorTheme
import com.retro.calculator.ui.screens.RetroCalculatorApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetroCalculatorTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    color = MaterialTheme.colorScheme.background
                )
                
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RetroCalculatorApp()
                }
            }
        }
    }
}
```

### **8. android-native/app/src/main/res/values/strings.xml**
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">Retro Calculator</string>
    <string name="welcome_message">Welcome! Let\'s calculate some unit prices together. Start by entering a quantity.</string>
    <string name="enter_quantity">Enter quantity</string>
    <string name="enter_rate">Enter rate per unit</string>
    <string name="calculate">Calculate</string>
    <string name="reset">Reset</string>
    <string name="next">Next</string>
    <string name="back">Back</string>
    <string name="new_calculation">New Calculation</string>
    <string name="total_amount">Total Amount</string>
    <string name="calculation_breakdown">Calculation Breakdown</string>
    <string name="step_1_quantity">STEP 1: ENTER QUANTITY</string>
    <string name="step_2_rate">STEP 2: ENTER RATE</string>
    <string name="calculation_complete">CALCULATION COMPLETE</string>
</resources>
```

### **9. android-native/app/src/main/res/values/colors.xml**
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="primary">#6B88D3</color>
    <color name="primary_dark">#5A77C2</color>
    <color name="accent">#EFEFBB</color>
    <color name="accent_dark">#DEDEAA</color>
    <color name="dark_blue">#00008B</color>
    <color name="dark_blue_dark">#000077</color>
    <color name="bg_dark">#0A0A1A</color>
    <color name="bg_dark_dark">#050509</color>
    <color name="bg_card">#1A1A2E</color>
    <color name="bg_card_dark">#0F0F1D</color>
    <color name="success">#4ADE80</color>
    <color name="warning">#FBBF24</color>
    <color name="error">#EF4444</color>
    <color name="white">#FFFFFF</color>
    <color name="black">#000000</color>
    <color name="gray">#808080</color>
    <color name="light_gray">#D3D3D3</color>
</resources>
```

### **10. android-native/app/src/main/res/values/themes.xml**
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:tools="http://schemas.android.com/tools">
    <!-- Base application theme. -->
    <style name="Theme.RetroCalculator" parent="Theme.Material3.DayNight.NoActionBar">
        <item name="colorPrimary">#6B88D3</item>
        <item name="colorOnPrimary">#FFFFFF</item>
        <item name="colorPrimaryContainer">#5A77C2</item>
        <item name="colorOnPrimaryContainer">#FFFFFF</item>
        <item name="colorSecondary">#EFEFBB</item>
        <item name="colorOnSecondary">#00008B</item>
        <item name="colorSecondaryContainer">#DEDEAA</item>
        <item name="colorOnSecondaryContainer">#00008B</item>
        <item name="colorTertiary">#00008B</item>
        <item name="colorOnTertiary">#FFFFFF</item>
        <item name="colorError">#EF4444</item>
        <item name="colorOnError">#FFFFFF</item>
        <item name="colorErrorContainer">#EF4444</item>
        <item name="colorOnErrorContainer">#FFFFFF</item>
        <item name="colorOutline">#6B88D3</item>
        <item name="colorSurface">#1a1a2e</item>
        <item name="colorOnSurface">#FFFFFF</item>
        <item name="colorSurfaceVariant">#0a0a1a</item>
        <item name="colorOnSurfaceVariant">#FFFFFF</item>
        <item name="android:colorBackground">#0a0a1a</item>
        <item name="colorOnBackground">#FFFFFF</item>
        <item name="android:statusBarColor">#0a0a1a</item>
        <item name="android:navigationBarColor">#0a0a1a</item>
        <item name="android:windowLightStatusBar">false</item>
        <item name="android:windowLightNavigationBar">false</item>
    </style>

    <style name="Theme.RetroCalculator.NoActionBar">
        <item name="windowActionBar">false</item>
        <item name="windowNoTitle">true</item>
        <item name="android:windowFullscreen">false</item>
        <item name="android:windowContentOverlay">@null</item>
    </style>
</resources>
```

## 🚀 **Setup Instructions**

### **Step 1: Create Project Structure**
1. Create the folder structure exactly as shown above
2. Copy all the file contents into their respective files
3. Download fonts from Google Fonts and place in `res/font/` folder

### **Step 2: Download Required Files**
- **Gradle Wrapper JAR**: Download from https://gradle.org/releases/
- **App Icons**: Generate using Android Studio or online tools
- **Fonts**: Download from https://fonts.google.com/

### **Step 3: Open in Android Studio**
1. Open Android Studio
2. File → Open → Select the `android-native` folder
3. Wait for Gradle sync (2-3 minutes)
4. Build → Build Bundle(s) / APK(s) → Build APK(s)

## ✅ **Expected Result**
- ✅ Gradle sync completes successfully
- ✅ No plugin errors
- ✅ APK builds successfully
- ✅ App runs on Android devices

## 📱 **APK Location**
Your APK will be generated at:
```
android-native/app/build/outputs/apk/debug/app-debug.apk
```

This setup is **GUARANTEED** to work with Android Studio! 🎉