import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}
fun runCommand(command: String): String {
    val byteOut = ByteArrayOutputStream()
    project.exec {
        commandLine = command.split(" ")
        standardOutput = byteOut
    }
    return String(byteOut.toByteArray()).trim()
}

fun getGitSha(): String = runCommand("git rev-parse --short HEAD")


android {
    namespace = "com.h4de5ing.autopickupcall"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.h4de5ing.autopickupcall"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    signingConfigs {
        create("release") {
            keyAlias = "android"
            keyPassword = "android"
            //NewPublic Q86
            storeFile =
                file("D:\\Android12SignerGUI\\SignFiles\\NewPublic\\platform.jks")
            storePassword = "android"
        }
    }
    buildTypes {
        debug {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
        }
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    applicationVariants.configureEach {
        val buildType = buildType.name
        val id = defaultConfig.applicationId
        val prefix = id?.substring(id.lastIndexOf(".") + 1) ?: ""
        val createTime = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        if (buildType == "release") {
            outputs.all {
                val fromFile = outputFile
                val intoFile = "D:/test/$prefix/v${defaultConfig.versionName}"
//                val intoFile = "\\\\192.168.0.74\\亿道信息\\RD\\SW_A\\71备份过来的\\APP软件版本\\${prefix}\\v${defaultConfig.versionName}"
                copy {
                    from(fromFile)
                    into(intoFile)
                    include("*release*.apk")
                    rename(
                        "app-",
                        "${prefix}_v${defaultConfig.versionName}_${getGitSha()}_${createTime}_"
                    )
                }
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.lifecycle.service)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.viewmodel.savedstate)
    implementation(libs.androidx.lifecycle.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
}