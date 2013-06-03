package ar.com.caeldev.bsacore.services.common.rules

import ar.com.caeldev.bsacore.services.validations.{ Error, Success }

object NotEmptyRule {

  val rule = { x: String =>
    var result: Either[Success, Error] = null
    x.isEmpty match {
      case true  => { result = Right(Error.create(1000)) }
      case false => { result = Left(Success.create()) }
    }
    result
  }

}
