package ar.com.caeldev.bsacore.dao

import ar.com.caeldev.bsacore.serializer.{BsonSerializer, Serializer}
import ar.com.caeldev.bsacore.domain.Role
import com.mongodb.DBObject

trait MongoDaoImpl[T] extends GenericDao[T] {

  val serializer: Serializer[Role, DBObject] = new BsonSerializer[Role]

  def findAll(): List[T] = ???

  def save(entity: T): T = ???

  def remove(entity: T) {}

  def findById(id: Long): T = ???

}
