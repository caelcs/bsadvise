package ar.com.caeldev.bsacore.validation.rules

import ar.com.caeldev.bsacore.validation.Success
import ar.com.caeldev.bsacore.validation

object NotEmpty {

  val get = { x: String =>
    var result: Either[Success, validation.Error] = null
    x.isEmpty match {
      case true  => { result = Right(validation.Error.create(1000)) }
      case false => { result = Left(Success.create()) }
    }
    result
  }

}
