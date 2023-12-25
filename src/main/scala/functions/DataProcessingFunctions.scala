package functions

import functions.ColumnFunctions.{ calcFuncUdf, idxCalcFuncUdf, incFuncUdf }
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
//объект с набором функций по обработке данных датафрейма
object DataProcessingFunctions {

  val processMainDf: DataFrame => DataFrame = df => {
    df.withColumn("calc_param", calcFuncUdf(col("duration"), lit(30)))
      .withColumn("pdays", incFuncUdf(col("pdays")))
      .withColumn("calc_idx_1", idxCalcFuncUdf(col("emp_var_rate"), col("cons_price_idx")))
  }

  val processOtherDf: DataFrame => DataFrame = df => {
    df.withColumn(
      "calc_idx_2",
      idxCalcFuncUdf(col("cons_conf_idx"), col("euribor3m"))
    )
      .withColumnRenamed("id", "other_id")
  }

  val extractOldMarriedLoaners: DataFrame => DataFrame = _.where(
    col("loan") === "yes" and
      col("marital") === "married" and
      col("age") > 60 and
      col("education") === "university.degree" and
      col("job") =!= "retired" and
      col("pdays") >= 100
  )

  val processResultDf: DataFrame => DataFrame = df => {
    df.withColumn("calc_idx_result", idxCalcFuncUdf(col("calc_idx_1"), col("calc_idx_2")))
      .select("id", "loan", "marital", "age", "education", "job", "pdays", "calc_param", "calc_idx_result")
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
