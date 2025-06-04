plugins {
    id("java")
    id ("application")
}
//plugins {
//    java
//}

group = "nsu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("nsu.TorrentClient") // Укажи свой главный класс
}
//repositories {
//    maven { url = uri("https://jitpack.io ") }
//}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    //implementation("com.dampcake:bencode:1.4.2")
    implementation("com.turn:ttorrent-core:1.5")
    implementation("commons-io:commons-io:2.11.0")
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("org.slf4j:slf4j-simple:2.0.9")
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
}

tasks.test {
    useJUnitPlatform()
}
