import sbt._

object Version {
  object Scala {
    val v211                                = "2.11.12"
    val v212                                = "2.12.11"
    val supportedScalaVersions: Seq[String] = List(v211, v212)
  }

  val Spark          = "3.1.2"
  val PureConfig     = "0.17.4"
  val SparkMeasure   = "0.23"
  val ScalaTest      = "3.1.1"
}

object Dependencies {

  object Spark {
    val Core = "org.apache.spark" %% "spark-core" % Version.Spark % Provided
    val Sql  = "org.apache.spark" %% "spark-sql"  % Version.Spark % Provided
  }

  object Metrics {
    val SparkMeasure = "ch.cern.sparkmeasure" %% "spark-measure" % Version.SparkMeasure
  }

  object Common {
    val PureConfig = "com.github.pureconfig" %% "pureconfig" % Version.PureConfig
  }

  object Testing {
    val ScalaTest = "org.scalatest" %% "scalatest" % Version.ScalaTest % Test
  }

  val Dependencies: Seq[ModuleID] = Seq(
    Spark.Core,
    Spark.Sql,
    Common.PureConfig,
    Metrics.SparkMeasure,
    Testing.ScalaTest
  )
}
