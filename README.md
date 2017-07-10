# Android Stop Badge

> A simple circular badge widget to display numbers and shapes

[![](https://jitpack.io/v/liefery/android-stop-badge.svg)](https://jitpack.io/#liefery/android-stop-badge)

![Sample app screenshot](https://liefery.github.io/android-stop-badge/screenshot.png)

## Installation

### sbt

```scala
resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies += "com.github.liefery" % "android-stop-badge" % "1.0.5"
```

### Gradle

```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}

dependencies {
    compile 'com.github.liefery:android-gallery:1.0.5'
}
```

## Usage

Creating a Bitmap from the `StopBadgeFactory` class
```
Bitmap myBitmap = StopBadgeFactory.worker().fromArrowUp().setShadowColor(Color.RED).create()
```

Using the custom layout in XML:
```xml
<com.liefery.android.stop_badge.StopBadgeView
    android:layout_width="50dp"
    android:layout_height="50dp"
    app:stopBadge_circleColor="#4c8c4a"
    app:stopBadge_shapeColor="#003300"
    app:stopBadge_shadowColor="#000000"
    app:stopBadge_shadowDx="1dp"
    app:stopBadge_shadowDy="1dp"
    app:stopBadge_shadowRadius="3dp"
    app:stopBadge_stopNumber="123" />
```

Please have a look at the sample application for more details.