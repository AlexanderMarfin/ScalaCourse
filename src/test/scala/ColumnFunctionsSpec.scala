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

      val inputValue    = 20
      val number        = 30
      val expectedValue = 485
      // проверка соответствия полученного результата ожидаемому
      calcFunc(inputValue, number) shouldBe expectedValue
    }

  }
  //название тестируемой функции или описание теста
  "idxCalcFunc" should {
    //описание ожидаемого результата теста или имя конкретного теста
    "return correct result" in {

      val value1        = 1
      val value2        = 1
      val expectedValue = 0.5
      // проверка соответствия полученного результата ожидаемому
      idxCalcFunc(value1, value2) shouldBe expectedValue
    }

  }

}
