import sbtassembly.AssemblyPlugin.autoImport._

name := "SparkRDDSum"
version := "1.0"
scalaVersion := "2.12.15"  // Use Scala 2.12 for better compatibility with Spark

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.4.1" % "provided"
)

// Assembly settings
assembly / assemblyJarName := "sparkrddsum-assembly-1.0.jar"

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

// Add this line to make the assembly task available
lazy val root = (project in file(".")).enablePlugins(AssemblyPlugin)

// Java 8 compatibility
javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

// Exclude Scala library from assembly
assembly / assemblyOption := (assembly / assemblyOption).value.withIncludeScala(false)