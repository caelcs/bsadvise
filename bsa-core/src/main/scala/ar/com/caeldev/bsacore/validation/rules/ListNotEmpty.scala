package ar.com.caeldev.bsacore.validation.rules

import ar.com.caeldev.bsacore.validation.Success
import ar.com.caeldev.bsacore.validation

object ListNotEmpty {

  val get = { list: List[_] =>
    var result: Either[Success, validation.Error] = null

    list.size == 0 match {
      case true  => { result = Right(validation.Error.create(1005)) }
      case false => { result = Left(Success.create()) }
    }
    result
  }
}
