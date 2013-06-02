package ar.com.caeldev.bsacore.services.common

import ar.com.caeldev.bsacore.dao.{ GenericDao, MongoDaoImpl }

trait Service[T <: AnyRef] {

  implicit val mot: Manifest[T]

  val dao: GenericDao[T] = new MongoDaoImpl[T]()

  def add(entity: T): T

  def delete(id: Long)

  def update(entity: T): T

  def get(id: Long): T

  def validate(entity: T)

}
