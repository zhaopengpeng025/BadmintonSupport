This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…![simulator_screenshot_9CCE7518-7E58-4B18-B440-D8A67D11F5B3.png](..%2F..%2F..%2F..%2F..%2Fvar%2Ffolders%2F20%2Fccdwybrd7wg7j5hxy89hxct00000gn%2FT%2Fsimulator_screenshot_9CCE7518-7E58-4B18-B440-D8A67D11F5B3.png)
