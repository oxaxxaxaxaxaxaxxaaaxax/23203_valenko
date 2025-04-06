plugins {
    id("java")
    id ("application")
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "nsu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.openjfx:javafx-controls:17")
    implementation ("org.openjfx:javafx-fxml:17")
    implementation ("com.github.almasb:fxgl:17.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

javafx {
    version = "17"
    modules = listOf("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("nsu.GameApp")
}

//tasks.withType<JavaExec> {
//    jvmArgs = listOf(
//        "--module-path", classpath.asPath,
//        "--add-modules", "javafx.controls,javafx.fxml"
//    )
//}

//tasks.register<JavaExec>("run") {
//    mainClass.set("org.example.FXApp") // Замените на ваш главный класс
//    classpath = sourceSets["main"].runtimeClasspath
//    jvmArgs = listOf(
//        "--module-path", classpath.asPath,
//        "--add-modules", "javafx.controls,javafx.fxml"
//    )
//}