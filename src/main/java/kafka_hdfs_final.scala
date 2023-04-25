import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.{Seconds, StreamingContext}
object kafka_hdfs_final {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setAppName("KafkaToHdfsExample")
      .setMaster("local[*]")
    val ssc = new StreamingContext(sparkConf, Seconds(5))
    val kafkaParams = Map[String, Object](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "localhost:9092",
      ConsumerConfig.GROUP_ID_CONFIG -> "kafka-to-hdfs",
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer],
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer]
    )
    val topics = Array("topic_log")
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams)
    )
    val outputPath = "hdfs://localhost:8020/fka_logs/test.log"
    stream.map(_.value).foreachRDD {
      rdd =>
        val spark = SparkSession.builder.config(rdd.sparkContext.getConf).getOrCreate()
        import spark.implicits._
        val df = rdd.toDF()
        df.write.mode("append").text(outputPath)
    }
    ssc.start()
    ssc.awaitTermination()
  }
}