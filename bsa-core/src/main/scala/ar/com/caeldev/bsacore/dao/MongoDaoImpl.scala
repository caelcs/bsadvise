package ar.com.caeldev.bsacore.dao

import ar.com.caeldev.bsacore.serializers.{ BsonSerializer, Serializer }
import ar.com.caeldev.bsacore.db.DBConnection
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.TypeImports.MongoCollection

class MongoDaoImpl[T <: AnyRef](implicit val mot: Manifest[T]) extends GenericDao[T] {

  val serializer: Serializer[T, DBObject] = new BsonSerializer[T]
  DBConnection.connect()
  val collection: MongoCollection = DBConnection.getCollection(mot.toString())

  def findAll(): List[T] = {
    val query = MongoDBObject("id" -> MongoDBObject("$exists" -> true))
    var result: List[T] = List.empty
    catcher {
      collection.find(query).foreach {
        x =>
          result = result ::: List[T](serializer.deserialize(x))
      }
      result
    }
  }

  def save(entity: T) {
    catcher {
      val dbObject = serializer.serialize(entity)
      collection += dbObject
    }
  }

  def remove(entity: T) {
    catcher {
      val dbObject = serializer.serialize(entity)
      collection.remove(dbObject)
    }
  }

  def findById(id: Any): T = {
    val results = findBy("id", id)
    var result: T = null.asInstanceOf[T]
    if (!results.isEmpty) {
      result = results.head
    }
    result
  }

  def findBy(field: String, value: Any): List[T] = {
    val query = MongoDBObject(field -> value)
    var result: List[T] = List.empty
    catcher {
      collection.find(query).foreach {
        x =>
          result = result ::: List[T](serializer.deserialize(x))
      }
      result
    }
  }

  def find(query: MongoDBObject, sort: Option[MongoDBObject], rows: Option[Int]): List[T] = {
    var result: List[T] = List.empty
    var cursor = collection.find(query)
    sort match {
      case Some(sort) => cursor = cursor.sort(sort)
      case None       => ()
    }

    rows match {
      case Some(rows) => cursor = cursor.limit(rows)
      case None       => ()
    }

    catcher {
      cursor.foreach {
        x =>
          result = result ::: List[T](serializer.deserialize(x))
      }
    }
    result
  }
}