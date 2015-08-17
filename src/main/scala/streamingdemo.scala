package com.mammothdata.demos

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext._
import org.apache.spark.streaming._


object StreamingDemo extends java.io.Serializable {
  def main() {
    val ssc = new StreamingContext(new SparkConf().setAppName("StreamingLoggingDemo"), Seconds(1))
    val streamingLog = ssc.socketTextStream("localhost", 8888)
    val errors = streamingLog.filter( x => x.split("\\s+")(1) == "ERROR" )
    val errorsByIPMap = errors.map( x => ((x.split("\\s+")(0)), 1L))
    val errorsByIPAndWindow = errorsByIPMap.reduceByKeyAndWindow( {(x:Long, y:Long) => x + y}, Seconds(30), Seconds(5))
    ssc.start()
    ssc.awaitTermination()
  }
}