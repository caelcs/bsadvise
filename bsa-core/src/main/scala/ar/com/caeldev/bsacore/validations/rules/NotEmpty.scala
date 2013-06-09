package ar.com.caeldev.bsacore.validations.rules

import ar.com.caeldev.bsacore.commons.domain.{ Category, Success, Error }

object NotEmpty {

  val get = { x: String =>
    var result: Either[Success, Error] = null
    x.isEmpty match {
      case true  => { result = Right(Error.create(1000, Category.rules)) }
      case false => { result = Left(Success.create()) }
    }
    result
  }

}
