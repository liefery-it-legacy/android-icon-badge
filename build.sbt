lazy val root = project.in( file( "." ) )
    .enablePlugins( AndroidLib )
    .settings( Settings.common )
    .settings(
        libraryDependencies ++=
            "com.android.support" % "appcompat-v7" % "26.0.1" ::
            Nil,
        name := "icon-badge",
        publishArtifact in ( Compile, packageDoc ) := false
    )

lazy val sample = project
    .enablePlugins( AndroidApp )
    .settings( Settings.common )
    .settings(
        organization := organization.value + ".icon_badge.sample",
        run := ( run in Android ).evaluated
    )
    .dependsOn( root )