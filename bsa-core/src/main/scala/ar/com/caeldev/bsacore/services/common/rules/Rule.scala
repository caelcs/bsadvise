package ar.com.caeldev.bsacore.services.validations

import scala.util.control.Breaks._
import ar.com.caeldev.bsacore.config.ConfigContext

trait Rule[T] {

  def isValid(entity: T, validation: (T) => Either[Success, Error]): Either[Success, Error] = {
    validation(entity)
  }

  def areValid(entities: List[T], validation: (T) => Either[Success, Error]): Either[Success, Error] = {
    var result: Either[Success, Error] = Left(Success.create())
    breakable {
      entities.foreach { f: T =>
        val singleResult: Either[Success, Error] = isValid(f, validation)
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

