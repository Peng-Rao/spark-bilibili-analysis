# 基于头歌的电商平台订单数据分析项目

## Table of Contents

## Background

## Install
### Install Spark
```agsl
docker pull bitnami/spark
```
### Install MySQL
```agsl
docker pull mysql:5.7
```
### Install Node.js packages
```agsl
cd web
npm install
```

### Install python flask
```agsl
pip install flask
pip install flask_cors
pip install flask_mysqldb
```

## Usage
### standalone mode
1. start mysql
```agsl
docker run -it --rm --name mysql -e MYSQL_ROOT_PASSWORD=123456 -p 3306:3306 mysql:5.7
```
2. start spark
```agsl
docker run -it --rm --name spark -p 8080:8080 -p 7077:7077 -p 6066:6066 -p 4040:4040 -e SPARK_MODE=standalone bitnami/spark
```
3. change the spark and scala version in pom.xml
```agsl
<dependency>
    <groupId>org.apache.spark</groupId>
    <artifactId>spark-sql_2.12</artifactId>
    <version>{spark.version}</version>
</dependency>
<!--Spark Core-->
<!-- https://mvnrepository.com/artifact/org.apache.spark/spark-core -->
<dependency>
    <groupId>org.apache.spark</groupId>
    <artifactId>spark-core_2.12</artifactId>
    <version>{spark.version}</version>
```
4. run the scala script  
after run the scala project, you can see the result in the mysql database
5. start the web server
```agsl
cd server
python app.py
```
6. start the web client
```agsl
cd web
npm run serve
```
7. open the browser and visit the url: http://localhost:8080

### cluster mode
if you want to run the project in cluster mode, you need to change the spark container based on hadoop
```agsl
docker pull s1mplecc/spark-hadoop:3
git clone https://github.com/s1mplecc/spark-hadoop-docker.git
cd spark-hadoop-docker
// start the container
docker-compose up -d
// enter the container
docker exec -it spark-hadoop-master bash
./start-hadoop.sh
// exit the container
exit
```
#### Web UI
|           Web UI            |          默认网址          |                         备注                         |
|:---------------------------:|:----------------------:|:--------------------------------------------------:|
|  \* **Spark Application**   | http://localhost:4040  | 由 SparkContext 启动，显示以本地或 Standalone 模式运行的 Spark 应用 |
|   Spark Standalone Master   | http://localhost:8080  |        显示集群状态，以及以 Standalone 模式提交的 Spark 应用        |
|    \* **HDFS NameNode**     | http://localhost:9870  |                   可浏览 HDFS 文件系统                    |
| \* **YARN ResourceManager** | http://localhost:8088  |               显示提交到 YARN 上的 Spark 应用               |
|      YARN NodeManager       | http://localhost:8042  |                  显示工作节点配置信息和运行时日志                  |
|    MapReduce Job History    | http://localhost:19888 |                   MapReduce 历史任务                   |

then you can run the project in cluster mode
