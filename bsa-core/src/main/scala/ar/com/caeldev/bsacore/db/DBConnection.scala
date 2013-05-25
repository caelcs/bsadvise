package ar.com.caeldev.bsacore.db

import ar.com.caeldev.bsacore.config.ConfigContext
import com.mongodb.casbah.{ MongoClient, MongoCollection, MongoConnection }

object DBConnection {

  val configContext: ConfigContext = new ConfigContext("db")

  val dbServer = configContext.get("db.server")
  val dbPort = configContext.get("db.port").toInt
  val dbName = configContext.get("db.name")
  val dbUser = configContext.get("db.user")
  val dbPassword = configContext.get("db.password")

  val conn = MongoClient(dbServer, dbPort)
  val db = conn(dbName)

  def getCollection(collectionName: String): MongoCollection = {
    login(dbUser, dbPassword)
    val collection: MongoCollection = db(collectionName)
    collection
  }

  def login(user: String, password: String) = {
    db.authenticate(user, user)
  }

}