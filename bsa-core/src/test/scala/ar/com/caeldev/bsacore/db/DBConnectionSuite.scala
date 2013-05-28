package ar.com.caeldev.bsacore.db

import org.scalatest.{ GivenWhenThen, FunSpec }
import ar.com.caeldev.bsacore.serializer.BsonSerializer
import ar.com.caeldev.bsacore.domain.Role
import com.mongodb.casbah.Imports._

class DBConnectionSuite extends FunSpec with GivenWhenThen {

  describe("A DB Connection") {
    it("should create a connection authenticated with a user a password") {
      Given("an environment to be connect")
      val env: String = "test"
      When("create a connection")
      DBConnection.connectTo(env)
      DBConnection.login(None, None)

      Then("it should be created successfully")
      assert(DBConnection.conn.getVersion() != null)
    }

    it("should not create a connection given a environment who it doesn't exists") {
      Given("an environment which doesn't exists")
      val env: String = "wrong"
      Then("should raise an exception")
      intercept[java.lang.Exception] {
        DBConnection.connectTo(env)
        DBConnection.login(None, None)
      }
    }

    it("should create a connection to the Database for a given collection") {
      Given("an environment to be connect")
      val env: String = "test"
      And("create a connection")
      DBConnection.connectTo(env)
      DBConnection.login(None, None)

      And("a collection Name")
      val collectionName: String = "test"

      And("collection from Database based on collection name")
      val collection: MongoCollection = DBConnection.getCollection(collectionName)

      And("with a sample data to persist")
      val serializer: BsonSerializer[Role] = new BsonSerializer[Role]()
      val objectToPersist: DBObject = serializer.serialize(new Role(1, "test"))

      When("insert data in the collection")
      collection += objectToPersist

      Then("the collection contains one element")
      val q = MongoDBObject("id" -> "1")
      collection.find(q).foreach { x =>
        assert(x("description") === "test")
      }
      collection.remove(q)

    }

  }

}
