import org.apache.spark.sql.{SaveMode, SparkSession}

object Price_range_analysis {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("SparkCleanJob")
      .master("local[*]").getOrCreate()
    //获取 bilibili_order 表数据
    val df1 = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://127.0.0.1:3306/e_commerce?useUnicode=true&characterEncoding=utf-8&useSSL=false")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("dbtable", "bilibili_order")
      .option("user", "root")
      .option("password", "123456")
      .load();
    df1.createOrReplaceTempView("df1")
    //创建 UDF 函数
    spark.udf.register("price_range", (item_price: Double) => {
      var range = ""
      if (item_price < 50) {
        range = "50元以下"
      } else if (item_price >= 50 && item_price < 100) {
        range = "50元至100元以内"
      } else if (item_price >= 100 && item_price < 150) {
        range = "100元至150元以内"
      } else if (item_price >= 150 && item_price < 200) {
        range = "150元至200元以内"
      } else if (item_price >= 200) {
        range = "200元以上"
      }
      range
    })
    //使用 UDF 函数查询价格分布情况，并添加至数据库中
    spark.sql("select price_range(item_price) as price_range,count(*) as count from df1 group by price_range(item_price) order by count desc")
      .repartition(1)
      .write
      //连接数据库
      .format("jdbc")
      .option("url", "jdbc:mysql://127.0.0.1:3306/e_commerce?useUnicode=true&characterEncoding=utf-8&useSSL=false")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("dbtable", "tb_price_range")
      .option("user", "root")
      .option("password", "123456")
      //不破坏数据表结构，在后添加
      .mode(SaveMode.Append)
      .save()

    /** ********* End ********* */
  }
}