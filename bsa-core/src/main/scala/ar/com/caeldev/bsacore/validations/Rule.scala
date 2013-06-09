package ar.com.caeldev.bsacore.validations

import scala.util.control.Breaks._
import ar.com.caeldev.bsacore.commons.domain.{ Success, Error }

class Rule[T](validation: (T) => Either[Success, Error]) {

  var entities: List[T] = Nil

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
          break()
        }
      }
    }
    result
  }
}

