### Expandable Compose Calendar
![Expandable Compose Calendar](art/banner.png)

Integrate calendar with your Jetpack Compose project.

[![Java CI with Gradle](https://github.com/mateusz800/Expandable-Compose-Calendar/actions/workflows/gradle.yml/badge.svg)](https://github.com/mateusz800/Expandable-Compose-Calendar/actions/workflows/gradle.yml)
[![](https://jitpack.io/v/mateusz800/Expandable-Compose-Calendar.svg)](https://jitpack.io/#mateusz800/Expandable-Compose-Calendar)

### Introduction
A calendar with a week view that can be expanded to a month view


### Setup
1. Configure jitpack
2. Add the dependency
```gradle
dependencies {
  .....
  implementation 'com.github.mateusz800:Expandable-Compose-Calendar:ce5075e1d4'
  .....
}
```

### Usage
Add the `CalendarView` composable to your code:
```kotlin
CalendarView(onDayClick = {
    ....
})
```
