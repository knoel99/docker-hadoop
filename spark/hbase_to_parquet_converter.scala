import org.apache.spark.sql.SparkSession

object HBaseToParquetConverter {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("HBase to Parquet Converter")
      .getOrCreate()

    val hbaseDF = spark.read
      .format("org.apache.hadoop.hbase.spark")
      .option("hbase.table", "customer_transactions")
      .option("hbase.columns.mapping", 
        "ROW_KEY STRING :key, " +
        "cf:customer_id STRING customer_id, " +
        "cf:transaction_id STRING transaction_id, " +
        "cf:amount DOUBLE amount, " +
        "cf:timestamp BIGINT timestamp")
      .load()

    hbaseDF.write.parquet("hdfs://namenode:9000/data/customer_transactions_parquet")
  }
}