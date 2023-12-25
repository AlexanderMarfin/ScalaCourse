package metrics
import ch.cern.sparkmeasure.StageMetrics
import org.apache.spark.sql.SparkSession
//объект с методом подключающем сбор метрик spark measure
object Metrics {

  def withStageMetrics(pushGateway: String)(f: SparkSession => Unit)(implicit spark: SparkSession): Unit = {
    val stageMetrics = StageMetrics(spark)
    stageMetrics.begin()
    //вызов переданной в метод функции, запускающей spark job
    f(spark)
    stageMetrics.end()
    stageMetrics.printReport()
    stageMetrics.sendReportPrometheus(
      pushGateway,
      spark.sparkContext.appName,
      spark.sparkContext.appName,
      spark.sparkContext.appName
    )
  }
}
