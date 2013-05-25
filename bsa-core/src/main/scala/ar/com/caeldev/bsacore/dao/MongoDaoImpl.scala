package ar.com.caeldev.bsacore.dao

import ar.com.caeldev.bsacore.serializer.{ BsonSerializer, Serializer }
import com.mongodb.{ DBCollection, DBObject }

class MongoDaoImpl[T <: AnyRef](implicit mot: Manifest[T], collectionName: String) extends GenericDao[T] {

  val serializer: Serializer[T, DBObject] = new BsonSerializer[T]

  def findAll(): List[T] = ???

  def save(entity: T): T = ???

  def remove(entity: T) {}

  def findById(id: Serializable): T = ???

}
