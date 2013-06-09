package ar.com.caeldev.bsacore.validations.rules

import ar.com.caeldev.bsacore.commons.domain.{ Category, Success, Error }

object ListNotEmpty {

  val get = { list: List[_] =>
    var result: Either[Success, Error] = null

    list.size == 0 match {
      case true  => { result = Right(Error.create(1005, Category.rules)) }
      case false => { result = Left(Success.create()) }
    }
    result
  }
}
