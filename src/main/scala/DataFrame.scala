package src.main.Scala
import org.apache.spark.sql.{SparkSession}
import org.apache.spark.sql.functions._
object DataFrame extends App{
  val spark = SparkSession.builder()
    .master("local[1]")
    .appName("SparkOtus")
    .getOrCreate();
  println(spark)
  println("Spark Version : " + spark.version)


  val df1 = spark.read.parquet("src/main/resources/data/yellow_taxi_jan_25_2018")
  val df2 = spark.read.option("header", true).csv("src/main/resources/data/taxi_zones.csv")
  val result = df1.join(df2, df1("DOLocationID") === df2("LocationID"), "left").select("Borough","passenger_count").groupBy("Borough")
      .agg(count("passenger_count").alias("count")).orderBy(col("count").desc)

  result.show()
  result.write.parquet("src/main/resources/data/Borough.parquet")

}