name := """horoscopes-meugeninua"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs
)

libraryDependencies += "org.postgresql" % "postgresql" % "9.3-1102-jdbc41"

libraryDependencies += "com.google.code.gson" % "gson" % "2.2.2"

libraryDependencies += "org.jsoup" % "jsoup" % "1.8.1"
