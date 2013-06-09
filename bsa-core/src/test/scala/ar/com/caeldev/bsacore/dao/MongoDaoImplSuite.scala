package ar.com.caeldev.bsacore.dao

import org.scalatest.{ PropSpec, GivenWhenThen }
import com.mongodb.casbah.query.Imports._
import org.scalatest.prop.TableDrivenPropertyChecks
import scala.Predef._
import ar.com.caeldev.bsacore.domain._
import ar.com.caeldev.bsacore.domain.Group
import scala.Some
import ar.com.caeldev.bsacore.domain.Member

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

  property("should save a ROLE entity to a database and be able to get it back") {
    Given("a Mongo Dao")
    val dao: GenericDao[Role] = new MongoDaoImpl[Role]()
    And("a Role entity")
    val entity: Role = DomainSamples.roles(1000)

    When("persist it to the database")
    dao.save(entity)

    Then("it should be get from DB by a query")
    assert(dao.findById(1000).id === 1000)

    And("should be deleted successfully")
    dao.remove(entity)
  }

  property("should save a MEMBER entity to a database and be able to get it back") {
    Given("a Mongo Dao")
    val dao: GenericDao[Member] = new MongoDaoImpl[Member]()
    And("a MEMBER entity")
    val entity: Member = DomainSamples.members(1001)

    When("persist it to the database")
    dao.save(entity)

    Then("it should be get from DB by a query")
    assert(dao.findById(1001).id === 1001)

    And("should be deleted successfully")
    dao.remove(entity)
  }

  property("should save a GROUP entity to a database and be able to get it back") {
    Given("a Mongo Dao")
    val dao: GenericDao[Group] = new MongoDaoImpl[Group]()
    And("a GROUP entity")
    val entity: Group = DomainSamples.groups(1002)

    When("persist it to the database")
    dao.save(entity)

    Then("it should be get from DB by a query")
    assert(dao.findById(1002).id === 1002)

    And("should be deleted successfully")
    dao.remove(entity)
  }

  property("should save a NOTIFICATION entity to a database and be able to get it back") {
    Given("a Mongo Dao")
    val dao: GenericDao[Notification] = new MongoDaoImpl[Notification]()
    And("a NOTIFICATION entity")
    val entity: Notification = DomainSamples.notifications(1004)

    When("persist it to the database")
    dao.save(entity)

    Then("it should be get from DB by a query")
    assert(dao.findById(1004).id === 1004)

    And("should be deleted successfully")
    dao.remove(entity)
  }

  property("should save a MESSAGE TEMPLATE entity to a database and be able to get it back") {
    Given("a Mongo Dao")
    val dao: GenericDao[MessageTemplate] = new MongoDaoImpl[MessageTemplate]()
    And("a MESSAGE TEMPLATE entity")
    val entity: MessageTemplate = DomainSamples.messageTemplates(1005)

    When("persist it to the database")
    dao.save(entity)

    Then("it should be get from DB by a query")
    assert(dao.findById(1005).id === 1005)

    And("should be deleted successfully")
    dao.remove(entity)
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
