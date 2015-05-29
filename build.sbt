name := """horoscopes-meugeninua"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

libraryDependencies += "org.postgresql" % "postgresql" % "9.3-1102-jdbc41"

libraryDependencies += "com.google.code.gson" % "gson" % "2.2.2"

libraryDependencies += "org.jsoup" % "jsoup" % "1.8.1"

libraryDependencies += "joda-time" % "joda-time" % "2.7"

libraryDependencies += "org.springframework" % "spring-core" % "4.1.6.RELEASE"

libraryDependencies += "org.springframework" % "spring-context" % "4.1.6.RELEASE"

libraryDependencies += "org.springframework" % "spring-beans" % "4.1.6.RELEASE"

libraryDependencies += "org.springframework" % "spring-aop" % "4.1.6.RELEASE"

