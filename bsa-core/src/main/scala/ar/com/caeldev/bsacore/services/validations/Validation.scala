package ar.com.caeldev.bsacore.services.validations

import util.control.Breaks._

trait Validation[T] {

  def isValid(entity: T, validation: (T) => Boolean) = {
    validation(entity)
  }

  def areValid(entities: List[T], validation: (T) => Boolean) = {
    var result: Boolean = true
    breakable {
      entities.foreach { f: T =>
        if (!isValid(f, validation)) {
          result = false
          break
        }
      }
    }
    result
  }
}
