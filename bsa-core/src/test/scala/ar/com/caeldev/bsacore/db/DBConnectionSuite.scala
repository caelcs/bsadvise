package ar.com.caeldev.bsacore.db

import org.scalatest.{ GivenWhenThen, FunSpec }
import ar.com.caeldev.bsacore.serializer.BsonSerializer
import ar.com.caeldev.bsacore.domain.Role
import com.mongodb.casbah.Imports._

class DBConnectionSuite extends FunSpec with GivenWhenThen {

  describe("A DB Connection") {
    it("should create a connection to the Database for a given collection") {
      Given("a collection from Database")
      val collectionName: String = "test1"
      val collection: MongoCollection = DBConnection.getCollection("test1")

      And("with a sample data to persist")
      val serializer: BsonSerializer[Role] = new BsonSerializer[Role]()
      val objectToPersist: DBObject = serializer.serialize(new Role(1, "test"))

      When("insert data in the collection")
      collection += objectToPersist

      Then("the collection contains one element")
      val q = MongoDBObject("id" -> "1")
      val cursor = collection.find(q).foreach { x =>
        assert(x("description") === "test")
      }
    }

  }

}
