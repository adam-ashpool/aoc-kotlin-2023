plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("junit:junit:4.13.2")
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "8.4"
    }
}