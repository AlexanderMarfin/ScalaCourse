import functions.ColumnFunctions.{ calcFunc, incFunc, idxCalcFunc }
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class ColumnFunctionsSpec extends AnyWordSpecLike with Matchers {
  //название тестируемой функции или описание теста
  "incFunc" should {
    //описание ожидаемого результата теста или имя конкретного теста
    "return correct result" in {

      val inputValue    = 1
      val expectedValue = inputValue + 1
      // проверка соответствия полученного результата ожидаемому
      incFunc(inputValue) shouldBe expectedValue
    }

  }
  //название тестируемой функции или описание теста
  "calcFunc" should {
    //описание ожидаемого результата теста или имя конкретного теста
    "return correct result" in {

      val inputValue    = 7
      val number        = 12
      val expectedValue = 5
      // проверка соответствия полученного результата ожидаемому
      calcFunc(inputValue, number) shouldBe expectedValue
    }

  }
  //название тестируемой функции или описание теста
  "idxCalcFunc" should {
    //описание ожидаемого результата теста или имя конкретного теста
    "return correct result" in {

      val value1        = 5
      val value2        = 8
      val value3        = -10
      val expectedValue = 4
      // проверка соответствия полученного результата ожидаемому
      idxCalcFunc(value1, value2, value3) shouldBe expectedValue
    }

  }

}
