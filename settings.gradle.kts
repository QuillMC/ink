rootProject.name = "ink"

dependencyResolutionManagement {
    repositories {
        maven("https://maven.fabricmc.net")
        maven("https://jitpack.io")
        mavenCentral()
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
    }
}

include("gradle-plugin", "test-mod")