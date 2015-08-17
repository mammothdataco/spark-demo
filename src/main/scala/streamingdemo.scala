package com.mammothdata.demos

import org.apache.spark.streaming.StreamingSparkContext
import org.apache.spark.SparkConf

object StreamingDemo extends java.io.Serializable {
  def main() {
    val ssc = new StreamingSparkContext(new SparkConfig("StreamingLoggingDemo"), Seconds(1))
    val streamingLog = ssc.socketTextStream("localhost", 8888)
    val errors = streamingLog.filter( x => x.split("\\s+")(1) == "ERROR" )
    val errorsByIPMap = errors.map( x => ((x.split("\\s+")(0)), 1L))
    val errorsByIPAndWindow = errorsByIPMap.reduceByKeyAndWindow( {(x, y) => x + y}, Seconds(30), Seconds(5))
    ssc.start()
    ssc.awaitTermination()
  }
}