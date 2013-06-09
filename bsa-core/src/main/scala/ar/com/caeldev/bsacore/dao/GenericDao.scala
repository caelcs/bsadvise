package ar.com.caeldev.bsacore.dao

import ar.com.caeldev.bsacore.config.ConfigContext
import scala.util.control.Exception._
import ar.com.caeldev.bsacore.dao.exceptions.DaoException

trait GenericDao[T] {

  val appConfigContext: ConfigContext = new ConfigContext("errors.conf")
  val catcher = catching(classOf[Exception]).withApply(e => throw new DaoException(appConfigContext.get("errors.commons.1201.description"), e))

  def findAll(): List[T]

  def save(entity: T)

  def remove(entity: T)

  def findById(id: Any): T

  def findBy(field: String, value: Any): List[T]

}
