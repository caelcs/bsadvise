package ar.com.caeldev.bsacore.validation.rules

import ar.com.caeldev.bsacore.validation.Success
import ar.com.caeldev.bsacore.validation

object NotNull {

  val get = { x: Any =>
    var result: Either[Success, validation.Error] = null
    x == null match {
      case true  => { result = Right(validation.Error.create(1004)) }
      case false => { result = Left(Success.create()) }
    }
    result
  }

}
