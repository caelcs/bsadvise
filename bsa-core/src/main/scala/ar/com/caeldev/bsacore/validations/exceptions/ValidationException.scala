package ar.com.caeldev.bsacore.validations.exceptions

import ar.com.caeldev.bsacore.exceptions.BaseException

class ValidationException(message: String, nestedException: Throwable) extends BaseException(message, nestedException) {

  def this() = this("", null)

  def this(message: String) = this(message, null)

  def this(nestedException: Throwable) = this("", nestedException)

}
