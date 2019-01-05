import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val kotlinVersion: String by project
val ktorVersion: String by project
val logbackVersion: String by project
val slf4jVersion: String by project
val koinVersion: String by project
val mongodbDriverReactiveStreamVersion: String by project
val kotlinxVersion: String by project
val junitVersion: String by project
val restAssuredVersion: String by project
val assertJVersion: String by project
val mongodbDriverSyncVersion: String by project

application {
    mainClassName = "io.ktor.server.netty.DevelopmentEngine"
}

plugins {
    kotlin("jvm") version "1.3.11"
    id("com.github.johnrengelman.shadow") version "2.0.4"
    id("com.palantir.docker") version "0.20.1"
    id("org.flywaydb.flyway") version "5.2.0"

    application
    idea
}

group = "consonance-api"
version = ""

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    fun kotlin(artifact: String = "") = "org.jetbrains.kotlin:$artifact:$kotlinVersion"
    fun kotlinx(artifact: String = "") = "org.jetbrains.kotlinx:$artifact:$kotlinxVersion"
    fun ktor(artifact: String = "") = "io.ktor:$artifact:$ktorVersion"

    compile(kotlin("kotlin-stdlib-jdk8"))

    compile(ktor("ktor-server-netty"))
    compile(ktor("ktor-html-builder"))
    compile(ktor("ktor-jackson"))
    compile(ktor("ktor-websockets"))
    compile(ktor("ktor-metrics"))

    compile(kotlinx("kotlinx-coroutines-core"))
    compile(kotlinx("kotlinx-coroutines-reactive"))

    compile("ch.qos.logback:logback-classic:$logbackVersion")
    compile("org.slf4j:slf4j-api:$slf4jVersion")
    compile("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    compile("org.koin:koin-core:$koinVersion")
    compile("org.mongodb:mongodb-driver-reactivestreams:$mongodbDriverReactiveStreamVersion")

    testCompile(kotlin("kotlin-test-junit"))
    testCompile(kotlin("kotlin-test"))

    testCompile(ktor("ktor-server-test-host"))

    testCompile("org.assertj:assertj-core:$assertJVersion")
    testCompile("io.rest-assured:rest-assured:$restAssuredVersion")
    testCompile("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testCompile("org.koin:koin-test:$koinVersion")
    testCompile("org.mongodb:mongodb-driver-sync:$mongodbDriverSyncVersion")
}

apply {
    plugin("com.github.johnrengelman.shadow")
    plugin("application")
}

tasks.withType<ShadowJar> {
    baseName = "consonance-api"
    classifier = ""
    version = ""
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
