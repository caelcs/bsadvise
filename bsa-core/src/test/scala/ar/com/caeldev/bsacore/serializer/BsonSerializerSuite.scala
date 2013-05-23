package ar.com.caeldev.bsacore.serializer

import org.scalatest.{FunSpec, GivenWhenThen}
import ar.com.caeldev.bsacore.domain.Role
import com.mongodb.casbah.Imports._

class BsonSerializerSuite extends FunSpec with GivenWhenThen {

  describe("A Serializer") {
    it("should serializer any object to bson") {

      Given("a new Role instance")
      val newRole: Role = new Role(1, "Test")

      And("a bson serializer")
      val serializer: BsonSerializer[Role] = new BsonSerializer[Role]()

      When("invoke the serialize method")
      val result:DBObject = serializer.serialize(newRole)

      Then("the result must contains the field id")
      assert(result.containsField("id"))

      And("the field description")
      assert(result.containsField("description"))

      And("must have the correct id and description value")
      assert(result.get("id") === 1)
      assert(result.get("description") === "Test")
    }

    it("should deserializer a DBObject back to the Role entity") {

      Given("a DBObject instance")
      val newRole: Role = new Role(1, "Test")
      val serializer: BsonSerializer[Role] = new BsonSerializer[Role]()
      val source:DBObject = serializer.serialize(newRole)

      And("a Bson Serializer instance")
      val deserializer: BsonSerializer[Role] = new BsonSerializer[Role]()

      When("invoke the deserialize method")
      val result:Role = deserializer.deserialize(source)

      Then("must have the correct id and description value")
      assert(result.id === 1)
      assert(result.description === "Test")
    }
  }

}
