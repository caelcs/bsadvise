package ar.com.caeldev.bsacore.commons.domain

import ar.com.caeldev.bsacore.config.ConfigContext

case class Success()

object Success {
  val success: Success = new Success()

  def create(): Success = {
    success
  }
}

case class Error(code: Long, description: String)

object Error {

  val config: ConfigContext = new ConfigContext("errors.conf")

  def create(code: Long, category: Category.Value): Error = {
    new Error(code, config.get("errors."+category.toString+"."+code.toString+".description"))
  }

}

object Category extends Enumeration {
  type Category = Value
  val rules = Value("rules")
  val services = Value("services")
  val commons = Value("commons")
}

