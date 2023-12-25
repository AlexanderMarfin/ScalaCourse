package job
import config.JobConfigurable
import functions.ColumnFunctions
import log.JobLogging
import org.json4s.jackson.JsonMethods.pretty
import org.json4s.{ DefaultFormats, Extraction, Formats }
import sink.CsvSink
import source.CsvSource
import transform.Processing
import metrics.Metrics.withStageMetrics
//основной класс spark джобы с подключенными трейтами получения конфигов, spark сессии и логгера
class ProcessingJob extends JobConfigurable with JobLogging {

  def run(): Unit = {
    //получение представления конфигов в формате json для вывода в лог
    implicit val formats: Formats = DefaultFormats
    val jsonConfig                = pretty(Extraction.decompose(jobConfig))
    //вывод сообщения в лог с уровнем INFO
    log.info(s"Starting job with jobConfig: $jsonConfig")
    //регистрация udf функций(если они требуются)
    ColumnFunctions.registerUdf
    //получаем функцию получения датафрейма из источника(csv файла)
    val read      = new CsvSource(jobConfig).read
    //получаем функцию преобразования датафрейма
    val transform = new Processing().transform
    //получаем функцию записи результирующего датафрейма в csv файл
    val write     = new CsvSink(jobConfig).write
    //объединение(композиция) функций и получение результирующей функции
    val pipeline = read andThen transform andThen write

    import jobConfig.metrics._
    //вызов результирующей функции (), в зависимости от конфигурации сбора метрик
    if (enable) withStageMetrics(pushGateway) { pipeline } else pipeline(spark)
  }
}
