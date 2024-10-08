// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.androidKsp) apply false
    alias(libs.plugins.hilt) apply  false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.module.graph) apply true // Plugin applied to allow module graph generation

}