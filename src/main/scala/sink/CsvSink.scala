package sink
import config.JobConfig
import org.apache.spark.sql.DataFrame
//класс отвечающий за запись датафрейма в csv файл
class CsvSink(jobConfig: JobConfig) {
  //облегчаем доступ к параметрам конфига блока sink
  import jobConfig.sink._
  //описываем метод записи датафрейма в csv файл
  def write: DataFrame => Unit = {
    // передаем параметры mode, path и options из блока sink конфига
    df => df.write.mode(mode).options(options).csv(path)
  }
}
