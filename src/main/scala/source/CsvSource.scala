package source
import config.JobConfig
import org.apache.spark.sql.{ DataFrame, SparkSession }
//класс отвечающий за чтение csv файла
class CsvSource(jobConfig: JobConfig) {
  //облегчаем доступ к параметрам конфига блока source
  import jobConfig.source._
  //описываем метод чтения csv файла
  def read: SparkSession => (DataFrame, DataFrame) =
    // получаем на вход spark сессию
    spark => {
      // передаем параметры path1, path2 и options из блока source конфига
      val df1 = spark.read
        .options(options)
        .csv(path1)

      val df2 = spark.read
        .options(options)
        .csv(path2)

      (df1, df2)
    }
}
