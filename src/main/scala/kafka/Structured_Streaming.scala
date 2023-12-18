package kafka


import org.apache.spark.sql.{SparkSession}
import org.apache.spark.sql.types.{StringType, StructType}
import org.apache.spark.sql.functions.{col, from_json}

object Structured_Streaming extends App {
  val KAFKA_TOPIC = "books"
  val KAFKA_SERVER = "kafka:9092"
  val spark = SparkSession.builder()
    .master("local[1]")
    .appName("SparkOtus")
    .getOrCreate();
  println(spark)
  println("Spark Version : " + spark.version)

  import spark.implicits._


  val emp_schema = new StructType()
    .add("Name", StringType, true)
    .add("Author", StringType, true)
    .add("User Rating", StringType, true)
    .add("Reviews", StringType, true)
    .add("Price", StringType, true)
    .add("Year", StringType, true)
    .add("Genre", StringType, true)

  // Subscribe to books topic
  val df = spark
    .readStream
    .format("kafka")
    .option("kafka.bootstrap.servers", KAFKA_SERVER)
    .option("subscribe", KAFKA_TOPIC)
    .option("startingOffsets", "earliest")
    .load()

  val from_kafka = df.select(from_json(col("value").cast("string"), emp_schema).alias("parsed_value"))
    .select(col("parsed_value.Name"), col("parsed_value.Author"), col("parsed_value.User Rating"), col("parsed_value.Reviews"), col("parsed_value.Price"), col("parsed_value.Year"), col("parsed_value.Genre"))
    .filter(col("parsed_value.User Rating").lt(4))
    .writeStream
    .format("parquet")
    .option("path", "src/main/resources/data/Structured_Streaming")
    .option("checkpointLocation", "src/main/resources/checkpoint")
    .outputMode("append")
    .start()


  from_kafka.awaitTermination();

}