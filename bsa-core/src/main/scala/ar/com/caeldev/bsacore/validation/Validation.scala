package ar.com.caeldev.bsacore.validation

import ar.com.caeldev.bsacore.validation.exceptions.ValidationException
import ar.com.caeldev.bsacore.validation.rules.NotNull

trait Validation[T] {

  def validate(entity: T, operation: String) = {
    executeRules(applyCommonsRulesFor(entity))
    executeRules(applyRulesFor(entity, operation))
  }

  private def executeRules(rules: List[Rule[_]]) {
    rules.foreach { rule =>
      var result: Either[Success, Error] = rule.validate()
      if (result.isRight) {
        throw new ValidationException(result.right.get.description)
      }
    }
  }

  def applyRulesFor(entity: T, operation: String): List[Rule[_]]

  def applyCommonsRulesFor(entity: T): List[Rule[_]] = {
    List(
      new Rule[T](entity, NotNull.get))
  }

}
