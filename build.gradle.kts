import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import io.izzel.taboolib.gradle.*
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8
import io.izzel.taboolib.gradle.Basic
import io.izzel.taboolib.gradle.BukkitUI
import io.izzel.taboolib.gradle.BukkitHook
import io.izzel.taboolib.gradle.Bukkit


plugins {
    java
    id("io.izzel.taboolib") version "2.0.37"
    id("org.jetbrains.kotlin.jvm") version "2.2.0"
}

taboolib {
    env {
        install(Basic)
        install(BukkitUI)
        install(BukkitHook)
        install(Bukkit)
        install(Kether)
        install(CommandHelper)
    }
    description {
        name = "CloudProfile"
        desc("查看玩家个人档案")
        contributors {
            name("BalanceSea")
        }
        dependencies {
            name("PlaceholderAPI").optional(true)
        }
    }
    version { taboolib = "6.3.0-afd75a7" }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("ink.ptms.core:v12004:12004:mapped")
    compileOnly("ink.ptms.core:v12004:12004:universal")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        jvmTarget.set(JVM_1_8)
        freeCompilerArgs.add("-Xjvm-default=all")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}