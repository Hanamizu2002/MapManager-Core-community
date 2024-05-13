/*
 * This file was generated by the Gradle 'init' task.
 */
description = "MapManager-API"

plugins {
    id("buildlogic.java-conventions")
    kotlin("jvm") version "1.9.23"
    id("org.jetbrains.dokka") version "1.9.20"
    id("net.thebugmc.gradle.sonatype-central-portal-publisher") version "1.2.3"
}

group = "work.alsace.mapmanager"
version = "3.1"

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compileOnly("com.onarandombox.multiversecore:Multiverse-Core:4.3.1")
    compileOnly("net.luckperms:api:5.3")
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

signing {
    useGpgCmd()
}

centralPortal {
    username = properties["centralPortal.username"].toString()
    password = properties["centralPortal.password"].toString()
    name = "MapManager-Core"

    pom {
        url = "..."
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                name = "AlsaceWork"
                email = "tech@alsace.team"
            }
        }
        scm {
            connection = "scm:git:https://github.com/Alsace-Technology-Department/MapManager-Core-community.git"
            developerConnection = "scm:git:https://github.com/Alsace-Technology-Department/MapManager-Core-community.git"
            url = "https://github.com/Alsace-Technology-Department/MapManager-Core-community.git"
        }
    }
}

centralPortal {
    sourcesJarTask = tasks.create<Jar>("sourcesEmptyJar") {
        archiveClassifier = "sources"
    }
    javadocJarTask = tasks.create<Jar>("javadocEmptyJar") {
        archiveClassifier = "javadoc"
    }
}






