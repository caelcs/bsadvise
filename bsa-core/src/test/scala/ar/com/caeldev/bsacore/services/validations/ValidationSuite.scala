package ar.com.caeldev.bsacore.services.validations

import org.scalatest.{ GivenWhenThen, PropSpec }
import org.scalatest.prop.TableDrivenPropertyChecks

class ValidationSuite extends PropSpec with GivenWhenThen with TableDrivenPropertyChecks with Validation[String] {

  property("should validate an String that is empty with a given Function") {
    Given("an empty String to be evaluted")
    val valueTest: String = new String("")

    And("a Function with a specific validation that is NotEmpty")
    val functionTest = { x: String => !x.isEmpty() }

    When("execute the validation with the given function")
    val result: Boolean = isValid(valueTest, functionTest)

    Then("should pass the validation.")
    assert(result === false)

  }

  property("should validate an String that is not empty with a given function") {
    Given("a not empty String to be evaluted")
    val valueTest: String = new String("asd")

    And("a Function with a specific validation that is NotEmpty")
    val functionTest = { x: String => !x.isEmpty() }

    When("execute the validation with the given function")
    val result: Boolean = isValid(valueTest, functionTest)

    Then("should pass the validation.")
    assert(result === true)
  }

  property("should validate two Strings that can't empty with a given function") {
    Given("a not empty String to be evaluted")
    val firstValueTest: String = new String("asd")
    And("a second not empty string to be evaluated")
    val secondValueTest: String = new String("asasasdas")

    And("a function which validate that a string should not be empty")
    val functionTest = { x: String => !x.isEmpty() }

    When("execute the validation on both Strings with the given function")
    val result: Boolean = areValid(List(firstValueTest, secondValueTest), functionTest)

    Then("should pass the validation.")
    assert(result === true)
  }

  property("should validate Both empty Strings, whose can't empty with a given function") {
    Given("an empty String to be evaluted")
    val firstValueTest: String = new String("")
    And("a second not empty string to be evaluated")
    val secondValueTest: String = new String("")

    And("a function which validate that a string should not be empty")
    val functionTest = { x: String => !x.isEmpty() }

    When("execute the validation on both Strings with the given function")
    val result: Boolean = areValid(List(firstValueTest, secondValueTest), functionTest)

    Then("should pass the validation.")
    assert(result === false)
  }

}
