package com.rao.sparkproject.spark_standalone

import org.apache.spark.sql.{SaveMode, SparkSession}

object Ecommerce_Order_Clean {
  def main(args: Array[String]): Unit = {
    //bilibili2月8日订单.csv  数据集位置
    val data1 = "data/93e21029-efd4-4754-ad03-424d1b5f3c5e"
    //bilibili2月9日订单.csv  数据集位置
    val data2 = "data/0a3164f5-e82a-4b85-a9fa-7b351c6dd826"
    val spark = SparkSession.builder().appName("SparkCleanJob")
      .master("local[*]").getOrCreate()
    //读取2月8日数据文件，并消除首行
    val df1 = spark.read.option("delimiter", ",").option("header", true).csv(data1)
    //读取2月9日数据文件，并消除首行
    val df2 = spark.read.option("delimiter", ",").option("header", true).csv(data2)
    //创建临时视图df1
    df1.createOrReplaceTempView("df1")
    //创建临时视图df2
    df2.createOrReplaceTempView("df2")
    spark.sql("select item_no,item_name,item_price,address,channel,Place_Order_time,pay_time,pay_no,store_name,store_id,order_status from df1")
      //union 合并两天数据
      .union(spark.sql("select item_no,item_name,item_price,address,channel,Place_Order_time,pay_time,pay_no,store_name,store_id,order_status from df2"))
      //去重
      .distinct()
      .repartition(1)
      .write
      //连接数据库
      .format("jdbc")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("url", "jdbc:mysql://127.0.0.1:3306/e_commerce?useUnicode=true&characterEncoding=utf-8&useSSL=false")
      .option("dbtable", "bilibili_order")
      .option("user", "root")
      .option("password", "123456")
      //不破坏数据表结构，在后添加
      .mode(SaveMode.Append)
      .save()

    /** ********* End ********* */
  }
}