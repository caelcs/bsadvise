package ar.com.caeldev.bsacore.db

import ar.com.caeldev.bsacore.config.ConfigContext
import com.mongodb.casbah.{ MongoDB, MongoClient, MongoCollection }

object DBConnection {

  val envConfigContext: ConfigContext = new ConfigContext("environments")
  val appConfigContext: ConfigContext = new ConfigContext("app")

  var dbServer: String = _
  var dbPort: Int = _
  var dbName: String = _
  var dbUser: String = _
  var dbPassword: String = _

  var conn: MongoClient = _
  var db: MongoDB = _

  var isConnected = false
  var isAuthenticated = false

  def getCollection(collectionName: String): MongoCollection = {
    if (!isConnected) {
      throw new Exception("There is no connection yet. Invoke connectTo method")
    }
    login(Some(dbUser), Some(dbPassword))
    val collection: MongoCollection = db(collectionName)
    collection
  }

  def login(user: Option[String], password: Option[String]) {
    if (!isAuthenticated) {

      val userTemp = user match {
        case Some(user) => user
        case None       => dbUser
      }

      val passwordTemp = password match {
        case Some(password) => password
        case None           => dbPassword
      }

      db.authenticate(userTemp, passwordTemp)
      isAuthenticated = true
    }
  }

  def validate(env: String) {
    try {
      envConfigContext.exists("environments."+env)
    }
    catch {
      case e: Exception => throw new Exception("Environment configuration doesn't exists.", e)
    }
  }

  def connectTo(environment: String) {
    validate(environment)
    dbServer = envConfigContext.get("environments."+environment+".db.server")
    dbPort = envConfigContext.get("environments."+environment+".db.port").toInt
    dbName = envConfigContext.get("environments."+environment+".db.name")
    dbUser = envConfigContext.get("environments."+environment+".db.user")
    dbPassword = envConfigContext.get("environments."+environment+".db.password")
    conn = MongoClient(dbServer, dbPort)
    db = conn(dbName)
    isConnected = true
  }

  def connect() {
    val env: String = appConfigContext.get("application.environment")
    connectTo(env)
  }

}