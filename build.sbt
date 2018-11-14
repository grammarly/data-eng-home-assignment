name := "data-eng-home-assignment"

version := "0.1"

scalaVersion := "2.11.12"

val sparkVersion = "2.4.0"

scalacOptions in ThisBuild ++= Seq("-feature", "-language:postfixOps", "-language:implicitConversions")

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)