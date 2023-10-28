import com.google.protobuf.gradle.id

plugins {
    kotlin("jvm") version "1.9.10"
    id("com.google.protobuf") version "0.9.4"
}

group = "com.testcode"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(8)
}
dependencies {
    implementation("com.google.protobuf:protobuf-kotlin:3.24.4")
    implementation("io.grpc:grpc-kotlin-stub:1.4.0")
    implementation("io.grpc:grpc-protobuf:1.59.0")
    implementation("io.grpc:grpc-netty:1.59.0")
    testImplementation(kotlin("test"))
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.24.4"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.59.0"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.4.0:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
            it.builtins {
                id("kotlin")
            }
        }
    }
}
