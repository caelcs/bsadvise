package ar.com.caeldev.bsacore.validations.rules

import ar.com.caeldev.bsacore.commons.domain.{ Category, Success, Error }

object NotNull {

  val get = { x: Any =>
    x == null match {
      case true  => { Right(Error.create(1004, Category.rules)) }
      case false => { Left(Success.create()) }
    }
  }

}
