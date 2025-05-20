plugins {
    id("java")
}
//plugins {
//    java
//}

group = "nsu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

//repositories {
//    maven { url = uri("https://jitpack.io ") }
//}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    //implementation("com.turn.torrent:jlibtorrent:1.2.0")
    //implementation("io.github.arnaudmorin:bencode:1.0")
    //implementation("com.dampcake:bencode:1.4.2")
    implementation("com.dampcake:bencode:1.3")
    //implementation("com.github.arnaudmorin:bencode-java:master-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}