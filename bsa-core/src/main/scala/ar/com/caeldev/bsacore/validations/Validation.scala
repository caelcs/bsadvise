package ar.com.caeldev.bsacore.validations

import ar.com.caeldev.bsacore.validations.exceptions.ValidationException
import ar.com.caeldev.bsacore.validations.rules.NotNull
import ar.com.caeldev.bsacore.commons.domain.{ Success, Error }

trait Validation[T] {

  def validate(entity: T, operation: Operation.Value) = {
    executeRules(applyCommonsRulesFor(entity))
    executeRules(applyRulesFor(entity, operation))
  }

  private def executeRules(rules: List[Rule[_]]) {
    rules.foreach { rule =>
      val result: Either[Success, Error] = rule.validate()
      if (result.isRight) {
        throw new ValidationException(result.right.get.description)
      }
    }
  }

  def applyRulesFor(entity: T, operation: Operation.Value): List[Rule[_]]

  def applyCommonsRulesFor(entity: T): List[Rule[_]] = {
    List(
      new Rule[T](entity, NotNull.get))
  }

}

object Operation extends Enumeration {
  type Operation = Value
  val add = Value("add")
  val update = Value("update")
  val delete = Value("delete")
}
