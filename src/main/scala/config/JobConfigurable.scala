package config

import org.apache.spark.sql.SparkSession

trait JobConfigurable {
  //вызываем метод apply() класса JobConfig и получаем конфиг
  val jobConfig: JobConfig = JobConfig()
  //облегчаем доступ к полям jobName и master из блока spark
  import jobConfig.spark._
  //расширяем класс SparkSession.Builder дополнительным методом withMaster
  implicit class SparkSessionEnrichment(builder: SparkSession.Builder) {
    //подставляем значение в master если он определен в конфиге
    def withMaster(master: String): SparkSession.Builder = if (master.isEmpty) builder else builder.master(master)
  }
  //создаем spark сессию с параметрами из конфиг файла
  implicit val spark: SparkSession = SparkSession.builder.appName(jobName).withMaster(master).getOrCreate()

}
