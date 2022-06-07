plugins {
    id("java-gradle-plugin")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    `maven-publish`
}

group = "com.github.quillmc"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://maven.fabricmc.net")
    maven("https://jitpack.io")
    mavenCentral()
}

dependencies {
    implementation("com.github.QuillMC:TinyMCP:f15b43105a");
}

gradlePlugin {
    plugins {
        create("ink") {
            id = "com.github.quillmc.ink"
            implementationClass = "com.github.quillmc.ink.InkPlugin"
        }
    }
}

tasks {
    jar {
        archiveClassifier.set("unshaded")
    }

    shadowJar {
        archiveClassifier.set("")
    }

    //build {
    //    dependsOn(shadowJar)
    //}
}

publishing {
    publications {
        register<MavenPublication>("shadow") {
            from(components.findByName("shadow"))
        }
    }
}

