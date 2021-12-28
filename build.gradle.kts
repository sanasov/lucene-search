import com.adarshr.gradle.testlogger.theme.ThemeType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val luceneVersion: String by project
val kotlinLoggingVersion: String by project
val kotlinCoroutinesVersion: String by project
val compileKotlin: KotlinCompile by tasks
val compileTestKotlin: KotlinCompile by tasks

plugins {
    val kotlinVersion = "1.5.21"
    val loggerVersion = "3.0.0"
    val kotlinterVersion = "3.4.5"

    kotlin("jvm") version kotlinVersion
    id("com.adarshr.test-logger") version loggerVersion
}

group = "ru.igrey.dev"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    maven { url = uri("https://repo.sberned.ru/repository/maven-releases/") }
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
    jcenter()
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:$kotlinCoroutinesVersion")

    // Lucene
    implementation("org.apache.lucene:lucene-core:$luceneVersion")
    implementation("org.apache.lucene:lucene-queryparser:$luceneVersion")
    implementation("org.apache.lucene:lucene-analyzers-common:8.11.1")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinCoroutinesVersion")
}

sourceSets.getByName("main") {
    java.srcDirs("src/main/kotlin")
}

sourceSets.getByName("test") {
    java.srcDirs("src/test/kotlin")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testlogger {
        theme = ThemeType.STANDARD
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf(
            "-Xjsr305=strict",
            "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xuse-experimental=kotlinx.coroutines.ObsoleteCoroutinesApi")
        jvmTarget = "11"
    }
}