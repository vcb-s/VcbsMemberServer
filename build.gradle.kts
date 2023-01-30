plugins {
	java
	id("org.springframework.boot") version "3.0.2"
	id("io.spring.dependency-management") version "1.1.0"
}

group = "org.vcb-s"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	maven { url = uri("https://maven.aliyun.com/repository/public/") }
	maven { url = uri("https://maven.aliyun.com/repository/google/") }
	maven { url = uri("https://maven.aliyun.com/repository/spring/") }
	maven { url = uri("https://maven.aliyun.com/repository/spring-plugin/") }
	maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin/") }
	mavenLocal()
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.session:spring-session-core")
    implementation("org.jetbrains:annotations:23.0.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
