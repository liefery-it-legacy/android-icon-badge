lazy val stopBadge = project.in( file( "." ) )
    .enablePlugins( AndroidLib )
    .settings( Settings.common )
    .settings(
        libraryDependencies ++=
            "com.android.support" % "support-annotations" % "25.3.1" ::
            Nil,
        name := "stop-badge",
        publishArtifact in ( Compile, packageDoc ) := false
    )

lazy val sample = project
    .enablePlugins( AndroidApp )
    .settings( Settings.common )
    .settings(
        organization := organization.value + ".stop.badge.sample",
        run := ( run in Android ).evaluated
    )
    .dependsOn( stopBadge )