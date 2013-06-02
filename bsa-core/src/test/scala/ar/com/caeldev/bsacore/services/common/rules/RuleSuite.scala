package ar.com.caeldev.bsacore.services.validations

import org.scalatest.{ GivenWhenThen, PropSpec }
import org.scalatest.prop.TableDrivenPropertyChecks
import ar.com.caeldev.bsacore.config.ConfigContext

class RuleSuite extends PropSpec with GivenWhenThen with TableDrivenPropertyChecks with Rule[String] {

  val ruleNotEmpty = { x: String =>
    var result: Either[Success, Error] = Left(Success.create())
    x.isEmpty match {
      case true  => { result = Right(Error.create(1000)) }
      case false => {}
    }
    result
  }

  property("should not be create an Error Class from an invalid code") {
    Given("an invalid code error")
    val code: Long = 1002122

    When("try to create an error from the code")
    Then("should not create it raising a ConfigException")
    intercept[com.typesafe.config.ConfigException] {
      val error: Error = Error.create(code)
    }
  }

  property("should create an Error Class from an valid code") {
    Given("an invalid code error")
    val code: Long = 1000
    And("a load all the errors from conf file")
    val config: ConfigContext = new ConfigContext("errors.conf")

    When("try to create an error from the code")
    val error: Error = Error.create(code)

    Then("should be created successfully")
    assert(error.description === config.get("errors.validations."+code.toString+".description"))
  }

  property("should validate an String that is empty with a given Function") {
    Given("an empty String to be evaluted")
    And("a Function with a specific validation that is NotEmpty")
    val valueTest: String = new String("")

    When("execute the validation with the function NotEmpty String")
    val result: Either[Success, Error] = isValid(valueTest, ruleNotEmpty)

    Then("should not pass the validation.")
    assert(result.isRight)
  }

  property("should validate an String that is not empty with a given function") {
    Given("a not empty String to be evaluted")
    val valueTest: String = new String("asd")

    When("execute the validation with the function NotEmpty String")
    val result: Either[Success, Error] = isValid(valueTest, ruleNotEmpty)

    Then("should pass the validation.")
    assert(result.isLeft)
  }

  property("should validate two Strings that can't empty with a given function") {
    Given("a not empty String to be evaluted")
    val firstValueTest: String = new String("asd")
    And("a second not empty string to be evaluated")
    val secondValueTest: String = new String("asasasdas")

    When("execute the validation on both Strings with the function NotEmpty String")
    val result: Either[Success, Error] = areValid(List(firstValueTest, secondValueTest), ruleNotEmpty)

    Then("should pass the validation.")
    assert(result.isLeft)
  }

  property("should validate Both empty Strings, whose can't empty with a given function") {
    Given("an empty String to be evaluted")
    val firstValueTest: String = new String("")
    And("a second not empty string to be evaluated")
    val secondValueTest: String = new String("")

    When("execute the validation on both Strings with the function NotEmpty String")
    val result: Either[Success, Error] = areValid(List(firstValueTest, secondValueTest), ruleNotEmpty)

    Then("should not pass the validation.")
    assert(result.isRight)
  }

}
