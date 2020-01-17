import org.gradle.jvm.tasks.Jar
import java.net.URI

val snakeyamlVersion: String by project
val sillyCollectionsVersion: String by project
val fileWorkerVersion: String by project
val testngVersion: String by project
val junitVersion: String by project
val meanbeanVersion: String by project
val kotlinVersion: String by project

plugins {
    signing
    jacoco
    kotlin("jvm") version "1.3.61"
    id("java")
    id("maven-publish")
    id("com.github.ben-manes.versions") version "0.27.0"
    id("com.github.hierynomus.license") version "0.15.0"
    id("org.jetbrains.dokka") version "0.10.0"
}

group = "de.alpharogroup"
version = "1.1-SNAPSHOT"
description = "prop-to-yaml"

repositories {
    jcenter()
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.yaml:snakeyaml:$snakeyamlVersion")
    implementation("de.alpharogroup:silly-collections:$sillyCollectionsVersion")
    testImplementation("de.alpharogroup:file-worker:$fileWorkerVersion")
    testImplementation("org.testng:testng:$testngVersion")
    testImplementation("junit:junit:$junitVersion")
    testImplementation("org.meanbean:meanbean:$meanbeanVersion")
    implementation(kotlin("stdlib-jdk8"))
}

// Configure existing Dokka task to output HTML to typical Javadoc directory
tasks.dokka {
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}

// Create dokka Jar task from dokka task output
val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    archiveClassifier.set("javadoc")
    // dependsOn(tasks.dokka) not needed; dependency automatically inferred by from(tasks.dokka)
    from(tasks.dokka)
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(sourcesJar.get())
            artifact(dokkaJar)

            pom {
                name.set(rootProject.name)
                url.set("https://github.com/astrapi69/"+ rootProject.name)
                description.set("Useful extensions and utilities for transform properties to yaml")
                organization {
                    name.set("Alpha Ro Group UG (haftungsbeschrängt)")
                    url.set("http://www.alpharogroup.de/")
                }
                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/astrapi69/"+ rootProject.name +"/issues")
                }
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("http://www.opensource.org/licenses/mit-license.php")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("astrapi69")
                        name.set("Asterios Raptis")
                    }
                }
                scm {
                    connection.set("scm:git:git:@github.com:astrapi69/"+ rootProject.name +".git")
                    developerConnection.set("scm:git:git@github.com:astrapi69/"+ rootProject.name +".git")
                    url.set("git:@github.com:astrapi69/"+ rootProject.name +".git")
                }
            }

            repositories {
                maven {
                    credentials {
                        val usernameString = System.getenv("ossrhUsername")
                            ?: project.property("ossrhUsername")
                        val passwordString = System.getenv("ossrhPassword")
                            ?: project.property("ossrhPassword")
                        username = usernameString.toString()
                        password = passwordString.toString()
                    }
                    val releasesRepoUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
                    val snapshotsRepoUrl = "https://oss.sonatype.org/content/repositories/snapshots"
                    val projectVersion = version.toString()
                    val urlString = if(projectVersion.endsWith("SNAPSHOT"))  snapshotsRepoUrl else releasesRepoUrl
                    url = URI.create(urlString)
                }
            }
        }
    }
}

license {
    ext.set("year", "2015")
    ext.set("owner", "Asterios Raptis")
    header = rootProject.file("src/main/resources/LICENSE.txt")
    val value = listOf("**/README",
            "**/README.md",
            "**/LICENSE",
            "**/NOTICE",
            "**/*.xml",
            "**/*.xsl",
            "**/*.xsd",
            "**/*.dtd",
            "**/*.html",
            "**/*.json",
            "**/*.jsp",
            "**/*.jpa",
            "**/*.sql",
            "**/*.properties",
            "**/*.bat",
            "**/*.gradle",
            "**/*.MF",
            "**/*.txt",
            "**/*.vm",
            "**/*.log",
            "**/*.map",
            "**/*.js.map",
            "**/*.tmpl",
            "**/*.js.tmpl",
            "**/*.editorconfig",
            "src/test/resources/**",
            "src/main/resources/**",
            "out/**",
            "build/**")
    excludes(value)
}

// workhack for license issue #76
gradle.startParameter.excludedTaskNames += "licenseMain"
gradle.startParameter.excludedTaskNames += "licenseTest"

signing {
    sign(publishing.publications["mavenJava"])
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.test {
    useTestNG()
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = false
        csv.isEnabled = false
        html.isEnabled = true
        html.destination = file("$buildDir/reports/coverage")
    }
    classDirectories.setFrom(
            sourceSets.main.get().output.asFileTree.matching {
                // exclude main()
                exclude("")
            }
    )
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.0".toBigDecimal()
            }
        }
    }
}

val testCoverage by tasks.registering {
    group = "verification"
    description = "Runs the unit tests with coverage."

    dependsOn(":test", ":jacocoTestReport", ":jacocoTestCoverageVerification")
    val jacocoTestReport = tasks.findByName("jacocoTestReport")
    jacocoTestReport?.mustRunAfter(tasks.findByName("test"))
    tasks.findByName("jacocoTestCoverageVerification")?.mustRunAfter(jacocoTestReport)
}