name := """horoscopes-meugeninua"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)

scalaVersion := "2.11.6"

routesGenerator := InjectedRoutesGenerator

includeFilter in (Assets, LessKeys.less) := "*.less"

excludeFilter in (Assets, LessKeys.less) := "_*.less"

LessKeys.compress := true

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

libraryDependencies += "org.postgresql" % "postgresql" % "9.3-1102-jdbc41"

libraryDependencies += "com.google.code.gson" % "gson" % "2.2.2"

libraryDependencies += "org.jsoup" % "jsoup" % "1.8.1"

libraryDependencies += "joda-time" % "joda-time" % "2.7"

libraryDependencies += "org.webjars" % "jquery" % "2.1.4"

libraryDependencies += "org.webjars" % "bootstrap" % "3.3.5"

libraryDependencies += "org.webjars" % "jquery-ui" % "1.11.4"

