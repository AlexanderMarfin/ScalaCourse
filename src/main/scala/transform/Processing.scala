package transform
//импорт объекта DataProcessingFunctions для доступа к функции processDfs
import functions.DataProcessingFunctions._
import org.apache.spark.sql.DataFrame
//класс описывающий метод преобразования данных датафрейма
class Processing {
  //описание метода преобразования данных
  //применение функции processDfs из объекта DataProcessingFunctions
  def transform: ((DataFrame, DataFrame)) => DataFrame = processDfs
}
