import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = "1.3.21"

plugins {
	id("org.springframework.boot") version "2.1.5.RELEASE"
	id("io.spring.dependency-management") version "1.0.7.RELEASE"
	kotlin("jvm") version "1.3.21"
	kotlin("plugin.spring") version "1.3.21"
}

group = "com.tiendanube"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "Greenwich.SR1"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.cloud:spring-cloud-starter-sleuth")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("io.arrow-kt:arrow-core-data:0.9.0")
	implementation("io.arrow-kt:arrow-typeclasses:0.9.0")
	implementation("io.arrow-kt:arrow-effects-extensions:0.9.0")

	runtimeOnly("org.postgresql:postgresql")
	testImplementation("io.rest-assured:rest-assured:4.0.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(module = "junit")
	}
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
	testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
	testImplementation("org.testcontainers:junit-jupiter:1.11.3")
	testImplementation("org.testcontainers:postgresql:1.11.3")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
