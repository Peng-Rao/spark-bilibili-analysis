package com.rao.sparkproject.spark_standalone

import org.apache.spark.sql.{SaveMode, SparkSession}

object Status_Contrast_Analysis {
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
    spark.sql("select day(Place_Order_time) as date_day , order_status,count(order_status) as count from df1 group by day(Place_Order_time),order_status order by day(Place_Order_time)")
      .write
      //连接数据库
      .format("jdbc")
      .option("url", "jdbc:mysql://127.0.0.1:3306/e_commerce?useUnicode=true&characterEncoding=utf-8&useSSL=false")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("dbtable", "tb_status_contrast")
      .option("user", "root")
      .option("password", "123456")
      //不破坏数据表结构，在后添加
      .mode(SaveMode.Append)
      .save()

    /** ********* End ********* */
  }
}