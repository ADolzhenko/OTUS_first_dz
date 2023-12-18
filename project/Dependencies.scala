import sbt._

object Dependencies{
  val library:Object{
    val sparkSql:ModuleID
    val kafka:ModuleID
    val kafka_sql:ModuleID
    val spark_core:ModuleID
  }=new{
    object Version{
      lazy val spark="3.4.1"
    }
    val spark_core= "org.apache.spark" %% "spark-core" % Version.spark
    val sparkSql="org.apache.spark"%%"spark-sql"%Version.spark %"provided"
    val kafka= "org.apache.spark" %%"spark-streaming" % Version.spark % "provided"
    val kafka_sql= "org.apache.spark" %%"spark-sql-kafka-0-10" % Version.spark

  }
}

