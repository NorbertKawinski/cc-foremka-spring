# Development

This file contains instructions for project setup, developing and publishing the library.



# First time setup

Configure `~/.gradle/gradle.properties` with your PGP signing keys and password.

```
signing.keyId=AAA
signing.password=BBB
signing.secretKeyRingFile=/CCC/DDD.pgp
```


# Testing the library

Run the `test` Gradle task:

```
./gradlew test
```



# Publishing to local repository
Run the `publishToMavenLocal` Gradle task:

```
./gradlew publishToMavenLocal
```

This step is usually done if you want to test the library in a local project before publishing it to Maven Central.



# Publishing to Maven Central

As of June 30, 2025, Maven Central no longer supports the legacy deployment protocol used by the Gradle's publishing plugin.  
Until good Gradle integration is available, you can publish the artifact to a local repository first,  
and then upload it manually to Maven Central using their web UI (https://central.sonatype.com/publishing/namespaces)  
You can get the .zip file ready for upload by running `packageForMavenCentral` Gradle task
