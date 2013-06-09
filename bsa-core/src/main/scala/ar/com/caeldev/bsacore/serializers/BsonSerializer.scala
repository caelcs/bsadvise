package ar.com.caeldev.bsacore.serializers

import com.novus.salat.global._
import com.novus.salat._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers

class BsonSerializer[T <: AnyRef](implicit mot: Manifest[T]) extends Serializer[T, DBObject] {

  RegisterJodaTimeConversionHelpers()

  def serialize(entity: T): DBObject = {
    catcher {
      val result = grater[T].asDBObject(entity)
      result
    }
  }

  def deserialize(target: DBObject): T = {
    catcher {
      val result = grater[T].asObject(target)
      result
    }
  }

}
