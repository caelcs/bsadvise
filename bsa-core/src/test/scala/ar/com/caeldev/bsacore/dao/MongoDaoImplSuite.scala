package ar.com.caeldev.bsacore.dao

import org.scalatest.{ GivenWhenThen, FunSpec, FunSuite }
import ar.com.caeldev.bsacore.domain.Role
import ar.com.caeldev.bsacore.db.DBConnection

class MongoDaoImplSuite extends FunSpec with GivenWhenThen {

  describe("A MongoDBDao") {
    it("should save an entity to a database and be able to get it back") {
      Given("an entity")
      val role: Role = new Role(1000, "test1000")
      And("a MongoDao")
      val dao: GenericDao[Role] = new MongoDaoImpl[Role]()

      When("persist it to the database")
      dao.save(role)
      And("get the entity from DB")
      val entitySaved: Role = dao.findById(1000)

      Then("it should be get from DB by a query")
      assert(entitySaved.description === "test1000")
      And("should be deleted successfully")
      dao.remove(entitySaved)
      val entityDeleted: Role = dao.findById(1000)
      assert(entityDeleted === null)
    }

    it("should get all the entities from a collection") {
      Given("two entities")
      val role1: Role = new Role(1001, "test1001")
      val role2: Role = new Role(1002, "test1002")
      And("a MongoDao")
      val dao: GenericDao[Role] = new MongoDaoImpl[Role]()

      When("persist all the entities")
      dao.save(role1)
      dao.save(role2)

      Then("should return all the entities from collection")
      dao.findAll().foreach { x =>
        assert(x.description.contains("test"))
      }
      And("should delete all the entities")
      dao.remove(role1)
      dao.remove(role2)
    }

  }
}
