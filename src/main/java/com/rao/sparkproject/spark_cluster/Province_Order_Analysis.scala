package com.rao.sparkproject.spark_cluster

import org.apache.spark.sql.{SaveMode, SparkSession}

object Province_Order_Analysis {
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
    spark.sql("select split(address,' ')[0] as province_name,count(split(address,' ')[0]) as count from df1 where order_status in ('待收货','待发货','已完成') group by split(address,' ')[0]")
      .write
      //连接数据库
      .format("jdbc")
      .option("url", "jdbc:mysql://127.0.0.1:3306/e_commerce?useUnicode=true&characterEncoding=utf-8&useSSL=false")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("dbtable", "tb_province_order")
      .option("user", "root")
      .option("password", "123456")
      //不破坏数据表结构，在后添加
      .mode(SaveMode.Append)
      .save()
  }
}