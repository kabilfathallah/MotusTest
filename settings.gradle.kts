pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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



enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "MotusTest"
include(":app")
include(":feature:welcome")
include(":feature:game")
include(":core:designsystem")
include(":core:ui")
include(":core:data")
include(":core:network")
include(":core:domain")
include(":core:common")
include(":core:database")
include(":feature:list")
