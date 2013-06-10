package ar.com.caeldev.bsacore.serializers

import org.scalatest.{ FunSpec, GivenWhenThen }
import org.scalatest.prop.TableDrivenPropertyChecks
import ar.com.caeldev.bsacore.domain._
import ar.com.caeldev.bsacore.domain.Group
import ar.com.caeldev.bsacore.domain.Member

class JsonSerializerSuite extends FunSpec with GivenWhenThen with TableDrivenPropertyChecks {

  describe("a Json Serializer") {

    it("Should serialize a ROLE entity to Json") {
      Given("a ROLE entity")
      var entity: Role = DomainSamples.roles(1000)

      And("a JsonSerializer")
      var serializer: Serializer[Role, String] = new JsonSerializer[Role]()

      When("serialize the entity to json")
      var result: String = serializer.serialize(entity)

      Then("should get a json string")
      assert(!result.isEmpty)
      var entityConv: Role = serializer.deserialize(result)
      assert(entityConv.description === entity.description)
      assert(entityConv.id === entity.id)
    }

    it("Should serialize a MEMBER entity to Json") {
      Given("a MEMBER entity")
      val entity: Member = DomainSamples.members(1001)

      And("a JsonSerializer")
      var serializer: Serializer[Member, String] = new JsonSerializer[Member]()

      When("serialize the entity to json")
      var result: String = serializer.serialize(entity)

      Then("should get a json string")
      assert(!result.isEmpty)
      var entityConv: Member = serializer.deserialize(result)

      assert(entityConv.id === entity.id)
      assert(entityConv.role_id === entity.role_id)
      assert(entityConv.firstName === entity.firstName)
      assert(entityConv.lastName === entity.lastName)
      assert(entityConv.email === entity.email)
      assert(entityConv.contactInfo === entity.contactInfo)
      assert(entityConv.contactInfo.address === entity.contactInfo.address)
      assert(entityConv.creationDate === entity.creationDate)
      assert(entityConv.updateDate === entity.updateDate)

    }

    it("Should serialize a GROUP entity to Json") {
      Given("a GROUP entity")
      val entity: Group = DomainSamples.groups(1002)

      And("a JsonSerializer")
      var serializer: Serializer[Group, String] = new JsonSerializer[Group]()

      When("serialize the entity to json")
      var result: String = serializer.serialize(entity)

      Then("should get a json string")
      assert(!result.isEmpty)
      var entityConv: Group = serializer.deserialize(result)

      assert(entityConv.id === entity.id)
      assert(entityConv.name === entity.name)
      assert(entityConv.members === entity.members)
    }

    it("Should serialize a NOTIFICATION entity to Json") {
      Given("a NOTIFICATION entity")
      val entity: Notification = DomainSamples.notifications(1004)

      And("a JsonSerializer")
      var serializer: Serializer[Notification, String] = new JsonSerializer[Notification]()

      When("serialize the entity to json")
      var result: String = serializer.serialize(entity)

      Then("should get a json string")
      assert(!result.isEmpty)
      var entityConv: Notification = serializer.deserialize(result)

      assert(entityConv.id === entity.id)
      assert(entityConv.message === entity.message)
      assert(entityConv.subject === entity.subject)
      assert(entityConv.receivers === entity.receivers)
      assert(entityConv.sender_id === entity.sender_id)
    }

    it("Should serialize a MESSAGE TEMPLATE entity to Json") {
      Given("a MESSAGE TEMPLATE entity")
      val entity: MessageTemplate = DomainSamples.messageTemplates(1005)

      And("a JsonSerializer")
      var serializer: Serializer[MessageTemplate, String] = new JsonSerializer[MessageTemplate]()

      When("serialize the entity to json")
      var result: String = serializer.serialize(entity)

      Then("should get a json string")
      assert(!result.isEmpty)
      var entityConv: MessageTemplate = serializer.deserialize(result)

      assert(entityConv.id === entity.id)
      assert(entityConv.message === entity.message)
      assert(entityConv.createdAt === entity.createdAt)
      assert(entityConv.createdBy === entity.createdBy)
    }

  }

}
