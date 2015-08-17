package com.mammothdata.demos


import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object LogDemo extends java.io.Serializable {    

  def errorFilter(line: String): Boolean = { line.split("\\s+")(1) == "ERROR" }

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("LogDemo")
    val sc = new SparkContext(conf)
    val log = sc.textFile("S3URL")
    val errors = log.filter(errorFilter(_))
    val errorsByIP = errors.map( x => (x.split(" ")(0), 1L))
    val errorsReduced = errorsByIP.reduceByKey( (x,y) => x + y ).cache
    errorsByIP.saveAsTextFile("errors")
  }
}