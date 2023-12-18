import Dependencies.library

ThisBuild / organization := "OTUS"
ThisBuild / scalaVersion := "2.12.12"
ThisBuild / version := "LATEST"


scalacOptions ++= Seq("-target:jvm-1.8")


lazy val root = (project in file("."))
  .settings(
    name := "test-project",
    libraryDependencies ++= Seq(
      library.sparkSql,
      library.kafka,
      library.kafka_sql,
      library.spark_core

    )

  )
assemblyMergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf")       => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$")   => MergeStrategy.discard
  case "reference.conf"                                 => MergeStrategy.concat
  case x: String if x.contains("UnusedStubClass.class") => MergeStrategy.first
  case _                                                => MergeStrategy.first
}