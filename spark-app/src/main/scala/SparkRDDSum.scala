import org.apache.spark.{SparkConf, SparkContext}

object SparkRDDSum {
  def main(args: Array[String]): Unit = {
    // Create a SparkConf object
    val conf = new SparkConf()
      .setAppName("SparkRDDSum")
      // Use local[*] for local testing, or comment this line to use the master specified in spark-submit
      .setMaster("local[*]")  

    // Create a SparkContext using the configuration
    val sc = new SparkContext(conf)

    try {
      // Create an array of integers from 0 to 100,000
      val numbers = (0 to 100000).toArray

      // Create an RDD from the array
      val numbersRDD = sc.parallelize(numbers)

      // Compute the sum
      val sum = numbersRDD.sum()

      // Print the result
      println(s"The sum of numbers from 0 to 100,000 is: $sum")
    } finally {
      // Stop the SparkContext
      sc.stop()
    }
  }
}