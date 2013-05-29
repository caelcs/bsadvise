package ar.com.caeldev.bsacore.services

import org.scalatest.{ GivenWhenThen, FunSpec }
import ar.com.caeldev.bsacore.domain.{ DomainSamples, Role }
import ar.com.caeldev.bsacore.services.exceptions.ServiceException

class RoleServiceSuite extends FunSpec with GivenWhenThen {

  describe("A Role Service") {
    it("Should add a new entity to the backend") {
      Given("a Role Entity")
      val entity: Role = DomainSamples.roles(1000)
      And("a Role Service")
      val roleService: Service[Role] = new RoleService()

      When("try to add the entity to the backend")
      val entitySaved: Role = roleService.add(entity)

      Then("should get it back from backend.")
      assert(entitySaved.equals(entity))

      roleService.delete(entitySaved.id)
    }

    it("Should not add a new entity to the backend") {
      Given("a Role Entity with an empty description")
      val entity: Role = DomainSamples.roles(1001)
      And("a Role Service")
      val roleService: Service[Role] = new RoleService()

      When("try to add the entity to the backend")
      Then("should raise a ServiceException")
      intercept[ServiceException] {
        roleService.add(entity)
      }
    }

  }
}
