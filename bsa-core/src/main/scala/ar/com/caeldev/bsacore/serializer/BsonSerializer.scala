package ar.com.caeldev.bsacore.serializer

import com.novus.salat.global._
import com.novus.salat._
import com.mongodb.casbah.Imports._

class BsonSerializer[T <: AnyRef](implicit mot: Manifest[T]) extends Serializer[T, DBObject] {

  def serialize(entity: T): DBObject = {
    val result = grater[T].asDBObject(entity)
    result
  }

  def deserialize(target: DBObject): T = {
    val result = grater[T].asObject(target)
    result
  }

}
