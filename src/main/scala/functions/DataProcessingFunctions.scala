package functions

import functions.ColumnFunctions.{ calcFuncUdf, idxCalcFuncUdf, changeFuncUdf }
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
//объект с набором функций по обработке данных датафрейма
object DataProcessingFunctions {

  val processMainDf: DataFrame => DataFrame = df => {
    df.withColumn("age", changeFuncUdf(col("age")))
      .withColumn("calc_param_new", calcFuncUdf(col("age"), col("duration")))
  }

  val processOtherDf: DataFrame => DataFrame = df => {
      df.withColumn("calc_idx_new", idxCalcFuncUdf(col("cons_conf_idx"), col("euribor3m"), col("nr_employed")))
      .withColumnRenamed("id", "other_id")
  }

  val extractOldMarriedLoaners: DataFrame => DataFrame = _.where(
    col("loan") === "no" and
      col("marital") === "single" and
      col("job") === "admin." and
      col("pdays") >= 100
  )

  val processResultDf: DataFrame => DataFrame = df => {
    df.select("id", "loan", "marital", "job", "pdays", "calc_param_new", "calc_idx_new")
  }

  val processDfs: ((DataFrame, DataFrame)) => DataFrame = dfs => {
    val (df1, df2)  = dfs
    val mainDf      = processMainDf(df1)
    val otherDf     = processOtherDf(df2)
    val joinedDf    = mainDf.join(otherDf, mainDf("id") === otherDf("other_id"), "inner")
    val extractedDf = extractOldMarriedLoaners(joinedDf)
    val resultDf    = processResultDf(extractedDf)
    resultDf
  }

}
