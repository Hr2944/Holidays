buildscript {
    extra.apply {
        set("compose_version", "1.1.1")
        set("koin_version", "3.2.0")
        set("mockk_version", "1.12.4")
        set("material_dialogs_version", "0.7.2")
        set("google_protobuf_version", "3.21.2")
    }
}

plugins {
    id("com.android.application") version "7.2.1" apply false
    id("com.android.library") version "7.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
