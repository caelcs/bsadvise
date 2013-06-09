package ar.com.caeldev.bsacore.serializer

import scala.util.control.Exception._
import ar.com.caeldev.bsacore.serializer.exceptions.SerializeException
import ar.com.caeldev.bsacore.config.ConfigContext

trait Serializer[T, V] {

  val appConfigContext: ConfigContext = new ConfigContext("errors.conf")
  val catcher = catching(classOf[Exception]).withApply(e => throw new SerializeException(appConfigContext.get("errors.commons.1200.description"), e))

  def serialize(entity: T): V

  def deserialize(target: V): T

}
