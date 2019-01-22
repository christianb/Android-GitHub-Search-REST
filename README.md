# About
* 100% Kotlin
* AndroidX
* MVP
* Clean Architecture
* No implementation of `refresh_token`

## Testing
* UnitTests with JUnit.
* Follow TDD.
* No lint warnings & error.
* Add Kotlin Detekt codestyle checker: `./gradlew detektCheck`
* UI Tests with Espresso. Network independent using OkReplay.
* Tested on a Nexus6 real device and Nexus5 emulator.

## Libraries
* Koin for a lightweight dependency injection
* Retrofit for network calls
* RxJava for reactive observation
* Mockito for mocking
* AssertJ for fluent assertions
* Glide for image url loading
* Use OkHttp interceptor to add auth token