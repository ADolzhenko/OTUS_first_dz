import Dependencies.library

ThisBuild / organization := "OTUS"
ThisBuild / scalaVersion := "2.13.12"
ThisBuild / version := "LATEST"

scalacOptions ++= Seq("-target:jvm-1.8")


lazy val root = (project in file("."))
  .settings(
    name := "test-project",
    libraryDependencies ++= Seq(
      library.sparkSql
    )

  )