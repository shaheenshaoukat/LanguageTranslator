pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        google()
        //noinspection JcenterRepositoryObsolete
        @Suppress("DEPRECATION")
        jcenter()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        google()
        //noinspection JcenterRepositoryObsolete
        @Suppress("DEPRECATION")
        jcenter()
    }
}

rootProject.name = "translator"
include(":app")
 