buildscript {

    val kotlinVersion = "1.7.10"

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {

        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")

        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.android.tools.build:gradle:7.2.2")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}