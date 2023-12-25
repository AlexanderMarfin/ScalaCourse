package functions

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf
//объект с набором пользовательских функций для работы с колонками датафрейма
object ColumnFunctions {
  //описание функций
  val incFunc: Int => Int                  = _ + 1
  val calcFunc: (Int, Int) => Int          = (value: Int, n: Int) => (1 to n).foldLeft(value)(_ + _)
  val idxCalcFunc: (Float, Float) => Float = (p1: Float, p2: Float) => (p1 * p2) / (p1 + p2)
  //преобразование в udf
  val incFuncUdf: UserDefinedFunction     = udf(incFunc)
  val calcFuncUdf: UserDefinedFunction    = udf(calcFunc)
  val idxCalcFuncUdf: UserDefinedFunction = udf(idxCalcFunc)
  //регистрация udf функций для использования в sql выражениях
  // spark.sql("select column1, incFunc(column2) from tableName")
  def registerUdf(implicit spark: SparkSession): Unit = {
    spark.udf.register("incFunc", incFuncUdf)
    spark.udf.register("calcFunc", calcFuncUdf)
    spark.udf.register("idxCalcFunc", idxCalcFuncUdf)
  }

  //NOTE: использование udf функций затратно с точки зрения производительности,
  //допустимо их использование только если нет возможности реализовать их средствами готового набора функций spark

}
