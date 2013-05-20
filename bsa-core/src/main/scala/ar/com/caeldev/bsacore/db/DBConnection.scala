package ar.com.caeldev.bsacore.db

import ar.com.caeldev.bsacore.config.ConfigContext
import com.mongodb.casbah.{MongoCollection, MongoConnection}

object DBConnection {

  val configContext: ConfigContext = new ConfigContext("db")
  val conn = MongoConnection(configContext.get("db.server"), configContext.get("db.port").toInt)
  val db = conn(configContext.get("db.name"))

  def getCollection(collectionName: String):MongoCollection = {
    val collection: MongoCollection = db(collectionName)
    collection
  }

}