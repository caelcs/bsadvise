package ar.com.caeldev.bsacore.dao

import ar.com.caeldev.bsacore.serializer.{ BsonSerializer, Serializer }
import com.mongodb.DBObject

abstract class MongoDaoImpl[T <: AnyRef](implicit mot: Manifest[T]) extends GenericDao[T] {

  val serializer: Serializer[T, DBObject] = new BsonSerializer[T]

}
