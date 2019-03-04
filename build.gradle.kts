import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion: String by project
val kotlinxVersion: String by project
val ktorVersion: String by project
val sparetimedevsVersion: String by project
val logbackVersion: String by project
val slf4jVersion: String by project
val koinVersion: String by project
val javaDotEnvVersion: String by project
val junitVersion: String by project
val restAssuredVersion: String by project
val assertJVersion: String by project
val mongodbDriverSyncVersion: String by project
val kotlinLoggingVersion: String by project
val auth0JwtVersion: String by project

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

plugins {
    kotlin("jvm") version "1.3.11"
    id("com.github.johnrengelman.shadow") version "4.0.2"
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
    fun kotlin(artifact: String) = "org.jetbrains.kotlin:$artifact:$kotlinVersion"
    fun kotlinx(artifact: String) = "org.jetbrains.kotlinx:$artifact:$kotlinxVersion"
    fun ktor(artifact: String) = "io.ktor:$artifact:$ktorVersion"
    fun sparetimedevs(artifact: String) = "com.sparetimedevs:$artifact:$sparetimedevsVersion"

    implementation(kotlin("kotlin-stdlib-jdk8"))
    implementation(kotlinx("kotlinx-coroutines-core"))
    implementation(kotlinx("kotlinx-coroutines-reactive"))
    implementation(ktor("ktor-server-netty"))
    implementation(ktor("ktor-html-builder"))
    implementation(ktor("ktor-jackson"))
    implementation(ktor("ktor-websockets"))
    implementation(ktor("ktor-metrics"))
    implementation(ktor("ktor-locations"))
    implementation(ktor("ktor-auth"))
    implementation(ktor("ktor-auth-jwt"))

    implementation(sparetimedevs("suspendmongo"))
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.koin:koin-ktor:$koinVersion")
    implementation("io.github.cdimascio:java-dotenv:$javaDotEnvVersion")
    implementation("com.auth0:java-jwt:$auth0JwtVersion")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")

    testImplementation(kotlin("kotlin-test-junit"))
    testImplementation(kotlin("kotlin-test"))
    testImplementation(ktor("ktor-server-test-host"))
    testImplementation("org.assertj:assertj-core:$assertJVersion")
    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.koin:koin-test:$koinVersion")
    testImplementation("org.mongodb:mongodb-driver-sync:$mongodbDriverSyncVersion")
}

tasks.withType<ShadowJar> {
    baseName = "consonance-api"
    classifier = ""
    version = ""
}

task("stage") {
    dependsOn("clean", "build", "shadowJar")
}

apply {
    plugin("com.github.johnrengelman.shadow")
    plugin("application")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
