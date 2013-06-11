package ar.com.caeldev.bsacore.validations.rules

import ar.com.caeldev.bsacore.commons.domain.{ Category, Success, Error }

object NotEmpty {

  val get = { x: String =>
    x.isEmpty match {
      case true  => { Right(Error.create(1000, Category.rules)) }
      case false => { Left(Success.create()) }
    }
  }

}
