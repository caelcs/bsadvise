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
        isValid(f, validation) match {
          case Right(error) => {
            result = Right(error)
            break
          }
          case Left(success) => {}
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
    new Error(code, config.get("errors.validations."+code.toString+".description"))
  }

}

