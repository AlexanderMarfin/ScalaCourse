package config

import pureconfig.ConfigSource
import pureconfig.generic.auto._

object JobConfig {

  def apply(): JobConfig = {
    //указываем имя конфигурационного файла ждля парсинга
    val config = ConfigSource.resources("application.conf").load[JobConfig] match {
      // если парсинг прошел корректно, то отдаем результат
      case Right(conf) => conf
      // если парсинг прошел с ошибкой, то останавливаем работу и выкидываем Exception с текстом ошибки
      case Left(ex) =>
        throw new IllegalArgumentException(
          s"Invalid configuration found. Please check application.conf and JobConfig.scala. Error list: $ex"
        )
    }
    config
  }
}
// Класс, описывающий структуру конфигурационного файла(блоки spark, metrics, source и sink в application.conf)
case class JobConfig(spark: SparkConfig, metrics: Metrics, source: Source, sink: Sink)

// Класс, описывающий структуру блока spark из apllication.conf
// в поле jobName мапится значение из job-name из блока spark из apllication.conf
// в поле master мапится значение из master из блока spark из apllication.conf
case class SparkConfig(jobName: String, master: String)
// Класс, описывающий структуру блока source из apllication.conf
// в поле path1 мапится значение из path-1 из блока source из apllication.conf
// в поле path2 мапится значение из path-2 из блока source из apllication.conf
// в мапу options мапится значение из options из блока source из apllication.conf
case class Source(path1: String, path2: String, options: Map[String, String])
// Класс, описывающий структуру блока sink из apllication.conf
// в поле path мапится значение из path из блока sink из apllication.conf
// в поле mode мапится значение из mode из блока sink из apllication.conf
// в мапу options мапится значение из options из блока sink из apllication.conf
case class Sink(path: String, mode: String, options: Map[String, String])
// Класс, описывающий структуру блока metrics из apllication.conf
// в поле enable мапится значение из enable из блока metrics из apllication.conf
// в поле pushGateway мапится значение из pushGateway из блока metrics из apllication.conf
case class Metrics(enable: Boolean, pushGateway: String)