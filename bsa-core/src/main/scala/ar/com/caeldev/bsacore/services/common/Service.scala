package ar.com.caeldev.bsacore.services.common

import ar.com.caeldev.bsacore.dao.{ GenericDao, MongoDaoImpl }
import ar.com.caeldev.bsacore.services.validations.{ Success, Rule, Error }
import ar.com.caeldev.bsacore.services.exceptions.ServiceException

trait Service[T <: AnyRef] {

  implicit val mot: Manifest[T]

  val dao: GenericDao[T] = new MongoDaoImpl[T]()

  def add(entity: T): T

  def delete(id: Any)

  def update(entity: T): T

  def get(id: Any): T

  def validate(entity: T, operation: String) = {
    val specificRules: List[Rule[_]] = applyRulesFor(entity, operation)
    specificRules.foreach { rule =>
      var result: Either[Success, Error] = rule.validate()
      if (result.isRight) {
        throw new ServiceException(result.right.get.description)
      }
    }
  }

  def applyRulesFor(entity: T, operation: String): List[Rule[_]]

}

object Operation extends Enumeration {
  type Operation = Value
  val add = Value("add")
  val update = Value("update")
  val delete = Value("delete")
}
