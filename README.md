# About
* 100% Kotlin
* AndroidX
* MVVM
* Clean Architecture
* Not updating details view every second, since GitHub only allows up to
10 requests per minute. So it gets updated every 10 seconds.

## Testing
* UnitTests with JUnit.
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