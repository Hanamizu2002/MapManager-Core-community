/*
 * This file was generated by the Gradle 'init' task.
 */
description = "v1_20_R1"

plugins {
    id("buildlogic.java-conventions")
    kotlin("jvm") version "1.9.23"

}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compileOnly("com.onarandombox.multiversecore:Multiverse-Core:4.3.1")
    compileOnly("net.luckperms:api:5.3")
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    implementation(project(":MapManager-API"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}