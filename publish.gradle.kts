import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register

gradle.allprojects {
  plugins.withId("java") {
    // Ensure publishing plugin is present once Java components exist
    plugins.apply("maven-publish")

    extensions.configure<PublishingExtension> {
      publications {
        register<MavenPublication>("gpr") {
          from(components["java"])
        }
      }
      repositories {
        maven {
          name = "GitHubPackages"
          url = uri(System.getenv("GPR_REPO") ?: error("Missing GPR_REPO"))
          credentials {
            username = System.getenv("GPR_USER") ?: error("Missing GPR_USER")
            password = System.getenv("GPR_KEY") ?: error("Missing GPR_KEY")
          }
        }
      }
    }
  }
}

