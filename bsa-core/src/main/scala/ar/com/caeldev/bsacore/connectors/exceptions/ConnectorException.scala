package ar.com.caeldev.bsacore.connectors.exceptions

import ar.com.caeldev.bsacore.exceptions.BaseException

class ConnectorException(message: String, nestedException: Throwable) extends BaseException(message, nestedException) {

  def this() = this("", null)

  def this(message: String) = this(message, null)

  def this(nestedException: Throwable) = this("", nestedException)

}
