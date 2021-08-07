import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
    application
}

application {
    mainClass.set("MainKt")
}

group = "me.amit.dash"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:+")
    testImplementation("io.kotest:kotest-assertions-core:+")
    testImplementation("io.kotest:kotest-property:+")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "9"
}

tasks.register("githubBuild") {
    dependsOn(tasks.build, tasks.test)
    group = "me.amit.dash"
    description = "$ ./gradlew githubBuild # runs build on GitHub Action"
}
