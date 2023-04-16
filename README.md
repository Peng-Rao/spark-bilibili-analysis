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
    <version>${spark.version}</version>
</dependency>
<!--Spark Core-->
<!-- https://mvnrepository.com/artifact/org.apache.spark/spark-core -->
<dependency>
    <groupId>org.apache.spark</groupId>
    <artifactId>spark-core_2.12</artifactId>
    <version>${spark.version}</version>
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
