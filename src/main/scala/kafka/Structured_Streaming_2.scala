package kafka

import org.apache.spark.sql.functions.{struct, to_json}
import org.apache.spark.sql.SparkSession

object Structured_Streaming_2 extends App {
  val KAFKA_TOPIC = "books"
  val KAFKA_SERVER = "kafka:9092"
  val spark = SparkSession.builder()
    .master("local[1]")
    .appName("SparkOtus")
    .getOrCreate();
  println(spark)
  println("Spark Version : " + spark.version)

  val csv_to_json = spark.read.option("header", true).csv("src/main/resources/data/bestsellers_with_categories.csv")
  csv_to_json.select(to_json(struct("*")).as("value"))
    .selectExpr("CAST(value AS STRING)")
    .write
    .format("kafka")
    .option("kafka.bootstrap.servers", KAFKA_SERVER)
    .option("topic", KAFKA_TOPIC)
    .save()

}