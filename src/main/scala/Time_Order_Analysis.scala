import org.apache.spark.sql.{SaveMode, SparkSession}

object Time_Order_Analysis {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("SparkCleanJob")
      .master("local[*]").getOrCreate()
    val df1 = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://127.0.0.1:3306/e_commerce?useUnicode=true&characterEncoding=utf-8&useSSL=false")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("dbtable", "bilibili_order")
      .option("user", "root")
      .option("password", "123456")
      .load();
    df1.createOrReplaceTempView("df1")
    spark.sql("select hour(pay_time) as time_point,count(hour(pay_time)) as count from df1 where order_status in ('待收货','待发货','已完成') group by hour(pay_time) order by hour(pay_time)")
      .write
      //连接数据库
      .format("jdbc")
      .option("url", "jdbc:mysql://127.0.0.1:3306/e_commerce?useUnicode=true&characterEncoding=utf-8&useSSL=false")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("dbtable", "tb_time_order")
      .option("user", "root")
      .option("password", "123456")
      //不破坏数据表结构，在后添加
      .mode(SaveMode.Append)
      .save()
  }
}