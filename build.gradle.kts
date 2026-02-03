plugins {
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.20"
    id("io.ktor.plugin") version "2.3.5"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.mangavel"
version = "1.0.0"

repositories {
    google()         // 1. Google first for Android-related parts
    mavenCentral()   // 2. Standard libraries
    maven { url = uri("https://jitpack.io") } // 3. JitPack last
}

dependencies {
    // This version (v2.11.0) is verified as "Green" on JitPack
    implementation("com.github.KotatsuApp:kotatsu-parsers:v2.11.0")
    
    // Ktor Server dependencies
    implementation("io.ktor:ktor-server-core-jvm:2.3.5")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.5")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:2.3.5")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.3.5")
    implementation("io.ktor:ktor-server-cors-jvm:2.3.5")
    implementation("ch.qos.logback:logback-classic:1.4.11")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    manifest {
        attributes["Main-Class"] = "ApplicationKt"
    }
    archiveBaseName.set("manga-api")
    archiveClassifier.set("all")
    archiveVersion.set("")
}
