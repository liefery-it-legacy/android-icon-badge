lazy val root = project.in( file( "." ) )
    .enablePlugins( AndroidLib )
    .settings( Settings.common )
    .settings(
        libraryDependencies ++=
            "com.android.support" % "support-annotations" % "25.3.1" % "compile" ::
            Nil,
        name := "android-stop-badge",
        organization := "com.github.liefery",
        publishArtifact in ( Compile, packageDoc ) := false
    )

lazy val sample = project
    .enablePlugins( AndroidApp )
    .settings( Settings.common )
    .settings(
        organization := organization.value + ".stop_badge.sample",
        run := ( run in Android ).evaluated
    )
    .dependsOn( root )