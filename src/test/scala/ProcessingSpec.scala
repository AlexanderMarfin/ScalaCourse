import functions.DataProcessingFunctions.extractOldMarriedLoaners
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.types.{ IntegerType, StringType, StructField, StructType }
import org.apache.spark.sql.{ Row, SparkSession }
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

import scala.collection.convert.ImplicitConversionsToJava._

class ProcessingSpec extends AnyWordSpecLike with Matchers {
  // создаем spark сессию для запуска тестирования
  private val spark = SparkSession.builder().appName("test").master("local[1]").getOrCreate()
  //название тестируемой функции или описание теста
  "extractOldMarriedLoaners" should {
    //описываем структуру тестовых данных и получаем схему
    val schema = StructType(
      Seq(
        StructField("age", IntegerType),
        StructField("job", StringType),
        StructField("loan", StringType),
        StructField("marital", StringType),
        StructField("education", StringType),
        StructField("pdays", IntegerType),
        StructField("duration", IntegerType)
      )
    )
    //описание ожидаемого результата теста или имя конкретного теста
    "return correct DataFrame" in {
      //подготавливаем тестовые данные для конкретного теста
      val testData = Seq(
        Row(31, "self-employed", "no", "single", "university", 2000, 20),
        Row(32, "admin.", "no", "single", "university", 20, 20),
        Row(30, "admin.", "no", "single", "university", 2000, 20),
        Row(33, "admin.", "no", "married", "university", 2000, 20),
        Row(34, "admin.", "yes", "married", "university", 2000, 20),
      )
      //создаем датафрейм с тестовыми данными
      val testDf = spark.createDataFrame(testData, schema)
      //вызываем тестируемую функцию на тестовых данных
      val resultDf = extractOldMarriedLoaners(testDf)
      //проверяем результат
      resultDf
        .filter(
          col("loan") === "no" and
            col("marital") === "single" and
            col("pdays") >= 100 and
            col("job") === "admin."
        )
        .count() shouldBe 1
    }

  }

}
