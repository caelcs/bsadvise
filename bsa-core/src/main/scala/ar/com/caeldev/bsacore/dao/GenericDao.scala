package ar.com.caeldev.bsacore.dao

trait GenericDao[T] {

  def findAll(): List[T]

  def save(entity: T): T

  def remove(entity: T): Unit

  def findById(id: Serializable): T

}
