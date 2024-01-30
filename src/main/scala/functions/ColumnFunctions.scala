package functions

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf
//объект с набором пользовательских функций для работы с колонками датафрейма
object ColumnFunctions {
  //описание функций
  val incFunc: Int => Int                  = _ + 1
  val calcFunc: (Float, Float) => Float = (p1: Float, p2: Float) => (p1 - p2).abs
  val changeFunc: (Int) => String = (p1: Int) => if (p1>59) "Old" else if (p1<59 & 21<p1) "MiddleAge" else "Young"
  val idxCalcFunc: (Float, Float, Float) => Float = (p1: Float, p2: Float, p3: Float) => p1 * p2/p3.abs
  //преобразование в udf
  val incFuncUdf: UserDefinedFunction     = udf(incFunc)
  val calcFuncUdf: UserDefinedFunction    = udf(calcFunc)
  val changeFuncUdf: UserDefinedFunction    = udf(changeFunc)
  val idxCalcFuncUdf: UserDefinedFunction = udf(idxCalcFunc)
  //регистрация udf функций для использования в sql выражениях
  // spark.sql("select column1, incFunc(column2) from tableName")
  def registerUdf(implicit spark: SparkSession): Unit = {
    spark.udf.register("incFunc", incFuncUdf)
    spark.udf.register("calcFunc", calcFuncUdf)
    spark.udf.register("changeFunc", changeFuncUdf)
    spark.udf.register("idxCalcFunc", idxCalcFuncUdf)
  }

  //NOTE: использование udf функций затратно с точки зрения производительности,
  //допустимо их использование только если нет возможности реализовать их средствами готового набора функций spark

}
