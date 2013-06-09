package ar.com.caeldev.bsacore.validations.rules

import ar.com.caeldev.bsacore.commons.domain.{ Category, Success, Error }

object NotNull {

  val get = { x: Any =>
    var result: Either[Success, Error] = null
    x == null match {
      case true  => { result = Right(Error.create(1004, Category.rules)) }
      case false => { result = Left(Success.create()) }
    }
    result
  }

}
