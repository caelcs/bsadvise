package ar.com.caeldev.bsacore.dao

import org.scalatest.{ PropSpec, GivenWhenThen }
import ar.com.caeldev.bsacore.domain.Role
import com.mongodb.casbah.query.Imports._
import org.scalatest.prop.TableDrivenPropertyChecks

class MongoDaoImplSuite extends PropSpec with GivenWhenThen with TableDrivenPropertyChecks {

  val sampleSet1 =
    Table(
      "role",
      new Role(1000, "test1000"))

  val sampleSet2 =
    Table(
      "role",
      new Role(1001, "test1001"),
      new Role(1002, "test1002"))

  val sampleSet3Match =
    Table(
      "role",
      new Role(1000, "test1000"),
      new Role(1001, "test1001"),
      new Role(1002, "test1002"),
      new Role(1001, "test1001"),
      new Role(1002, "test1002"))

  val sampleSet3Unmatch =
    Table(
      "role",
      new Role(1003, "1003"),
      new Role(1004, "1004"))

  val sampleSet3Rows =
    Table(
      "rows",
      3,
      10,
      20)

  property("should save entity to a database and be able to get it back") {
    forAll(sampleSet1) {
      role =>
        Given("a Mongo Dao")
        And("an entity")
        val dao: GenericDao[Role] = new MongoDaoImpl[Role]()

        When("persist it to the database")
        dao.save(role)

        And("get the entity from DB")
        val entitySaved: Role = dao.findById(role.id)

        Then("it should be get from DB by a query")
        assert(entitySaved.description === role.description)
        And("should be deleted successfully")
        dao.remove(entitySaved)
    }
  }

  property("should get all the entities that have persisted from a collection") {
    Given("a Mongo Dao")
    And("a collections of entities")
    val dao: GenericDao[Role] = new MongoDaoImpl[Role]()

    When("persist all the entities")
    forAll(sampleSet2) {
      role =>
        dao.save(role)
    }

    Then("should return all the entities from collection")
    val result = dao.findAll()
    assert(result.size === sampleSet2.size)

    forAll(sampleSet2) {
      role =>
        dao.remove(role)
    }
  }

  property("should return all the elements sorting by a criteria and with a limited size") {
    forAll(sampleSet3Rows) {
      rows =>

        Given("a Mongo Dao")
        val dao: MongoDaoImpl[Role] = new MongoDaoImpl[Role]()

        And("Several entities persisted")
        forAll(sampleSet3Match ++ sampleSet3Unmatch) {
          role =>
            dao.save(role)
        }

        And("a query")
        val query: MongoDBObject = MongoDBObject("description" -> "test*".r)
        And("with sort criteria")
        val sort: MongoDBObject = MongoDBObject("description" -> 1)
        And("with a max size result")

        When("try to get the result using the query %s and sorting criteria %s and max result size of %d" format (query.toString(), sort.toString(), rows))
        val result = dao.find(query, Some(sort), Some(rows))

        Then("should contain only those result whom match with the given query")
        result.foreach {
          x =>
            assert(x.description.contains("test"))
        }

        And("should return less or equal to max size of result")
        assert(result.size <= rows)

        forAll(sampleSet3Match ++ sampleSet3Unmatch) {
          role =>
            dao.remove(role)
        }
    }
  }
}
