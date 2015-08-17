package com.mammothdata.demos


import org.apache.spark.mllib.feature._
import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD


object KmeansDemo extends java.io.Serializable {    
  def featurize(s: String): Vector = {
      val tf = new HashingTF(1000)
      val bigram = s.sliding(2).toSeq
      tf.transform(bigram)
    }
    
    def build_model(text: RDD[String], numClusters: Int, numIterations: Int ): KMeansModel = {
      // Caches the vectors since it will be used many times by KMeans.
      val vectors = text.map(featurize).cache
      vectors.count()  // Calls an action to create the cache.
      KMeans.train(vectors, numClusters, numIterations)
    }
    
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("KmeansDemo")
    val sc = new SparkContext(conf)
    val log = sc.textFile("S3URL")
    val model = build_model(log, 10, 100)
    val predictedCluster = model.predict(featurize("This is a test string"))
  }
}