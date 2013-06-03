package ar.com.caeldev.bsacore.services.validations

import scala.util.control.Breaks._
import ar.com.caeldev.bsacore.config.ConfigContext

class Rule[T](validation: (T) => Either[Success, Error]) {

  var entities: List[T] = _

  def this(entity: T, validation: (T) => Either[Success, Error]) = {
    this(validation)
    entities = List(entity)
  }

  def this(entities: List[T], validation: (T) => Either[Success, Error]) = {
    this(validation)
    this.entities = entities
  }

  private def isValid(entity: T): Either[Success, Error] = {
    validation(entity)
  }

  def validate(entities: List[T]): Either[Success, Error] = {
    this.entities = entities
    validate()
  }

  def validate(): Either[Success, Error] = {
    var result: Either[Success, Error] = Left(Success.create())
    breakable {
      entities.foreach { f: T =>
        val singleResult: Either[Success, Error] = isValid(f)
        if (singleResult.isRight) {
          result = singleResult
          break
        }
      }
    }
    result
  }
}

case class Success()

object Success {
  val success: Success = new Success()

  def create(): Success = {
    success
  }
}

case class Error(code: Long, description: String)

object Error {

  val config: ConfigContext = new ConfigContext("errors.conf")

  def create(code: Long): Error = {
    new Error(code, config.get("errors.rules."+code.toString+".description"))
  }

}

