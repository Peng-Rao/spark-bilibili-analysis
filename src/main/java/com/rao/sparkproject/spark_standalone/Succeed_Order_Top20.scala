package com.rao.sparkproject.spark_standalone

import org.apache.spark.sql.{SaveMode, SparkSession}

object Succeed_Order_Top20 {
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
    spark.sql("select item_name,count(item_name) as count  from df1 where order_status in ('待收货','待发货','已完成') group by item_name order by count(item_name) desc limit 20")
      .repartition(1)
      .write
      //连接数据库
      .format("jdbc")
      .option("url", "jdbc:mysql://127.0.0.1:3306/e_commerce?useUnicode=true&characterEncoding=utf-8&useSSL=false")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("dbtable", "succeed_goods_top20")
      .option("user", "root")
      .option("password", "123456")
      //不破坏数据表结构，在后添加
      .mode(SaveMode.Append)
      .save()

    /** ********* End ********* */
  }
}