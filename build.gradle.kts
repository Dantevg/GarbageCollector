import kr.entree.spigradle.kotlin.spigotmc
import kr.entree.spigradle.kotlin.spigot

plugins {
    idea
    id("java")
    id("kr.entree.spigradle") version "2.4.3"
}

group = "nl.dantevg"
version = "1.0"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    mavenLocal()
    spigotmc()
}

dependencies {
    compileOnly(spigot("1.13.2"))
}

tasks.test {
    useJUnitPlatform()
}

spigot {
    apiVersion = "1.13"
    description = "Run the garbage collector"
    authors = listOf("RedPolygon")
    commands {
        create("gc") {
            description = "Run the garbage collector"
            permission = "garbagecollector.*"
            usage = """
                Usage:
                  /<command>       - runs the garbage collector
                  /<command> query - displays the amount of memory in use
            """.trimIndent()
        }
    }
    permissions {
        create("garbagecollector.*") {
            description = "Allow access to all garbage collector commands"
            defaults = "op"
        }
    }
}
