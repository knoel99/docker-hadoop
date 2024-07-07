import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.avro.functions._
import org.apache.spark.sql.functions._

object CustomerTransactionProcessor {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Customer Transactions Processor")
      .getOrCreate()

    import spark.implicits._

    val avroSchema = 
      """{
        "type": "record",
        "name": "CustomerTransaction",
        "fields": [
          {"name": "customer_id", "type": "string"},
          {"name": "transaction_id", "type": "string"},
          {"name": "amount", "type": "double"},
          {"name": "timestamp", "type": "long"}
        ]
      }"""

    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "kafka:9092")
      .option("subscribe", "customer_transactions")
      .load()

    val avroDF = df.select(from_avro(col("value"), avroSchema) as "data")
      .select("data.*")

    val query = avroDF
      .writeStream
      .outputMode("append")
      .format("org.apache.hadoop.hbase.spark")
      .option("hbase.table", "customer_transactions")
      .option("hbase.columns.mapping", 
        "ROW_KEY STRING :key, " +
        "cf:customer_id STRING customer_id, " +
        "cf:transaction_id STRING transaction_id, " +
        "cf:amount DOUBLE amount, " +
        "cf:timestamp BIGINT timestamp")
      .option("hbase.use.hbase.context", true)
      .option("hbase.config.resources", "/etc/hbase/conf/hbase-site.xml")
      .start()

    query.awaitTermination()
  }
}