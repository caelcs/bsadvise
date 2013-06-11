package ar.com.caeldev.bsacore.validations.rules

import ar.com.caeldev.bsacore.commons.domain.{ Category, Success, Error }

object ListNotEmpty {

  val get = { list: List[_] =>
    list.size == 0 match {
      case true  => { Right(Error.create(1005, Category.rules)) }
      case false => { Left(Success.create()) }
    }
  }
}
