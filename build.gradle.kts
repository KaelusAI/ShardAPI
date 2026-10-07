import net.ltgt.gradle.errorprone.CheckSeverity
import net.ltgt.gradle.errorprone.errorprone

plugins {
  `java-library`
  alias(libs.plugins.spotless)
  alias(libs.plugins.errorprone)
  alias(libs.plugins.maven.publish)
}

repositories {
  mavenCentral()
  maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
  api(libs.jspecify)
  compileOnlyApi(libs.jetbrains.annotations)
  compileOnly(libs.paper.api)
  errorprone(libs.errorprone.core)
  errorprone(libs.nullaway)
}

java { toolchain.languageVersion.set(JavaLanguageVersion.of(21)) }

tasks.withType<JavaCompile>().configureEach {
  options.release.set(17)
  options.encoding = "UTF-8"
  options.compilerArgs.addAll(listOf("-Xlint:all", "-Werror"))
  options.errorprone {
    disableWarningsInGeneratedCode.set(true)
    check("NullAway", CheckSeverity.ERROR)
    option("NullAway:OnlyNullMarked", "true")
    option("NullAway:JSpecifyMode", "true")
  }
}

tasks.jar { manifest { attributes("Automatic-Module-Name" to "ac.shard.api") } }

tasks.withType<AbstractArchiveTask>().configureEach {
  isPreserveFileTimestamps = false
  isReproducibleFileOrder = true
}

tasks.javadoc {
  (options as StandardJavadocDocletOptions).apply {
    encoding = "UTF-8"
    addBooleanOption("Xdoclint:all,-missing", true)
    links("https://jd.papermc.io/paper/1.20.4/", "https://jspecify.dev/docs/api/")
  }
}

spotless {
  java {
    googleJavaFormat()
    licenseHeaderFile(rootProject.file("HEADER"))
  }
  kotlinGradle { ktfmt().googleStyle() }
}

mavenPublishing {
  publishToMavenCentral(automaticRelease = true)
  signAllPublications()

  pom {
    name.set("ShardAPI")
    description.set(
      "The public Java API of Shard, the machine-learning anticheat for Paper, Folia and Spigot servers"
    )
    inceptionYear.set("2026")
    url.set("https://github.com/KaelusAI/ShardAPI")
    licenses {
      license {
        name.set("The Apache License, Version 2.0")
        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
        distribution.set("repo")
      }
    }
    developers {
      developer {
        id.set("kaelusai")
        name.set("KaelusAI")
        url.set("https://github.com/KaelusAI")
      }
    }
    scm {
      url.set("https://github.com/KaelusAI/ShardAPI")
      connection.set("scm:git:https://github.com/KaelusAI/ShardAPI.git")
      developerConnection.set("scm:git:ssh://git@github.com/KaelusAI/ShardAPI.git")
    }
  }
}
