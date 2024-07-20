// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
}
buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.0-alpha14")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44")
    }
}