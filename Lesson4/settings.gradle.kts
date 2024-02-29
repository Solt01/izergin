pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Lesson4"
include(":mp3player")
include(":thread")
include(":datathread")
include(":looper")
include(":cryptoloader")
include(":serviceapp")
include(":workmanager")
include(":mireaproject")
