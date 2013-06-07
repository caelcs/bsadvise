package ar.com.caeldev.bsacore.dao

trait GenericDao[T] {

  def findAll(): List[T]

  def save(entity: T)

  def remove(entity: T)

  def findById(id: Any): T

  def findBy(field: String, value: Any): List[T]

}
