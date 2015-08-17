
name := "Spark Demo Applications"
version := "0.1.0"
organization := "com.mammothdata"
scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.10" % "1.4.1" % "provided",
  "org.apache.spark" %% "spark-mllib" % "1.4.1" % "provided",
  "org.apache.spark" %% "spark-sql" % "1.4.1" % "provided",
  "org.apache.spark" % "spark-streaming_2.10" % "1.4.1" % "provided",
  "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.4.1",

)

jarName in assembly := "spark-demo.jar"


assemblyMergeStrategy in assembly := {
  case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class") => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}